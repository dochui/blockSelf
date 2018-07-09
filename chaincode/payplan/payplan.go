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
type PayLoanItem struct {
	DelayAmount         float64   	`json:"delayAmount"`
	DelayDays           int   		`json:"delayDays"`
	Flag                string   	`json:"flag"`
	Interest            float64   	`json:"interest"`
	InterestRate        float64   	`json:"interestRate"`
	PayAmount           float64   	`json:"payAmount"`
	PayDate             string   	`json:"payDate"`
	Penalty             float64   	`json:"penalty"`
	Period              int   		`json:"period"`
	Periods             int   		`json:"periods"`
	PlanInterest        float64   	`json:"planInterest"`
	PlanPayDate         string   	`json:"planPayDate"`
	PlanPrincipal       float64   	`json:"planPrincipal"`
	Principal           float64   	`json:"principal"`
	RealInterest        float64   	`json:"realInterest"`
	RealPenalty         float64   	`json:"realPenalty"`
	RealPrincipal       float64   	`json:"realPrincipal"`
	RepaymentDate       string   	`json:"repaymentDate"`
	ResidualPrincipal   float64   	`json:"residualPrincipal"`
	StartDate           string   	`json:"startDate"`
	State               string   	`json:"state"`
	TotalAmount         float64   	`json:"totalAmount"`
	TransactionDate     string   	`json:"transactionDate"`
}

type PayPlan struct {
	LoanNumber          string   			`json:"loanNumber"`
	TransactionDate	    string   			`json:"transactionDate"`
	PayLoanItemList 	[]PayLoanItem		`json:"payLoanItemList"`
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

	var payPlan PayPlan
	payPlanBytes := []byte(args[0])
	err := json.Unmarshal(payPlanBytes, &payPlan)
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because: %s", err.Error()))
	}

	err = APIStub.PutState(payPlan.LoanNumber, payPlanBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	compositeKey3, _ := APIStub.CreateCompositeKey("payplan", []string{payPlan.TransactionDate, payPlan.LoanNumber})
	err = APIStub.PutState(compositeKey3, payPlanBytes)
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
		rqi, _ := APIStub.GetStateByPartialCompositeKey("payplan", []string{args[1]})
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
