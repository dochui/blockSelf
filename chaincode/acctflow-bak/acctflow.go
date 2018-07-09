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

// Define the car structure, with 4 properties.  Structure tags are used by encoding/json library
type AcctFlow struct {
	BranchNo          string 		`json:"branchNo"`
	BusinessAmount    float64 		`json:"businessAmount"`
	BusinessCode      string 		`json:"businessCode"`
	BusinessDesc      string 		`json:"businessDesc"`
	Currency          string 		`json:"currency"`
	HappenDate        string 		`json:"happenDate"`
	LoanFlag      	  string 		`json:"loanFlag"`
	LoanNumber        string 		`json:"loanNumber"`
	ProductNo      	  string 		`json:"productNo"`
	Type      		  string 		`json:"type"`
}


func (s *SmartContract) Init(APIStub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIStub shim.ChaincodeStubInterface) peer.Response {

	// Retrieve the requested Smart Contract function and arguments
	function, args := APIStub.GetFunctionAndParameters()
	// Route to the appropriate handler function to interact with the ledger appropriately
	if function == "insert" {
		return s.insert(APIStub, args)
	} else if function == "query" {
		return s.query(APIStub, args)
	}

	return shim.Error("Invalid Smart Contract function name.")
}

func (s *SmartContract) insert(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	var acctFlow AcctFlow
	acctFlowBytes := []byte(args[0])
	err := json.Unmarshal(acctFlowBytes, &acctFlow)
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because: %s", err.Error()))
	}

	//err = APIStub.PutState(acctFlow.LoanNumber, acctFlowBytes)
	//if err != nil {
	//	return shim.Error(err.Error())
	//}

	id := fmt.Sprintf("%s_%s_%s_%s_%s", acctFlow.LoanNumber, acctFlow.HappenDate, acctFlow.BusinessCode, acctFlow.LoanFlag, acctFlow.Type)

	compositeKey3, _ := APIStub.CreateCompositeKey("acctFlowDate", []string{acctFlow.HappenDate, id})
	err = APIStub.PutState(compositeKey3, acctFlowBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	compositeKey4, _ := APIStub.CreateCompositeKey("acctFlow", []string{acctFlow.LoanNumber, id})
	err = APIStub.PutState(compositeKey4, acctFlowBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

func (s *SmartContract) query(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	var buf bytes.Buffer
	buf.WriteString("[")
	if args[0] == "" {
		rqi, _ := APIStub.GetStateByPartialCompositeKey("acctFlowDate", []string{args[1]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
		buf.WriteString("]")
		return shim.Success(buf.Bytes())
	} else {
		rqi, _ := APIStub.GetStateByPartialCompositeKey("acctFlow", []string{args[0]})
		for rqi.HasNext() {
			response, err := rqi.Next()
			if err == nil {
				buf.Write(response.Value)
				if rqi.HasNext() {
					buf.WriteString(",")
				}
			}
		}
		buf.WriteString("]")
		return shim.Success(buf.Bytes())
	}

}

// The main function is only relevant in unit test mode. Only included here for completeness.
func main() {

	// Create a new Smart Contract
	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}