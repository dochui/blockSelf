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
type PayLog struct {
	EntryAmount          float64 	`json:"entryAmount"`
	EntryDate            string 	`json:"entryDate"`
	LoanNumber           string 	`json:"loanNumber"`
	Period               int 		`json:"period"`
	RepayFlag            string 	`json:"repayFlag"`
	TransactionCost      float64 	`json:"transactionCost"`
	TransactionDate      string 	`json:"transactionDate"`
	TransactionInterest  float64 	`json:"transactionInterest"`
	TransactionPenalty   float64 	`json:"transactionPenalty"`
	TransactionPrincipal float64 	`json:"transactionPrincipal"`
	TransactionSerialNo  string 	`json:"transactionSerialNo"`
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

	var payLog PayLog
	payLogBytes := []byte(args[0])
	err := json.Unmarshal(payLogBytes, &payLog)
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because: %s", err.Error()))
	}

	err = APIStub.PutState(payLog.TransactionSerialNo, payLogBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	compositeKey3, _ := APIStub.CreateCompositeKey("payLogDate", []string{payLog.TransactionDate, payLog.TransactionSerialNo})
	err = APIStub.PutState(compositeKey3, payLogBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	compositeKey4, _ := APIStub.CreateCompositeKey("payLog", []string{payLog.LoanNumber, payLog.TransactionSerialNo})
	err = APIStub.PutState(compositeKey4, payLogBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	return shim.Success(nil)
}

func (s *SmartContract) query(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	var buf bytes.Buffer
	buf.WriteString("[")
	if args[0] == "" {
		if args[1] == "" {
			rqi, _ := APIStub.GetStateByPartialCompositeKey("payLogDate", []string{args[2]})
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
			rqi, _ := APIStub.GetStateByPartialCompositeKey("payLog", []string{args[1]})
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
	} else {
		results, err := APIStub.GetState(args[0])
		if err != nil {
			return shim.Error(err.Error())
		}
		return shim.Success(results)
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