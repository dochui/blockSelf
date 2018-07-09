package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 扣费明细
 * @author chengcaiyi
 *
 */
public class Fee implements Serializable{

	private static final long serialVersionUID = 1L;

	private String dueDate; // 应扣费日期
	private BigDecimal payableAmount;//应扣费金额
	private String actualTransactionDate;//实际扣费日期
	private BigDecimal actualDeductionAmount;//实际扣费金额
	private String deductionStatus;//扣费状态 1-成功 0-失败
	private String transactionSerialNo; //交易流水号
	private String deductionRule;//扣费规则 ID
	private String deductionRuleName;//扣费规则名称
	private String deductionRuleCode;//扣费规则编码
	private BigDecimal basedOn;//计费基础
	private BigDecimal rate;//费率

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getActualTransactionDate() {
		return actualTransactionDate;
	}

	public void setActualTransactionDate(String actualTransactionDate) {
		this.actualTransactionDate = actualTransactionDate;
	}

	public BigDecimal getActualDeductionAmount() {
		return actualDeductionAmount;
	}

	public void setActualDeductionAmount(BigDecimal actualDeductionAmount) {
		this.actualDeductionAmount = actualDeductionAmount;
	}

	public String getDeductionStatus() {
		return deductionStatus;
	}

	public void setDeductionStatus(String deductionStatus) {
		this.deductionStatus = deductionStatus;
	}

	public String getTransactionSerialNo() {
		return transactionSerialNo;
	}

	public void setTransactionSerialNo(String transactionSerialNo) {
		this.transactionSerialNo = transactionSerialNo;
	}

	public String getDeductionRule() {
		return deductionRule;
	}

	public void setDeductionRule(String deductionRule) {
		this.deductionRule = deductionRule;
	}

	public String getDeductionRuleName() {
		return deductionRuleName;
	}

	public void setDeductionRuleName(String deductionRuleName) {
		this.deductionRuleName = deductionRuleName;
	}

	public String getDeductionRuleCode() {
		return deductionRuleCode;
	}

	public void setDeductionRuleCode(String deductionRuleCode) {
		this.deductionRuleCode = deductionRuleCode;
	}

	public BigDecimal getBasedOn() {
		return basedOn;
	}

	public void setBasedOn(BigDecimal basedOn) {
		this.basedOn = basedOn;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
