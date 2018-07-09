package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款明细
 * @author chengcaiyi
 * @date 2018年2月22日 下午2:59:32
 */
public class LoanDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7061986548906751972L;
	
	private String loanNumber; // 借款编号-业务主键，新网唯一标识一笔业务。
	private Integer periods;// 期数
	private String loanDate; // 借款日期
	private String loanEndDate;// 借款结束日期
	private String firstPayDate; // 首次还款日
	private BigDecimal loanAmount;// 借款金额
	private String loanPurpose;// 借款用途 8 个人消费 9 购车
	private String payMethod;// 还款方式 01-随借随还 02-等额本息 03-等额本金 04-利随本清
								// 06-按月付息，到期还本付息 08-按月付息，按年还本
	private BigDecimal rate;// 利率 8 日利率
	private String customerName; // 客户姓名
	private String transactionDate;// 交易起息日
	private String stopInterestFlag; // 停息标志 1 停息 0 未停息
	private Integer repaidPeriod;// 已还期数
	private String settleOnDate; // 结清日
	private String rateType;// 利率类型 (01:固定,02:浮动,03:阶段)
	private String repaymentPeriod;// 还款周期 0-月 6-年 7-一次性 9-其他
	private String loanType; // 贷款类型 03-消费贷（联合贷默认值）
	private String borrowerType;// 借款人类型 07-自然人；（联合贷默认值）
	private String productNo; // 产品编号-联合贷产品编号
	private String productName;// 产品名称-联合贷产品名称
	private String accountState;// 账户状态 代码表：1-提前结清；2-正常结清；3-逾期；4-处理中；7-已撤销；10-回购
	private String guaranteeMethod;// 担保方式 4-信用/免担保；
	private BigDecimal normalBalance;// 正常余额
	private BigDecimal delayBalance;// 逾期余额
	private BigDecimal penaltyAmount;// 罚息金额
	private BigDecimal debitInterestAmount;// 欠息金额
	private BigDecimal penaltyRate;// 罚息利率
	private String customerNo;// 客户号-新网内部客户号
	private String contractNo;// 合同号-与额度表中当前额度节点对应的合同号对应
	private Integer maxDelayDays;// 最大延滞天数 截止目前为止，该笔借据逾期且未结清最小一期的延滞天数（订单最大逾期天数）
	private String receiverBankNo;// 收款人银行账号
	private String receiverBankType;// 收款人账户银行
	private String receiverName;// 收款人名称
	private String updateflag;// 收款人名称
	private String transactionFlowNo;// 交易流水号

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public String getFirstPayDate() {
		return firstPayDate;
	}

	public void setFirstPayDate(String firstPayDate) {
		this.firstPayDate = firstPayDate;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStopInterestFlag() {
		return stopInterestFlag;
	}

	public void setStopInterestFlag(String stopInterestFlag) {
		this.stopInterestFlag = stopInterestFlag;
	}

	public Integer getRepaidPeriod() {
		return repaidPeriod;
	}

	public void setRepaidPeriod(Integer repaidPeriod) {
		this.repaidPeriod = repaidPeriod;
	}

	public String getSettleOnDate() {
		return settleOnDate;
	}

	public void setSettleOnDate(String settleOnDate) {
		this.settleOnDate = settleOnDate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRepaymentPeriod() {
		return repaymentPeriod;
	}

	public void setRepaymentPeriod(String repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getGuaranteeMethod() {
		return guaranteeMethod;
	}

	public void setGuaranteeMethod(String guaranteeMethod) {
		this.guaranteeMethod = guaranteeMethod;
	}

	public BigDecimal getNormalBalance() {
		return normalBalance;
	}

	public void setNormalBalance(BigDecimal normalBalance) {
		this.normalBalance = normalBalance;
	}

	public BigDecimal getDelayBalance() {
		return delayBalance;
	}

	public void setDelayBalance(BigDecimal delayBalance) {
		this.delayBalance = delayBalance;
	}

	public BigDecimal getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(BigDecimal penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public BigDecimal getDebitInterestAmount() {
		return debitInterestAmount;
	}

	public void setDebitInterestAmount(BigDecimal debitInterestAmount) {
		this.debitInterestAmount = debitInterestAmount;
	}

	public BigDecimal getPenaltyRate() {
		return penaltyRate;
	}

	public void setPenaltyRate(BigDecimal penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getMaxDelayDays() {
		return maxDelayDays;
	}

	public void setMaxDelayDays(Integer maxDelayDays) {
		this.maxDelayDays = maxDelayDays;
	}

	public String getReceiverBankNo() {
		return receiverBankNo;
	}

	public void setReceiverBankNo(String receiverBankNo) {
		this.receiverBankNo = receiverBankNo;
	}

	public String getReceiverBankType() {
		return receiverBankType;
	}

	public void setReceiverBankType(String receiverBankType) {
		this.receiverBankType = receiverBankType;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getTransactionFlowNo() {
		return transactionFlowNo;
	}

	public void setTransactionFlowNo(String transactionFlowNo) {
		this.transactionFlowNo = transactionFlowNo;
	}

	public String getUpdateflag() {
		return updateflag;
	}

	public void setUpdateflag(String updateflag) {
		this.updateflag = updateflag;
	}
}
