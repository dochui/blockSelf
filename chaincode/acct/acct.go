package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"fmt"
	"encoding/json"
	"bytes"
)

// 定义智能合约
type AcctSmartContract struct {

}

// 定义日总额核对台帐
type Acct struct {
	ReconciliationDate string `json:"reconciliationDate"`//对账日期
	LastLoanPrincipalBalance float64 `json:"lastLoanPrincipalBalance"`//上日贷款本金余额
	DayIssuePrincipalTotal float64 `json:"dayIssuePrincipalTotal"`//当日发放本金总额
	DayRePrincipalTotal float64 `json:"dayRePrincipalTotal"`//当日回收本金总额
	DayLoanPrincipalTotal float64 `json:"dayLoanPrincipalTotal"`//当日贷款本金余额
	LastComeInterestBalance float64 `json:"lastComeInterestBalance"` //上日应收利息余额
	DayPlanComeInterest float64 `json:"dayPlanComeInterest"`//当日计提应收利息
	DayComeInterest float64 `json:"dayComeInterest"`//当日回收利息
	DayComeInterestBalance float64 `json:"dayComeInterestBalance"`//当日应收利息余额
	LastComeRenaltyBalance float64 `json:"lastComeRenaltyBalance"`//上日应收罚息余额
	DayPlanComeRenalty float64 `json:"dayPlanComeRenalty"`//当日计提应收罚息
	DayComeRenalty float64 `json:"dayComeRenalty"`//当日回收罚息
	DayComeRenaltyBalance float64 `json:"dayComeRenaltyBalance"`//当日应收罚息余额
	DayReverseTotal	float64 `json:"dayReverseTotal"` 	   //当日冲销本金总额
	DayReverseInterest	float64 `json:"dayIssuePrincipalTotal"`   //当日冲销利息
	DayReverseRenalty        float64 `json:"dayRePrincipalTotal"`      //当日冲销罚息
	LastComePayNormalBalance float64 `json:"dayLoanPrincipalTotal"`    //上日应付正常费用余额
	DayComePayNormalCost  	 float64 `json:"lastComeInterestBalance"`  //当日应付正常费用
	DayBuckleNormalCost      float64 `json:"dayPlanComeInterest"`      //当日扣收正常费用
	DayComePayNormalCostBalance          float64 `json:"dayComeInterest"`          //当日应付正常费用余额
	DayReverseNormalCost   	 float64 `json:"dayComeInterestBalance"`   //当日冲销正常费用
	LastComePayArrearageCostBalance      float64 `json:"lastComeRenaltyBalance"`   //上日应付延滞费用余额
	DayComePayArrearageCost  float64 `json:"dayPlanComeRenalty"`       //当日应付延滞费用
	DayBuckleArrearageCost   float64 `json:"dayComeRenalty"`           //当日扣收延滞费用
	DayComePayArrearageCostBalance       float64 `json:"dayComeRenaltyBalance"`    //当日应付延滞费用余额
	DayReverseArrearageCost  float64 `json:"dayComeRenaltyBalance"`    //当日冲销延滞费用
}

// 初始化
func (a *AcctSmartContract) Init(stub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}

// 调用
func (a *AcctSmartContract) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	function, args := stub.GetFunctionAndParameters()
	if function == "insert" {// 插入
		return a.insert(stub, args)
	} else if function == "query" {// 查询
		return a.query(stub, args)
	}

	return shim.Error("Invalid AcctSmartContract function name")
}

// 入链
func (a *AcctSmartContract) insert(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. expecting 2")
	}

	var acct Acct
	acctBytes := []byte(args[1])
	err := json.Unmarshal(acctBytes, &acct)
	if err != nil {
		return shim.Error(fmt.Sprintf("Unmarshal failed because %s", err.Error()))
	}

	// 定义一个复合主键，里面包含所属的机构及其台帐日期
	compositeKey, _ := stub.CreateCompositeKey("orgName-acctDate", []string{args[0], acct.ReconciliationDate})

	err = stub.PutState(compositeKey, acctBytes)
	if err != nil {
		return shim.Error(fmt.Sprintf("put state db failed by compositeKey: %s, because %s", compositeKey, err.Error()))
	}

	return shim.Success(nil)
}

// 查询
func (a *AcctSmartContract) query(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. expecting 2")
	}

	if args[0] == "" {// 目前只能够查询某个机构下的日总额核对台帐，暂不支持根据日期查某些机构的日总额核对台帐
		return shim.Error("Invalid argument, args[0] cannot be null.")
	}

	var iteratorResult shim.StateQueryIteratorInterface
	// 构造复合主键去查询日总额核对台帐
	if args[1] != "" {
		iteratorResult, _ = stub.GetStateByPartialCompositeKey("orgName-acctDate", []string{args[0], args[1]})
	} else {
		iteratorResult, _ = stub.GetStateByPartialCompositeKey("orgName-acctDate", []string{args[0]})
	}

	result, err := getIteratorResult(iteratorResult)
	if err != nil {
		return shim.Error(fmt.Sprintf("invoke function getIteratorResult error: %s", err.Error()))
	}

	return shim.Success(result)
}

func getIteratorResult(iteratorResult shim.StateQueryIteratorInterface) ([]byte, error) {
	defer iteratorResult.Close()

	var buffer bytes.Buffer
	buffer.WriteString("[")

	for iteratorResult.HasNext() {// 遍历结果集
		queryResult, err := iteratorResult.Next()
		if err != nil {
			return nil, err
		}

		// 将查询到的结果追加至buffer中
		buffer.WriteString(string(queryResult.Value))
		if iteratorResult.HasNext() {// 如果还有下一个结果，则追加一个','号
			buffer.WriteString(",")
		}
	}

	buffer.WriteString("]")
	return buffer.Bytes(), nil
}

func main() {
	err := shim.Start(new(AcctSmartContract))
	if err != nil {
		fmt.Printf("Error starting AcctSmartContract chaincode: %s", err)
	}
}