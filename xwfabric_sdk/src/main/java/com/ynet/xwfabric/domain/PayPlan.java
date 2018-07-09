package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 贷款归还计划
 * @author chengcaiyi
 * @date 2018年2月22日 上午11:05:17
 */
public class PayPlan implements Serializable {

	private static final long serialVersionUID = 1L;
	private String loanNumber; // 借款编号
	private String transactionDate;//交易日期
	private List<PayPlanItem> payPlanItemList; // 还款计划

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public List<PayPlanItem> getPayPlanItemList() {
		return payPlanItemList;
	}

	public void setPayPlanItemList(List<PayPlanItem> payPlanItemList) {
		this.payPlanItemList = payPlanItemList;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

}
