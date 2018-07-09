package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"fmt"
	"encoding/json"
	"bytes"
)

type RevTransSmartContract struct {
}

// 定义冲正交易构造体
type RevTrans struct {
	LoanNumber           string  `json:"loanNumber"`           // 借款编号-业务主键，新网唯一标识一笔业务。
	RushReason     string  `json:"rushReason"`     // 冲正原因 1-放款冲销 2-还款冲销
	OriginalTransactionDate     string  `json:"originalTransactionDate"`     // 原交易日期
	RushDate       string  `json:"rushDate"`       // 冲正日期
	RushAmount   float64  `json:"rushAmount"`   // 冲正金额
	RushPrincipal   float64  `json:"rushPrincipal"`   // 冲正本金
	RusInterest       float64  `json:"rusInterest"`       // 冲正利息
	RushPenaltyInterest float64 `json:"rushPenaltyInterest"` // 冲正罚息
	RushCost       float64  `json:"rushCost"`       // 冲销费用
}

// 初始化方法
func (a *RevTransSmartContract) Init(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("revTrans: Init")
	return shim.Success(nil)
}

// 调用
func (a *RevTransSmartContract) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("revTrans: Invoke")
	function, args := stub.GetFunctionAndParameters()
	if function == "insert" {
		return a.insert(stub, args)
	} else if function == "query" {
		return a.query(stub, args)
	}
	return shim.Error("Invalid invoke function name. expecting \"insert\"  \"query\" ")
}

// 扣费明细入链
func (a *RevTransSmartContract) insert(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	fmt.Printf("revTrans insert function invoked")

	var revTrans RevTrans
	revTransBytes := []byte(args[0]) // 将传进来的参数转换为字节数组

	err := json.Unmarshal(revTransBytes, &revTrans) // 通过json工具将字节数组转换为实体对象
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because %s", err.Error()))
	}


	// 以借款编号+冲正原因+冲正日期做联合主键
	compositeKey, _ := stub.CreateCompositeKey("loanNumber-rushReason-rushDate", []string{revTrans.LoanNumber,revTrans.RushReason,revTrans.RushDate})
	err = stub.PutState(compositeKey, revTransBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey, err.Error()))
	}
	// 以借款编号+冲正日期做联合主键
	compositeKey1, _ := stub.CreateCompositeKey("loanNumber-rushDate", []string{revTrans.LoanNumber,revTrans.RushDate})
	err = stub.PutState(compositeKey1, revTransBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey1, err.Error()))
	}
	// 以冲正原因+冲正日期做联合主键
	compositeKey2, _ := stub.CreateCompositeKey("rushReason-rushDate", []string{revTrans.RushReason,revTrans.RushDate})
	err = stub.PutState(compositeKey2, revTransBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey2, err.Error()))
	}
	// 以冲正日期做联合主键
	compositeKey3, _ := stub.CreateCompositeKey("rushDate", []string{revTrans.RushDate})
	err = stub.PutState(compositeKey3, revTransBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey3, err.Error()))
	}


	return shim.Success(nil)
}

// 查询
func (a *RevTransSmartContract) query(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 3 {
		return shim.Error("Invalid number of arguments. Expecting 2.")
	}

	var iteratorResult shim.StateQueryIteratorInterface
	if args[0] != "" {
		if args[1] != "" {
			if args[2] != "" {
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-rushReason-rushDate", []string{args[0],args[1],args[2]})
			}else{
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-rushReason-rushDate", []string{args[0],args[1]})
			}
		}else{
			if args[2] != "" {
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-rushDate", []string{args[0],args[2]})
			}else{
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-rushDate", []string{args[0]})
			}
		}
	}else{
		if args[1] != "" {
			if args[2] != "" {
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("rushReason-rushDate", []string{args[1],args[2]})
			}else{
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("rushReason-rushDate", []string{args[1]})
			}
		}else{
			if args[2] != "" {
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("rushDate", []string{args[2]})
			}else{
				return shim.Error("Incorrect arguments. every argument is empty")
			}
		}

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
	err := shim.Start(new(RevTransSmartContract))
	fmt.Printf("Error starting RevTransSmartContract chaincode: %s", err.Error())
}
