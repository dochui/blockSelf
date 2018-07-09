package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 还款计划项
 * @author chengcaiyi
 * @date 2018年2月23日 上午10:09:49
 */
public class PayPlanItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer periods; // 期数
	private String repaymentDate; // 还款截至日期
	private String transactionDate; // 交易日期
	private BigDecimal payAmount; // 应还金额
	private BigDecimal planPrincipal; // 计划本金
	private BigDecimal planInterest; // 计划利息
	private String state; // 状态
	private Integer period; // 期数
	private Integer delayDays; // 本期延滞天数
	private BigDecimal totalAmount; // 当期总金额
	private BigDecimal principal; // 当期本金
	private BigDecimal interest; // 当期利息
	private BigDecimal delayAmount; // 当期逾期金额
	private BigDecimal penalty; // 当期罚息
	private BigDecimal realPrincipal; // 本期实还本金
	private BigDecimal realInterest; // 本期实还利息
	private String payDate; // 本期还款日期
	private BigDecimal realPenalty; // 本期实还罚息
	private String flag; // 本期结清标记
	private String planPayDate; // 计划还款日期
	private String startDate; // 本期开始日期
	private BigDecimal interestRate; // 本期利率
	private BigDecimal residualPrincipal; // 剩余本金

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getPlanPrincipal() {
		return planPrincipal;
	}

	public void setPlanPrincipal(BigDecimal planPrincipal) {
		this.planPrincipal = planPrincipal;
	}

	public BigDecimal getPlanInterest() {
		return planInterest;
	}

	public void setPlanInterest(BigDecimal planInterest) {
		this.planInterest = planInterest;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(Integer delayDays) {
		this.delayDays = delayDays;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getDelayAmount() {
		return delayAmount;
	}

	public void setDelayAmount(BigDecimal delayAmount) {
		this.delayAmount = delayAmount;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getRealPrincipal() {
		return realPrincipal;
	}

	public void setRealPrincipal(BigDecimal realPrincipal) {
		this.realPrincipal = realPrincipal;
	}

	public BigDecimal getRealInterest() {
		return realInterest;
	}

	public void setRealInterest(BigDecimal realInterest) {
		this.realInterest = realInterest;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getRealPenalty() {
		return realPenalty;
	}

	public void setRealPenalty(BigDecimal realPenalty) {
		this.realPenalty = realPenalty;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPlanPayDate() {
		return planPayDate;
	}

	public void setPlanPayDate(String planPayDate) {
		this.planPayDate = planPayDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getResidualPrincipal() {
		return residualPrincipal;
	}

	public void setResidualPrincipal(BigDecimal residualPrincipal) {
		this.residualPrincipal = residualPrincipal;
	}
}
