package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"fmt"
	"encoding/json"
	"bytes"
)

type FeeSmartContract struct {
}

// 定义扣费明细构造体
type Fee struct {
	DueDate           string  `json:"dueDate"`           // 应扣费日期
	PayableAmount     float64  `json:"payableAmount"`     // 应扣费金额
	ActualTransactionDate     string  `json:"actualTransactionDate"`     // 实际扣费日期
	ActualDeductionAmount       float64  `json:"actualDeductionAmount"`       // 实际扣费金额
	DeductionStatus   string  `json:"deductionStatus"`   // 扣费状态 1-成功 0-失败
	TransactionSerialNo   string  `json:"transactionSerialNo"`   // 交易流水号
	DeductionRule       string  `json:"deductionRule"`       // 扣费规则 ID
	DeductionRuleName string `json:"deductionRuleName"` // 扣费规则名称
	DeductionRuleCode       string  `json:"deductionRuleCode"`       // 扣费规则编码
	BasedOn      float64  `json:"basedOn"`      // 计费基础
	Rate        float64  `json:"rate"`        // 费率
}

// 初始化方法
func (a *FeeSmartContract) Init(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("fee: Init")
	return shim.Success(nil)
}

// 调用
func (a *FeeSmartContract) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("fee: Invoke")
	function, args := stub.GetFunctionAndParameters()
	if function == "insert" {
		return a.insert(stub, args)
	} else if function == "query" {
		return a.query(stub, args)
	}
	return shim.Error("Invalid invoke function name. expecting \"insert\"  \"query\" ")
}

// 扣费明细入链
func (a *FeeSmartContract) insert(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	fmt.Printf("fee insert function invoked")

	var fee Fee
	feeBytes := []byte(args[0]) // 将传进来的参数转换为字节数组

	err := json.Unmarshal(feeBytes, &fee) // 通过json工具将字节数组转换为实体对象
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because %s", err.Error()))
	}


	// 以交易流水号-实际扣费日期做联合主键
	compositeKey, _ := stub.CreateCompositeKey("transactionSerialNo-actualTransactionDate", []string{fee.TransactionSerialNo, fee.ActualTransactionDate})
	err = stub.PutState(compositeKey, feeBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey, err.Error()))
	}

	// 以实际扣费日期做联合主键
	compositeKey1, _ := stub.CreateCompositeKey("actualTransactionDate", []string{fee.ActualTransactionDate})
	err = stub.PutState(compositeKey1, feeBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey1, err.Error()))
	}

	return shim.Success(nil)
}

// 查询
func (a *FeeSmartContract) query(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 2 {
		return shim.Error("Invalid number of arguments. Expecting 2.")
	}

	var iteratorResult shim.StateQueryIteratorInterface
	if args[0] != "" {
		if args[1] != "" { // 应扣费日期和实际扣费日期联合查询
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("transactionSerialNo-actualTransactionDate", []string{args[0], args[1]})
		} else { // 只按应扣费日期查询
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("transactionSerialNo-actualTransactionDate", []string{args[0]})
		}
	} else if args[1] != "" { // 只按实际扣费日期查询
		iteratorResult, _ = stub.GetStateByPartialCompositeKey("actualTransactionDate", []string{args[1]})
	} else {
		return shim.Error("Incorrect arguments. every argument is empty")
	}

	result, err := getIteratorResult(iteratorResult)

	if err != nil {
		return shim.Error(fmt.Sprintf("invoke function getIteratorResult error: %s", err.Error()))
	}

	return shim.Success(result)
}

// 内部方法，为查询出来待处理的结果集服务
func getIteratorResult(iteratorResult shim.StateQueryIteratorInterface) ([]byte, error) {
	defer iteratorResult.Close()

	var buffer bytes.Buffer
	buffer.WriteString("[")

	for iteratorResult.HasNext() {
		queryResult, err := iteratorResult.Next()
		if err != nil {
			return nil, err
		}

		buffer.WriteString(string(queryResult.GetValue()))
		if iteratorResult.HasNext() {
			buffer.WriteString(",")
		}
	}

	buffer.WriteString("]")
	return buffer.Bytes(), nil
}

func main() {
	err := shim.Start(new(FeeSmartContract))
	fmt.Printf("Error starting FeeSmartContract chaincode: %s", err.Error())
}
