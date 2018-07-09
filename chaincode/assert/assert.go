package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"encoding/json"
	"bytes"
	"strconv"
)
// Define the Smart Contract structure
type SmartContract struct {
}
//loanDetail
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
	UpdateFlag	     string   `json:"updateFlag"`
	TransactionFlowNo    string   `json:"transactionFlowNo"`
}

//PayLoanItem
type PayPlanItem struct {
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
//PayPlan
type PayPlan struct {
	LoanNumber          string   			`json:"loanNumber"`
	TransactionDate	    string   			`json:"transactionDate"`
	PayPlanItemList 	[]PayPlanItem		`json:"payPlanItemList"`
}
//SpecialBusiness
type SpecialBusiness struct {
	BusinessAmount          float64 	`json:"businessAmount"`
	BusinessType            string 		`json:"businessType"`
	ChangePeriod           	int 		`json:"changePeriod"`
	DetailInfo              string 		`json:"detailInfo"`
	HappenDate            	string 		`json:"happenDate"`
	LoanNumber      		string 		`json:"loanNumber"`
	PayPlanChangeFlag      	string 		`json:"payPlanChangeFlag"`
}

//PayLog
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
//Customer
type Customer struct {
	CustomerNo         string   `json:"customerNo"`
	CertificateType    string   `json:"certificateType"`
	CertificateNum     string   `json:"certificateNum"`
	Name  		   string   `json:"name"`
	Sex   		   string   `json:"sex"`
	Birthday           string   `json:"birthday"`
	phone              string   `json:"phone"`
	Address            string   `json:"address"`
	District           string   `json:"district"`
	City               string   `json:"city"`
	Province           string   `json:"province"`
	LinkMan            string   `json:"linkMan"`
	LinkManRelation    string   `json:"linkManRelation"`
	LinkManPhone       string   `json:"linkManPhone"`
	CompanyName        string   `json:"companyName"`
	CompanyType        string   `json:"companyType"`
	CompanyAddress     string   `json:"companyAddress"`
	CompanyDistrict    string   `json:"companyDistrict"`
	CompanyCity        string   `json:"companyCity"`
	CompanyProvince    string   `json:"companyProvince"`
	AddressCode        string   `json:"addressCode"`
	HomeAddress        string   `json:"homeAddress"`
	CommunicationCode  string   `json:"communicationCode"`
	CompanyCode        string   `json:"companyCode"`
	MaritaStatus       string   `json:"maritaStatus"`
	Education          string   `json:"education"`
	Income             float64  `json:"income"`
	Mobile             string   `json:"mobile"`
	Job                string   `json:"job"`
	JobTitle           string   `json:"jobTitle"`
	JobLeval           string   `json:"jobLeval"`
	UpdateFlag         string   `json:"updateFlag"`
	CardAddress        string   `json:"cardAddress"`
	CardStartDate      string   `json:"cardStartDate"`
	CardEndDate        string   `json:"cardEndDate"`
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
//Document hash
type Document struct {
	Hash    string  `json:"hash"`
}

//Assert
type Assert struct {
	LoanDetailInfo  LoanDetail   `json:"loanDetailInfo"`
	PayPlanInfo     PayPlan	     `json:"payPlanInfo"`
	DocumentInfo    Document     `json:"documentInfo"`
	CreditInfo      Credit	     `json:"creditInfo"`
	CustomerInfo    Customer     `json:"customerInfo"`
	PayLogListInfo      []PayLog	     `json:"payLogListInfo"`
	SpecialBusinessListInfo     []SpecialBusiness	     `json:"specialBusinessListInfo"`

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

	var assert Assert
	assertBytes := []byte(args[0])
	err := json.Unmarshal(assertBytes, &assert)
	if err != nil {
		return shim.Error(fmt.Sprintf("assert unmarshal failed because: %s", err.Error()))
	}

	//compositeKey, _ := APIStub.CreateCompositeKey("customerNO", []string{assert.LoanDetailInfo.CustomerNo,assert.LoanDetailInfo.LoanNumber})

	//1........loanDetail key
	var loanDetail LoanDetail
	var loanDetailJson []byte
	if &assert.LoanDetailInfo != nil{
		loanDetail = assert.LoanDetailInfo
		loanDetailJson , err = json.Marshal(&loanDetail)
		if err != nil {
			return shim.Error(fmt.Sprintf("loanDetailJson Marshal failed because: %s", err.Error()))
		}

		err = APIStub.PutState(loanDetail.LoanNumber, assertBytes)
		if err != nil {
			return shim.Error(err.Error())
		}

		compositeKey, _ := APIStub.CreateCompositeKey("assert2", []string{loanDetail.LoanDate,loanDetail.LoanNumber})
		err = APIStub.PutState(compositeKey, assertBytes)
		if err != nil {
			return shim.Error(err.Error())
		}
		//compositeKey0, _ := APIStub.CreateCompositeKey("assert3", []string{loanDetail.CustomerNo,loanDetail.LoanNumber})
		//err = APIStub.PutState(compositeKey0, assertBytes)
		//if err != nil {
		//	return shim.Error(err.Error())
		//}

		compositeKey1, _ := APIStub.CreateCompositeKey("assert", []string{loanDetail.LoanNumber,"loandetail"})

		compositeKey11, _ := APIStub.CreateCompositeKey("assert1", []string{loanDetail.LoanDate,"loandetail",loanDetail.LoanNumber})

		err = APIStub.PutState(compositeKey1, loanDetailJson)
		if err != nil{
			return shim.Error(err.Error())
		}
		err = APIStub.PutState(compositeKey11, loanDetailJson)
		if err != nil{
			return shim.Error(err.Error())
		}
	}

	//2.......payPlan
	var payPlan PayPlan
	var payPlanJson []byte
	if &assert.PayPlanInfo != nil{
		payPlan = assert.PayPlanInfo
		payPlanJson,err = json.Marshal(&payPlan)
		if err != nil {
			return shim.Error(fmt.Sprintf("payPlanJson Marshal failed because: %s", err.Error()))
		}
		compositeKey2, _ := APIStub.CreateCompositeKey("assert", []string{payPlan.LoanNumber,"payplan"})
		compositeKey22, _ := APIStub.CreateCompositeKey("assert1", []string{payPlan.TransactionDate,"payplan",payPlan.LoanNumber})

		err = APIStub.PutState(compositeKey2, payPlanJson)
		if err != nil {
			return shim.Error(err.Error())
		}
		err = APIStub.PutState(compositeKey22, payPlanJson)
		if err != nil {
			return shim.Error(err.Error())
		}
	}

	//3.......payLog
	var payLog []PayLog
	var payLogJson []byte
	if (&assert.PayLogListInfo != nil && len(assert.PayLogListInfo)> 0){
		payLog = assert.PayLogListInfo
		payLogJson,err = json.Marshal(&payLog)
		if err != nil {
			return shim.Error(fmt.Sprintf("payLogJson Marshal failed because: %s", err.Error()))
		}
		compositeKey3, _ := APIStub.CreateCompositeKey("assert", []string{payLog[0].LoanNumber,"paylog"})
		err = APIStub.PutState(compositeKey3, payLogJson)
		if err != nil {
			return shim.Error(err.Error())
		}
		var size = len(payLog)
		for i := 0; i < size; i++{
			str := strconv.Itoa(i)
			var compositeKey33 = compositeKey3+str
			compositeKey33,_ = APIStub.CreateCompositeKey("assert1", []string{payLog[i].TransactionDate,"paylog",str,payLog[i].LoanNumber})
			err = APIStub.PutState(compositeKey33, payLogJson)
			if err != nil {
				return shim.Error(err.Error())
			}
		}
	}

	//4.........Customer
	//var customer Customer
	//var customerJson []byte
	//if &assert.CustomerInfo != nil{
	//	customer = assert.CustomerInfo
	//	customerJson,err = json.Marshal(&customer)
	//	if err != nil {
	//		return shim.Error(fmt.Sprintf("customerJson Marshal failed because: %s", err.Error()))
	//	}
	//	compositeKey4, _ := APIStub.CreateCompositeKey("assert", []string{assert.LoanDetailInfo.LoanNumber,"customer",assert.LoanDetailInfo.LoanNumber})
	//	compositeKey44, _ := APIStub.CreateCompositeKey("assert1", []string{assert.LoanDetailInfo.LoanDate,"customer",loanDetail.LoanNumber})
	//	err = APIStub.PutState(compositeKey4, customerJson)
	//	if err != nil {
	//		return shim.Error(err.Error())
	//	}
	//	err = APIStub.PutState(compositeKey44, customerJson)
	//	if err != nil {
	//		return shim.Error(err.Error())
	//	}
	//}
	//5.........specialBusiness
	var specialBusiness []SpecialBusiness
	var specialBusinessJson []byte

	if (&assert.SpecialBusinessListInfo != nil && len(assert.SpecialBusinessListInfo) >0 ){
		specialBusiness = assert.SpecialBusinessListInfo
		specialBusinessJson,err = json.Marshal(&specialBusiness)
		if err != nil {
			return shim.Error(fmt.Sprintf("specialBusinessJson Marshal failed because: %s", err.Error()))
		}
		compositeKey5, _ := APIStub.CreateCompositeKey("assert", []string{specialBusiness[0].LoanNumber,"specialBusiness"})
		err = APIStub.PutState(compositeKey5, specialBusinessJson)
		if err != nil {
			return shim.Error(err.Error())
		}
		var size = len(specialBusiness)
		for i := 0; i < size; i++{
			str := strconv.Itoa(i)
			var scompositeKey = compositeKey5+str
			scompositeKey, _ = APIStub.CreateCompositeKey("assert1", []string{specialBusiness[i].HappenDate,"specialBusiness",specialBusiness[i].LoanNumber })
			err = APIStub.PutState(scompositeKey, specialBusinessJson)
			if err != nil {
				return shim.Error(err.Error())
			}
		}
	}
	//6.........Credit
	//var credit Credit
	//var creditJson []byte
	//if &assert.CreditInfo != nil{
	//	credit = assert.CreditInfo
	//	creditJson,err = json.Marshal(&credit)
	//	if err != nil {
	//		return shim.Error(fmt.Sprintf("creditJson Marshal failed because: %s", err.Error()))
	//	}
	//	compositeKey6, _ := APIStub.CreateCompositeKey("assert", []string{assert.LoanDetailInfo.LoanNumber,"credit"})
	//	//compositeKey66, _ := APIStub.CreateCompositeKey("assert1", []string{assert.LoanDetailInfo.LoanDate,"credit",assert.LoanDetailInfo.LoanNumber})
	//
	//	err = APIStub.PutState(compositeKey6, creditJson)
	//	if err != nil {
	//		return shim.Error(err.Error())
	//	}
		//err = APIStub.PutState(compositeKey66, creditJson)
		//if err != nil {
		//	return shim.Error(err.Error())
		//}

	//}
	//7.........Document
	//var document Document
	//var documentJson []byte
	//if &assert.DocumentInfo != nil{
	//	document = assert.DocumentInfo
	//	documentJson,err = json.Marshal(&document)
	//	if err != nil {
	//		return shim.Error(fmt.Sprintf("documentJson Marshal failed because: %s", err.Error()))
	//	}
	//	compositeKey7, _ := APIStub.CreateCompositeKey("assert", []string{assert.LoanDetailInfo.LoanNumber,"document"})
	//	//compositeKey77, _ := APIStub.CreateCompositeKey("assert1", []string{assert.LoanDetailInfo.LoanDate,"document",assert.LoanDetailInfo.LoanNumber})
	//
	//	err = APIStub.PutState(compositeKey7, documentJson)
	//	if err != nil {
	//		return shim.Error(err.Error())
	//	}
	//	//err = APIStub.PutState(compositeKey77, documentJson)
	//	//if err != nil {
	//	//	return shim.Error(err.Error())
	//	//}
	//}
	return shim.Success(nil)
}
//query assert(args1-type args2-loanNumber args3-Date)
func (s *SmartContract) query(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}
	if args[1] == "" {
		if args[0] == "1" {
			return s.selector(APIStub,[]string{args[2],"loandetail"})
		} else if args[0] == "2" {
			return s.selector(APIStub,[]string{args[2],"payplan"})
		}else if args[0] == "3"{
			return s.selector(APIStub,[]string{args[2],"paylog"})
		}else if args[0] == "4"{
			return s.selector(APIStub,[]string{args[2],"specialBusiness"})
		}else if args[0] == "5"{
			return s.selector(APIStub,[]string{args[2],"document"})
		}else if args[0] == "6"{
			return s.selector(APIStub,[]string{args[2],"credit"})
		}else if args[0] == "7"{
			return s.selector(APIStub,[]string{args[2],"customer"})
		}else if args[0] == "8"{
			return s.selector2(APIStub,[]string{args[2]})
		}
	}else{
		if args[0] == "1" {
			return s.selector1(APIStub,[]string{args[1],"loandetail"})
		} else if args[0] == "2" {
			return s.selector1(APIStub,[]string{args[1],"payplan"})
		}else if args[0] == "3"{
			return s.selector1(APIStub,[]string{args[1],"paylog"})
		}else if args[0] == "4"{
			return s.selector1(APIStub,[]string{args[1],"specialBusiness"})
		}else if args[0] == "5"{
			return s.selector1(APIStub,[]string{args[1],"document"})
		}else if args[0] == "6"{
			return s.selector1(APIStub,[]string{args[1],"credit"})
		}else if args[0] == "7"{
			return s.selector1(APIStub,[]string{args[1],"customer"})
		}else if args[0] == "8"{
			results, err := APIStub.GetState(args[1])
			if err != nil {
				return shim.Error(err.Error())
			}
			return shim.Success(results)
		}
	}
	return shim.Success(nil)
}
func (s *SmartContract)selector(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	var buf bytes.Buffer
	buf.WriteString("[")
	rqi, _ := APIStub.GetStateByPartialCompositeKey("assert1", args)
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
func (s *SmartContract)selector1(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	var buf bytes.Buffer
	buf.WriteString("[")
	rqi, _ := APIStub.GetStateByPartialCompositeKey("assert", args)
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
func (s *SmartContract)selector2(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
	var buf bytes.Buffer
	buf.WriteString("[")
	rqi, _ := APIStub.GetStateByPartialCompositeKey("assert2", args)
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
//func (s *SmartContract)selector3(APIStub shim.ChaincodeStubInterface, args []string) peer.Response {
//	var buf bytes.Buffer
//	buf.WriteString("[")
//	rqi, _ := APIStub.GetStateByPartialCompositeKey("assert3", args)
//	for rqi.HasNext() {
//		response, err := rqi.Next()
//		if err == nil {
//			buf.Write(response.Value)
//			if rqi.HasNext() {
//				buf.WriteString(",")
//			}
//		}
//	}
//	buf.WriteString("]")
//	return shim.Success(buf.Bytes())
//}
func main() {

	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}

