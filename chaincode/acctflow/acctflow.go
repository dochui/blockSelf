package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"fmt"
	"encoding/json"
	"bytes"
	"crypto/sha1"
	"io"
)

type AcctFlowSmartContract struct {
}

// 定义会计分录构造体
type AcctFlow struct {
	Uuid           string  `json:"uuid"`           // UUID
	LoanNumber     string  `json:"loanNumber"`     // 贷款编号
	HappenDate     string  `json:"happenDate"`     // 发生日期
	Currency       string  `json:"currency"`       // 币种
	BusinessCode   string  `json:"businessCode"`   // 交易码
	BusinessDesc   string  `json:"businessDesc"`   // 交易描述
	LoanFlag       string  `json:"loanFlag"`       // 借贷标记（D-借，C-贷）
	BusinessAmount float64 `json:"businessAmount"` // 入账金额
	BranchNo       string  `json:"branchNo"`       // 分支行号
	ProductNo      string  `json:"productNo"`      // 产品号
	Subject        string  `json:"subject"`        // 科目（1301-个人贷款，1501-应收利息，2111-同业存放，2311-储蓄活期存款，6001-贷款利息收入）
}

// 初始化方法
func (a *AcctFlowSmartContract) Init(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("acctflow: Init")
	return shim.Success(nil)
}

// 调用
func (a *AcctFlowSmartContract) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	fmt.Printf("acctflow: Invoke")
	function, args := stub.GetFunctionAndParameters()
	if function == "insert" {
		return a.insert(stub, args)
	} else if function == "query" {
		return a.query(stub, args)
	}
	return shim.Error("Invalid invoke function name. expecting \"insert\"  \"query\" ")
}

// 会计分录入链
func (a *AcctFlowSmartContract) insert(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	fmt.Printf("acctflow insert function invoked")

	var acctFlow AcctFlow
	acctflowBytes := []byte(args[0]) // 将传进来的参数转换为字节数组

	err := json.Unmarshal(acctflowBytes, &acctFlow) // 通过json工具将字节数组转换为实体对象
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because %s", err.Error()))
	}

	// 以uuid做主键
	compositeKey0, _ := stub.CreateCompositeKey("uuid", []string{acctFlow.Uuid})
	err = stub.PutState(compositeKey0, acctflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey0, err.Error()))
	}

	// 以贷款编号-发生日期-uuid做联合主键
	compositeKey, _ := stub.CreateCompositeKey("loanNumber-happenDate", []string{acctFlow.LoanNumber, acctFlow.HappenDate, acctFlow.Uuid})
	err = stub.PutState(compositeKey, acctflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey, err.Error()))
	}

	// 以发生日期-uuid做联合主键
	compositeKey2, _ := stub.CreateCompositeKey("happenDate", []string{acctFlow.HappenDate, acctFlow.Uuid})
	err = stub.PutState(compositeKey2, acctflowBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db error by compositeKey: %s, because %s", compositeKey2, err.Error()))
	}

	return shim.Success(nil)
}

// 查询
func (a *AcctFlowSmartContract) query(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 3 {
		return shim.Error("Invalid number of arguments. Expecting 3.")
	}

	var iteratorResult shim.StateQueryIteratorInterface
	// []string{"loanNumber", "happenDate", "uuid"}
	if args[2] != "" { // 根据uuid主键查询
		iteratorResult, _ = stub.GetStateByPartialCompositeKey("uuid", []string{args[2]})
	} else {
		if args[0] != "" {
			if args[1] != "" { // 贷款编号和发生日期联合查询
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-happenDate", []string{args[0], args[1]})
			} else { // 只按贷款编号查询
				iteratorResult, _ = stub.GetStateByPartialCompositeKey("loanNumber-happenDate", []string{args[0]})
			}
		} else if args[1] != "" { // 只按发生日期查询
			iteratorResult, _ = stub.GetStateByPartialCompositeKey("happenDate", []string{args[1]})
		} else {
			return shim.Error("Incorrect arguments. every argument is empty")
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

/**
 * 计算传入参数的哈希值
 */
func calculateSha1(data string) string {
	hash := sha1.New()
	io.WriteString(hash, data)
	return fmt.Sprintf("%x", hash.Sum(nil))
}

func main() {
	//id := util.TimeUUID()
	//fmt.Printf("uuid = %s\n", id.String())
	err := shim.Start(new(AcctFlowSmartContract))
	fmt.Printf("Error starting AcctFlowSmartContract chaincode: %s", err.Error())
}
