package com.ynet.xwfabric.domain;


import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 日总额核对台账
 * @author chengcaiyi
 *
 */
public class Acct implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String reconciliationDate;//对账日期
	private BigDecimal lastLoanPrincipalBalance;//上日贷款本金余额
	private BigDecimal dayIssuePrincipalTotal;//当日发放本金总额
	private BigDecimal dayRePrincipalTotal;//当日回收本金总额
	private BigDecimal dayLoanPrincipalTotal;//当日贷款本金余额
	private BigDecimal lastComeInterestBalance; //上日应收利息余额
	private BigDecimal dayPlanComeInterest;//当日计提应收利息
	private BigDecimal dayComeInterest;//当日回收利息
	private BigDecimal dayComeInterestBalance;//当日应收利息余额
	private BigDecimal lastComeRenaltyBalance;//上日应收罚息余额
	private BigDecimal dayPlanComeRenalty;//当日计提应收罚息
	private BigDecimal dayComeRenalty;//当日回收罚息
	private BigDecimal dayComeRenaltyBalance;//当日应收罚息余额
	private BigDecimal dayReverseTotal;//当日冲销本金总额
	private BigDecimal dayReverseInterest;//当日冲销利息
	private BigDecimal dayReverseRenalty;//当日冲销罚息
	private BigDecimal lastComePayNormalBalance;//上日应付正常费用余额
	private BigDecimal dayComePayNormalCost;//当日应付正常费用
	private BigDecimal dayBuckleNormalCost; //当日扣收正常费用
	private BigDecimal dayComePayNormalCostBalance;//当日应付正常费用余额
	private BigDecimal dayReverseNormalCost;//当日冲销正常费用
	private BigDecimal lastComePayArrearageCostBalance;//上日应付延滞费用余额
	private BigDecimal dayComePayArrearageCost;//当日应付延滞费用
	private BigDecimal dayBuckleArrearageCost;//当日扣收延滞费用
	private BigDecimal dayComePayArrearageCostBalance;//当日应付延滞费用余额
	private BigDecimal dayReverseArrearageCost;//当日冲销延滞费用
	
	public String getReconciliationDate() {
		return reconciliationDate;
	}
	public void setReconciliationDate(String reconciliationDate) {
		this.reconciliationDate = reconciliationDate;
	}
	public BigDecimal getLastLoanPrincipalBalance() {
		return lastLoanPrincipalBalance;
	}
	public void setLastLoanPrincipalBalance(BigDecimal lastLoanPrincipalBalance) {
		this.lastLoanPrincipalBalance = lastLoanPrincipalBalance;
	}
	public BigDecimal getDayIssuePrincipalTotal() {
		return dayIssuePrincipalTotal;
	}
	public void setDayIssuePrincipalTotal(BigDecimal dayIssuePrincipalTotal) {
		this.dayIssuePrincipalTotal = dayIssuePrincipalTotal;
	}
	public BigDecimal getDayRePrincipalTotal() {
		return dayRePrincipalTotal;
	}
	public void setDayRePrincipalTotal(BigDecimal dayRePrincipalTotal) {
		this.dayRePrincipalTotal = dayRePrincipalTotal;
	}
	public BigDecimal getDayLoanPrincipalTotal() {
		return dayLoanPrincipalTotal;
	}
	public void setDayLoanPrincipalTotal(BigDecimal dayLoanPrincipalTotal) {
		this.dayLoanPrincipalTotal = dayLoanPrincipalTotal;
	}
	public BigDecimal getLastComeInterestBalance() {
		return lastComeInterestBalance;
	}
	public void setLastComeInterestBalance(BigDecimal lastComeInterestBalance) {
		this.lastComeInterestBalance = lastComeInterestBalance;
	}
	public BigDecimal getDayPlanComeInterest() {
		return dayPlanComeInterest;
	}
	public void setDayPlanComeInterest(BigDecimal dayPlanComeInterest) {
		this.dayPlanComeInterest = dayPlanComeInterest;
	}
	public BigDecimal getDayComeInterest() {
		return dayComeInterest;
	}
	public void setDayComeInterest(BigDecimal dayComeInterest) {
		this.dayComeInterest = dayComeInterest;
	}
	public BigDecimal getDayComeInterestBalance() {
		return dayComeInterestBalance;
	}
	public void setDayComeInterestBalance(BigDecimal dayComeInterestBalance) {
		this.dayComeInterestBalance = dayComeInterestBalance;
	}
	public BigDecimal getLastComeRenaltyBalance() {
		return lastComeRenaltyBalance;
	}
	public void setLastComeRenaltyBalance(BigDecimal lastComeRenaltyBalance) {
		this.lastComeRenaltyBalance = lastComeRenaltyBalance;
	}
	public BigDecimal getDayPlanComeRenalty() {
		return dayPlanComeRenalty;
	}
	public void setDayPlanComeRenalty(BigDecimal dayPlanComeRenalty) {
		this.dayPlanComeRenalty = dayPlanComeRenalty;
	}
	public BigDecimal getDayComeRenalty() {
		return dayComeRenalty;
	}
	public void setDayComeRenalty(BigDecimal dayComeRenalty) {
		this.dayComeRenalty = dayComeRenalty;
	}
	public BigDecimal getDayComeRenaltyBalance() {
		return dayComeRenaltyBalance;
	}
	public void setDayComeRenaltyBalance(BigDecimal dayComeRenaltyBalance) {
		this.dayComeRenaltyBalance = dayComeRenaltyBalance;
	}

	public BigDecimal getDayReverseTotal() {
		return dayReverseTotal;
	}

	public void setDayReverseTotal(BigDecimal dayReverseTotal) {
		this.dayReverseTotal = dayReverseTotal;
	}

	public BigDecimal getDayReverseInterest() {
		return dayReverseInterest;
	}

	public void setDayReverseInterest(BigDecimal dayReverseInterest) {
		this.dayReverseInterest = dayReverseInterest;
	}

	public BigDecimal getDayReverseRenalty() {
		return dayReverseRenalty;
	}

	public void setDayReverseRenalty(BigDecimal dayReverseRenalty) {
		this.dayReverseRenalty = dayReverseRenalty;
	}

	public BigDecimal getLastComePayNormalBalance() {
		return lastComePayNormalBalance;
	}

	public void setLastComePayNormalBalance(BigDecimal lastComePayNormalBalance) {
		this.lastComePayNormalBalance = lastComePayNormalBalance;
	}

	public BigDecimal getDayComePayNormalCost() {
		return dayComePayNormalCost;
	}

	public void setDayComePayNormalCost(BigDecimal dayComePayNormalCost) {
		this.dayComePayNormalCost = dayComePayNormalCost;
	}

	public BigDecimal getDayBuckleNormalCost() {
		return dayBuckleNormalCost;
	}

	public void setDayBuckleNormalCost(BigDecimal dayBuckleNormalCost) {
		this.dayBuckleNormalCost = dayBuckleNormalCost;
	}

	public BigDecimal getDayComePayNormalCostBalance() {
		return dayComePayNormalCostBalance;
	}

	public void setDayComePayNormalCostBalance(BigDecimal dayComePayNormalCostBalance) {
		this.dayComePayNormalCostBalance = dayComePayNormalCostBalance;
	}

	public BigDecimal getDayReverseNormalCost() {
		return dayReverseNormalCost;
	}

	public void setDayReverseNormalCost(BigDecimal dayReverseNormalCost) {
		this.dayReverseNormalCost = dayReverseNormalCost;
	}

	public BigDecimal getLastComePayArrearageCostBalance() {
		return lastComePayArrearageCostBalance;
	}

	public void setLastComePayArrearageCostBalance(BigDecimal lastComePayArrearageCostBalance) {
		this.lastComePayArrearageCostBalance = lastComePayArrearageCostBalance;
	}

	public BigDecimal getDayComePayArrearageCost() {
		return dayComePayArrearageCost;
	}

	public void setDayComePayArrearageCost(BigDecimal dayComePayArrearageCost) {
		this.dayComePayArrearageCost = dayComePayArrearageCost;
	}

	public BigDecimal getDayBuckleArrearageCost() {
		return dayBuckleArrearageCost;
	}

	public void setDayBuckleArrearageCost(BigDecimal dayBuckleArrearageCost) {
		this.dayBuckleArrearageCost = dayBuckleArrearageCost;
	}

	public BigDecimal getDayComePayArrearageCostBalance() {
		return dayComePayArrearageCostBalance;
	}

	public void setDayComePayArrearageCostBalance(BigDecimal dayComePayArrearageCostBalance) {
		this.dayComePayArrearageCostBalance = dayComePayArrearageCostBalance;
	}

	public BigDecimal getDayReverseArrearageCost() {
		return dayReverseArrearageCost;
	}

	public void setDayReverseArrearageCost(BigDecimal dayReverseArrearageCost) {
		this.dayReverseArrearageCost = dayReverseArrearageCost;
	}
}
