package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 还款明细
 * @author chengcaiyi
 *
 */
public class PayLog implements Serializable{

	private static final long serialVersionUID = 1L;
	 private String loanNumber ;//  借款编号
	 private String transactionDate ;//  交易日期
	 private BigDecimal transactionPrincipal ;//  交易本金
	 private BigDecimal transactionInterest ;//  交易利息
	 private BigDecimal transactionCost ;//  交易费用
	 private BigDecimal transactionPenalty ;//  交易罚息
	 private String entryDate ;//  入账日期
	 private BigDecimal entryAmount ;//  入账金额
	 private Integer period ;//  第几期
	 private String repayFlag ;//  自动还款标识
	 private String transactionSerialNo ;//  交易流水号
	public String getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionPrincipal() {
		return transactionPrincipal;
	}
	public void setTransactionPrincipal(BigDecimal transactionPrincipal) {
		this.transactionPrincipal = transactionPrincipal;
	}
	public BigDecimal getTransactionInterest() {
		return transactionInterest;
	}
	public void setTransactionInterest(BigDecimal transactionInterest) {
		this.transactionInterest = transactionInterest;
	}
	public BigDecimal getTransactionCost() {
		return transactionCost;
	}
	public void setTransactionCost(BigDecimal transactionCost) {
		this.transactionCost = transactionCost;
	}
	public BigDecimal getTransactionPenalty() {
		return transactionPenalty;
	}
	public void setTransactionPenalty(BigDecimal transactionPenalty) {
		this.transactionPenalty = transactionPenalty;
	}
	public BigDecimal getEntryAmount() {
		return entryAmount;
	}
	public void setEntryAmount(BigDecimal entryAmount) {
		this.entryAmount = entryAmount;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getRepayFlag() {
		return repayFlag;
	}
	public void setRepayFlag(String repayFlag) {
		this.repayFlag = repayFlag;
	}
	public String getTransactionSerialNo() {
		return transactionSerialNo;
	}
	public void setTransactionSerialNo(String transactionSerialNo) {
		this.transactionSerialNo = transactionSerialNo;
	}
	 
	 
	 
}
