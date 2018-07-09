package com.ynet.xwfabric.domain;

import java.util.List;


public class Assert {

    private LoanDetail loanDetailInfo; //贷款明细相关信息
    private Customer customerInfo;    //客户相关信息
    private PayPlan payPlanInfo;    //还款计划相关信息
    private List<PayLog> payLogListInfo; //还款明细相关信息
    private List<SpecialBusiness> specialBusinessListInfo;//特殊交易相关
    private Document documentInfo;//文件相关hash

    public LoanDetail getLoanDetailInfo() {
        return loanDetailInfo;
    }

    public void setLoanDetailInfo(LoanDetail loanDetailInfo) {
        this.loanDetailInfo = loanDetailInfo;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public PayPlan getPayPlanInfo() {
        return payPlanInfo;
    }

    public void setPayPlanInfo(PayPlan payPlanInfo) {
        this.payPlanInfo = payPlanInfo;
    }

    public List<PayLog> getPayLogListInfo() {
        return payLogListInfo;
    }

    public void setPayLogListInfo(List<PayLog> payLogListInfo) {
        this.payLogListInfo = payLogListInfo;
    }

    public List<SpecialBusiness> getSpecialBusinessListInfo() {
        return specialBusinessListInfo;
    }

    public void setSpecialBusinessListInfo(
            List<SpecialBusiness> specialBusinessListInfo) {
        this.specialBusinessListInfo = specialBusinessListInfo;
    }

    public Document getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(Document documentInfo) {
        this.documentInfo = documentInfo;
    }


}
