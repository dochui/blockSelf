package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"fmt"
	"encoding/json"
	"bytes"
)

type BfjFlowSmartContract struct{
}

type BfjFlow struct {
	BfjNo string `json:"bfjNo"`//备付金账号
	TransactionDate string `json:"transactionDate"`//交易时间
	Subject string `json:"subject"`//科目
	TransactionAmount float64 `json:"transactionAmount"`//交易金额
	TransactionDesc string `json:"transactionDesc"`//交易描述
	LoanFlag string `json:"loanFlag"`//借贷标记
	LoanNumber string `json:"loanNumber"`//借款编号
	TransactionSerialNo string `json:"transactionSerialNo"`//交易流水号
}

// 初始化
func (b *BfjFlowSmartContract) Init(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("bfjflow: Init\n")
	return shim.Success(nil)
}

// 调用
func (b *BfjFlowSmartContract) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("bfjflow: Invoke\n")
	function, args := stub.GetFunctionAndParameters()
	if function == "insert" {
		return b.insert(stub, args)
	} else if function == "query" {
		return b.query(stub, args)
	}

	return shim.Error("Invalid function name. Expecting \"insert\" \"query\"")
}

// 入链(参数顺序：备付金帐号，交易日期，借款编号，交易流水号)
func (b *BfjFlowSmartContract) insert(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	fmt.Printf("bfjflow insert function invoked.\n")

	var bfjflow BfjFlow
	bfjflowBytes := []byte(args[0])

	err := json.Unmarshal(bfjflowBytes, &bfjflow)
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because %s", err.Error()))
	}

	// 构造复合主键
	compositeKey, _ := stub.CreateCompositeKey("bfjNo-transactionDate-loanNumber-transactionSerialNo", []string{bfjflow.BfjNo, bfjflow.TransactionDate, bfjflow.LoanNumber, bfjflow.TransactionSerialNo})
	err = stub.PutState(compositeKey, bfjflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey, err.Error()))
	}

	compositeKey2, _ := stub.CreateCompositeKey("bfjNo-loanNumber-transactionSerialNo", []string{bfjflow.BfjNo, bfjflow.LoanNumber, bfjflow.TransactionSerialNo})
	err = stub.PutState(compositeKey2, bfjflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey2: %s, because %s", compositeKey2, err.Error()))
	}

	compositeKey3, _ := stub.CreateCompositeKey("transactionDate-loanNumber-transactionSerialNo", []string{bfjflow.TransactionDate, bfjflow.LoanNumber, bfjflow.TransactionSerialNo})
	err = stub.PutState(compositeKey3, bfjflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey3: %s, because %s", compositeKey3, err.Error()))
	}

	compositeKey4, _ := stub.CreateCompositeKey("loanNumber-transactionSerialNo", []string{bfjflow.LoanNumber, bfjflow.TransactionSerialNo})
	err = stub.PutState(compositeKey4, bfjflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey4: %s, because %s", compositeKey4, err.Error()))
	}

	return shim.Success(nil)
}

// 查询
func (b *BfjFlowSmartContract) query(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	fmt.Printf("bfjflow query function invoked.\n")
	if len(args) != 3 {
		return shim.Error(fmt.Sprintf("Incorrect number of arguments. Expecting 3."))
	}

	var iteratorResult shim.StateQueryIteratorInterface
	if args[0] != "" {
		if args[1] != "" {
			if args[2] != "" {
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("bfjNo-transactionDate-loanNumber-transactionSerialNo", []string{args[0], args[1], args[2]})
			} else {
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("bfjNo-transactionDate-loanNumber-transactionSerialNo", []string{args[0], args[1]})
			}
		} else if args[2] != "" {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("bfjNo-loanNumber-transactionSerialNo", []string{args[0], args[2]})
		} else {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("bfjNo-transactionDate-loanNumber-transactionSerialNo", []string{args[0]})
		}
	} else if args[1] != "" {
		if args[2] != "" {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("transactionDate-loanNumber-transactionSerialNo", []string{args[1], args[2]})
		} else {
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("transactionDate-loanNumber-transactionSerialNo", []string{args[1]})
		}
	} else if args[2] != "" {
		iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-transactionSerialNo", []string{args[2]})
	} else {
		return shim.Error("incorrect arguments. every argument is empty")
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
		result, err := iteratorResult.Next()
		if err != nil {
			fmt.Printf("iterator StateQueryIteratorInterface error because :%s", err.Error())
			return nil, err
		}

		buffer.WriteString(string(result.Value))

		if iteratorResult.HasNext() {
			buffer.WriteString(",")
		}
	}

	buffer.WriteString("]")

	return buffer.Bytes(), nil
}

func main() {
	err := shim.Start(new(BfjFlowSmartContract))
	if err != nil {
		fmt.Printf("Error starting a new BfjFlowSmartContract: %s", err.Error())
	}
}