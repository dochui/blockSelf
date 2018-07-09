package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 客户资料
 * @author chengcaiyi
 *
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	 private  String customerNo; //客户号
	 private  String certificateType; //证件类型
	 private  String certificateNum;//证件号码
	 private  String name; //姓名
	 private  String sex;//性别
	 private  String birthday;//出生年月
	 private  String phone;//手机号码
	 private  String address;//居住地址
	 private  String district;//居住区
	 private  String city;//市
	 private  String province;//省
	 private  String linkMan;//联系人
	 private  String linkManRelation;//联系人关系
	 private  String linkManPhone;//联系人电话
	 private  String companyName;//公司名称
	 private  String companyType;//公司类型
	 private  String companyAddress;//公司地址
	 private  String companyDistrict;//公司地址-区
	 private  String companyCity;//公司地址-市
	 private  String companyProvince;//公司地址 -省
	 private  String addressCode;//邮编
	 private  String homeAddress;//家庭地址
	 private  String communicationCode;//通讯地址邮编
	 private  String companyCode;//公司地址邮编
	 private  String maritaStatus;//婚姻状况
	 private  String education;//学历
	 private  BigDecimal income;//收入
	 private  String mobile;//住宅电话
	 private  String job;//职业
	 private  String jobTitle;//职务
	 private  String jobLeval;//职称
	 private  String updateFlag;//更新标识
	 private  String cardAddress;//身份证地址
	 private  String cardStartDate;//身份证有效期起
	 private  String cardEndDate;//身份证有效期止

	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkManRelation() {
		return linkManRelation;
	}
	public void setLinkManRelation(String linkManRelation) {
		this.linkManRelation = linkManRelation;
	}
	public String getLinkManPhone() {
		return linkManPhone;
	}
	public void setLinkManPhone(String linkManPhone) {
		this.linkManPhone = linkManPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyDistrict() {
		return companyDistrict;
	}
	public void setCompanyDistrict(String companyDistrict) {
		this.companyDistrict = companyDistrict;
	}
	public String getCompanyCity() {
		return companyCity;
	}
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	public String getCompanyProvince() {
		return companyProvince;
	}
	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getCommunicationCode() {
		return communicationCode;
	}
	public void setCommunicationCode(String communicationCode) {
		this.communicationCode = communicationCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getMaritaStatus() {
		return maritaStatus;
	}
	public void setMaritaStatus(String maritaStatus) {
		this.maritaStatus = maritaStatus;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}

	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobLeval() {
		return jobLeval;
	}
	public void setJobLeval(String jobLeval) {
		this.jobLeval = jobLeval;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getCardAddress() {
		return cardAddress;
	}
	public void setCardAddress(String cardAddress) {
		this.cardAddress = cardAddress;
	}
	public String getCardStartDate() {
		return cardStartDate;
	}
	public void setCardStartDate(String cardStartDate) {
		this.cardStartDate = cardStartDate;
	}
	public String getCardEndDate() {
		return cardEndDate;
	}
	public void setCardEndDate(String cardEndDate) {
		this.cardEndDate = cardEndDate;
	}

	 
	
}
