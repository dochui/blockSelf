package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 备付金流水
 * @author chengcaiyi
 */
public class BfjFlow implements Serializable {

    private static final long serialVersionUID = 1L;
    private String bfjNo;//备付金账号
    private String transactionDate;//交易时间
    private String subject;//科目
    private BigDecimal transactionAmount;//交易金额
    private String transactionDesc;//交易描述
    private String loanFlag;//借贷标记
    private String loanNumber;//借款编号
    private String transactionSerialNo;//交易流水号

    public String getBfjNo() {
        return bfjNo;
    }

    public void setBfjNo(String bfjNo) {
        this.bfjNo = bfjNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }

    public String getLoanFlag() {
        return loanFlag;
    }

    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getTransactionSerialNo() {
        return transactionSerialNo;
    }

    public void setTransactionSerialNo(String transactionSerialNo) {
        this.transactionSerialNo = transactionSerialNo;
    }


}
