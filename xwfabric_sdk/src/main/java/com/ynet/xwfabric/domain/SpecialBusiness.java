package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 特殊交易
 * @author chengcaiyi
 */
public class SpecialBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loanNumber;// 贷款编号

	private String happenDate;// 发生日期

	private String businessType;// 特殊交易类型
								// 3-提前还款（摊薄：期限不变，期供变少）；5-提前还款（结清，不会新增还款计划）

	private BigDecimal businessAmount;// 特殊交易金额

	private Integer changePeriod;// 变更期次

	private String detailInfo;// 明细信息（此次特殊交易的说明）

	private String payPlanChangeFlag;// 还款计划变更标识（此次 特殊交易是否引起还款计划变更。1-是，2-否）

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(String happenDate) {
		this.happenDate = happenDate;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public BigDecimal getBusinessAmount() {
		return businessAmount;
	}

	public void setBusinessAmount(BigDecimal businessAmount) {
		this.businessAmount = businessAmount;
	}

	public Integer getChangePeriod() {
		return changePeriod;
	}

	public void setChangePeriod(Integer changePeriod) {
		this.changePeriod = changePeriod;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getPayPlanChangeFlag() {
		return payPlanChangeFlag;
	}

	public void setPayPlanChangeFlag(String payPlanChangeFlag) {
		this.payPlanChangeFlag = payPlanChangeFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
