package main

import (
	"testing"

	"encoding/json"
	"fmt"
)

func TestUnmarshal(t *testing.T) {
	var loanDetail LoanDetail
	loanDetailBytes := []byte("{\"accountState\":\"4\",\"borrowerType\":\"07\",\"contractNo\":\"TTTLRR03\",\"customerName\":\"好着急\",\"customerNo\":\"0000001000084653\",\"debitInterestAmount\":0.00,\"delayBalance\":0.00,\"firstPayDate\":\"2017/06/08\",\"guaranteeMethod\":\"4\",\"loanAmount\":1404.80,\"loanDate\":\"2017/06/08\",\"loanEndDate\":\"2018/05/08\",\"loanNumber\":\"20170607171137835606929584404492\",\"loanPurpose\":\"9\",\"loanType\":\"03\",\"maxDelayDays\":0,\"normalBalance\":1404.80,\"payMethod\":\"02\",\"penaltyAmount\":0.00,\"penaltyRate\":0.0002667,\"periods\":12,\"productName\":\"好人贷-ML - 车款分期\",\"productNo\":\"F021009002008001\",\"rate\":0.00017780,\"rateType\":\"01\",\"receiverBankNo\":\"9010002010000290\",\"receiverBankType\":\"新网银行\",\"receiverName\":\"好着急\",\"repaidPeriod\":0,\"repaymentPeriod\":\"0\",\"settleOnDate\":\"\",\"stopInterestFlag\":\"0\",\"transactionDate\":\"2017/06/08\",\"transactionFlowNo\":\"0\"}")
	err := json.Unmarshal(loanDetailBytes, &loanDetail)
	if err != nil {
		fmt.Println("unmarshal failed:%s", err.Error())
		t.FailNow()
	}
}
