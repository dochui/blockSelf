package main

import (
	"testing"

	"encoding/json"
	"fmt"
)

func TestUnmarshal(t *testing.T) {
	var payPlan PayPlan
	loanDetailBytes := []byte("{\"loanNumber\":\"20171219171911326326210858384086\",\"payLoanItemList\":[{\"delayAmount\":0.00,\"delayDays\":0,\"flag\":\"1\",\"interest\":743.90,\"interestRate\":0.00018330,\"payAmount\":4543.90,\"payDate\":\"2017/12/27\",\"penalty\":0.00,\"period\":1,\"periods\":1,\"planInterest\":743.90,\"planPayDate\":\"2020/11/28\",\"planPrincipal\":3800.00,\"principal\":3800.00,\"realInterest\":0.70,\"realPenalty\":0.00,\"realPrincipal\":3800.00,\"repaymentDate\":\"2020/11/28\",\"residualPrincipal\":0.00,\"startDate\":\"2017/12/26\",\"state\":\"2\",\"totalAmount\":4543.90,\"transactionDate\":\"2017/12/26\"}]} ")
	err := json.Unmarshal(loanDetailBytes, &payPlan)
	if err != nil {
		fmt.Println("unmarshal failed:%s", err.Error())
		t.FailNow()
	}
}
