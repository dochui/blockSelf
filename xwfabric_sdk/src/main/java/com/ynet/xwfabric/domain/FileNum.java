package com.ynet.xwfabric.domain;

import java.io.Serializable;
/**
 * 当日各文件数据总数量
 * @author chengcaiyi
 *
 */
public class FileNum implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String date;//日期
	private Integer customerInfoTotal;//客户信息总数
	private Integer customerCreditTotal;//客户授信信息总数
	private Integer issueLoanTotal; //发放贷款总数
	private Integer payPlanTotal;//还款计划总数
	private Integer comeLoanTotal;//回收贷款总数
	private Integer specialBusinessTotal;//特殊交易总数
	private Integer acctFlowTotal;//会计分录日报总数
	private Integer bfjFlowTotal;//备付金流水总数
	private Integer bfjSumTotal;//备付金总额
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getCustomerInfoTotal() {
		return customerInfoTotal;
	}
	public void setCustomerInfoTotal(Integer customerInfoTotal) {
		this.customerInfoTotal = customerInfoTotal;
	}
	public Integer getCustomerCreditTotal() {
		return customerCreditTotal;
	}
	public void setCustomerCreditTotal(Integer customerCreditTotal) {
		this.customerCreditTotal = customerCreditTotal;
	}
	public Integer getIssueLoanTotal() {
		return issueLoanTotal;
	}
	public void setIssueLoanTotal(Integer issueLoanTotal) {
		this.issueLoanTotal = issueLoanTotal;
	}
	public Integer getPayPlanTotal() {
		return payPlanTotal;
	}
	public void setPayPlanTotal(Integer payPlanTotal) {
		this.payPlanTotal = payPlanTotal;
	}
	public Integer getComeLoanTotal() {
		return comeLoanTotal;
	}
	public void setComeLoanTotal(Integer comeLoanTotal) {
		this.comeLoanTotal = comeLoanTotal;
	}
	public Integer getSpecialBusinessTotal() {
		return specialBusinessTotal;
	}
	public void setSpecialBusinessTotal(Integer specialBusinessTotal) {
		this.specialBusinessTotal = specialBusinessTotal;
	}
	public Integer getAcctFlowTotal() {
		return acctFlowTotal;
	}
	public void setAcctFlowTotal(Integer acctFlowTotal) {
		this.acctFlowTotal = acctFlowTotal;
	}
	public Integer getBfjFlowTotal() {
		return bfjFlowTotal;
	}
	public void setBfjFlowTotal(Integer bfjFlowTotal) {
		this.bfjFlowTotal = bfjFlowTotal;
	}
	public Integer getBfjSumTotal() {
		return bfjSumTotal;
	}
	public void setBfjSumTotal(Integer bfjSumTotal) {
		this.bfjSumTotal = bfjSumTotal;
	}
	
	

}
