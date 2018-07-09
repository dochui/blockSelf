package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 冲正类交易
 * @author chengcaiyi
 *
 */
public class RevTrans implements Serializable{

	private static final long serialVersionUID = 1L;

	private String loanNumber; // 借款编号-业务主键，新网唯一标识一笔业务。
	private String rushReason;//冲正原因 1-放款冲销 2-还款冲销
	private String originalTransactionDate;//原交易日期
	private String rushDate;//冲正日期
	private BigDecimal rushAmount;//冲正金额
	private BigDecimal rushPrincipal; //冲正本金
	private BigDecimal rusInterest;//冲正利息
	private BigDecimal rushPenaltyInterest;//冲正罚息
	private BigDecimal rushCost;//冲销费用

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getRushReason() {
		return rushReason;
	}

	public void setRushReason(String rushReason) {
		this.rushReason = rushReason;
	}

	public String getOriginalTransactionDate() {
		return originalTransactionDate;
	}

	public void setOriginalTransactionDate(String originalTransactionDate) {
		this.originalTransactionDate = originalTransactionDate;
	}

	public String getRushDate() {
		return rushDate;
	}

	public void setRushDate(String rushDate) {
		this.rushDate = rushDate;
	}

	public BigDecimal getRushAmount() {
		return rushAmount;
	}

	public void setRushAmount(BigDecimal rushAmount) {
		this.rushAmount = rushAmount;
	}

	public BigDecimal getRushPrincipal() {
		return rushPrincipal;
	}

	public void setRushPrincipal(BigDecimal rushPrincipal) {
		this.rushPrincipal = rushPrincipal;
	}

	public BigDecimal getRusInterest() {
		return rusInterest;
	}

	public void setRusInterest(BigDecimal rusInterest) {
		this.rusInterest = rusInterest;
	}

	public BigDecimal getRushPenaltyInterest() {
		return rushPenaltyInterest;
	}

	public void setRushPenaltyInterest(BigDecimal rushPenaltyInterest) {
		this.rushPenaltyInterest = rushPenaltyInterest;
	}

	public BigDecimal getRushCost() {
		return rushCost;
	}

	public void setRushCost(BigDecimal rushCost) {
		this.rushCost = rushCost;
	}
}
