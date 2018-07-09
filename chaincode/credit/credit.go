package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"encoding/json"
	"bytes"
)
// Define the Smart Contract structure
type SmartContract struct {
}


//Credit
type Credit struct {
	CustomerNo           string 	`json:"customerNo"`
	CreditNum            string 	`json:"creditNum"`
	CreditType           string 	`json:"creditType"`
	EffectiveCredit      float64    `json:"effectiveCredit"`
	UsedCredit           float64 	`json:"float64"`
	NoUsedAmount         float64 	`json:"noUsedAmount"`
	PayNoComingAmount    float64 	`json:"payNoComingAmount"`
	ExpirationDate       string 	`json:"expirationDate"`
	CreditStartDate      string 	`json:"creditStartDate"`
	ContractNo           string 	`json:"contractNo"`
}

func (s *SmartContract) Init(APIStub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIStub shim.ChaincodeStubInterface) peer.Response {

	function, args := APIStub.GetFunctionAndParameters()
	if function == "insert" {
		return s.insert(APIStub, args)
	} else if function == "query" {
		return s.query(APIStub, args)
	}

	return shim.Error("Invalid Smart Contract function name.")
}
//insert assert
func (s *SmartContract) insert(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}
	var credit Credit
	creditBytes := []byte(args[0])
	err := json.Unmarshal(creditBytes, &credit)
	if err != nil {
		return shim.Error(fmt.Sprintf("assert unmarshal failed because: %s", err.Error()))
	}
	compositeKey, _ := APIStub.CreateCompositeKey("credit", []string{credit.CustomerNo, credit.CreditType})
	err = APIStub.PutState(compositeKey, creditBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	compositeKey1, _ := APIStub.CreateCompositeKey("credit1", []string{credit.CreditType,credit.CustomerNo})
	err = APIStub.PutState(compositeKey1, creditBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}
//query assert(args1-type args2-loanNumber args3-Date)
func (s *SmartContract) query(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	var buf bytes.Buffer
	buf.WriteString("[")
	if args[0] == "" {
		rqi, _ := APIStub.GetStateByPartialCompositeKey("credit1", []string{args[1]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
	}else if(args[0] != "" && args[1]==""){
		rqi, _ := APIStub.GetStateByPartialCompositeKey("credit", []string{args[0]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
	}else{
		rqi, _ := APIStub.GetStateByPartialCompositeKey("credit", []string{args[0],args[1]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
	}
	buf.WriteString("]")
	return shim.Success(buf.Bytes())

}
func main() {

	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}


