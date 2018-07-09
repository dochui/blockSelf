package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 备付金日总余额
 * @author chengcaiyi
 */
public class BfjSum implements Serializable {

    private static final long serialVersionUID = 1L;
    private String bfjNo;//备付金账号
    private String transactionDate;//交易发生日期日期
    private BigDecimal AmountBalance;//账户金额

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

    public BigDecimal getAmountBalance() {
        return AmountBalance;
    }

    public void setAmountBalance(BigDecimal amountBalance) {
        AmountBalance = amountBalance;
    }


}
