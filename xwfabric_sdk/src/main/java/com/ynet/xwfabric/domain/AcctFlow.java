package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会计分录
 *
 * @author chengcaiyi
 */
public class AcctFlow implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String uuid;// UUID（主键）

    private String loanNumber;// 贷款编号

    private String happenDate;// 发生日期

    private String currency;// 币种

    private String businessCode;// 交易码

    private String businessDesc;// 交易描述

    private String loanFlag;// 借贷标记（D-借，C-贷）

    private BigDecimal businessAmount;// 入账金额

    private String branchNo;// 分支行号

    private String productNo;// 产品号

    private String subject;// 科目（1301-个人贷款，1501-应收利息，2111-同业存放，2311-储蓄活期存款，6001-贷款利息收入）

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public String getLoanFlag() {
        return loanFlag;
    }

    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag;
    }

    public BigDecimal getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(BigDecimal businessAmount) {
        this.businessAmount = businessAmount;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
