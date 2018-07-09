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
type LoanDetail struct {
	AccountState         string   `json:"accountState"`
	BorrowerType 		 string   `json:"borrowerType"`
	ContractNo    		 string   `json:"contractNo"`
	CustomerName  		 string   `json:"customerName"`
	CustomerNo   		 string   `json:"customerNo"`
	DebitInterestAmount  float64  `json:"debitInterestAmount"`
	DelayBalance         float64  `json:"delayBalance"`
	FirstPayDate         string   `json:"firstPayDate"`
	GuaranteeMethod      string   `json:"guaranteeMethod"`
	LoanAmount           float64  `json:"loanAmount"`
	LoanDate             string   `json:"loanDate"`
	LoanEndDate          string   `json:"loanEndDate"`
	LoanNumber           string   `json:"loanNumber"`
	LoanPurpose          string   `json:"loanPurpose"`
	LoanType             string   `json:"loanType"`
	MaxDelayDays         int      `json:"maxDelayDays"`
	NormalBalance        float64  `json:"normalBalance"`
	PayMethod            string   `json:"payMethod"`
	PenaltyAmount        float64  `json:"penaltyAmount"`
	PenaltyRate          float64  `json:"penaltyRate"`
	Periods              int      `json:"periods"`
	ProductName          string   `json:"productName"`
	ProductNo            string   `json:"productNo"`
	Rate                 float64  `json:"rate"`
	RateType             string   `json:"rateType"`
	ReceiverBankNo       string   `json:"receiverBankNo"`
	ReceiverBankType     string   `json:"receiverBankType"`
	ReceiverName         string   `json:"receiverName"`
	RepaidPeriod         int   	  `json:"repaidPeriod"`
	RepaymentPeriod      string   `json:"repaymentPeriod"`
	SettleOnDate         string   `json:"settleOnDate"`
	StopInterestFlag     string   `json:"stopInterestFlag"`
	TransactionDate      string   `json:"transactionDate"`
	TransactionFlowNo    string   `json:"transactionFlowNo"`
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

	var loanDetail LoanDetail
	loanDetailBytes := []byte(args[0])
	err := json.Unmarshal(loanDetailBytes, &loanDetail)
	if err != nil {
		return shim.Error(fmt.Sprintf("json unmarshal failed because: %s", err.Error()))
	}

	err = APIStub.PutState(loanDetail.LoanNumber, loanDetailBytes)
	if err != nil {
		return shim.Error(err.Error())
	}

	compositeKey3, _ := APIStub.CreateCompositeKey("loandetail", []string{loanDetail.LoanDate, loanDetail.LoanNumber})
	err = APIStub.PutState(compositeKey3, loanDetailBytes)
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
		rqi, _ := APIStub.GetStateByPartialCompositeKey("loandetail", []string{args[1]})
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