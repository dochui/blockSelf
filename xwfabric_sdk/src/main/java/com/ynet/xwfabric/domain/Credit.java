package com.ynet.xwfabric.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户授信信息
 *
 * @author chengcaiyi
 */
public class Credit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerNo; //客户号
    private String creditNum; //额度编号
    private String creditType;//额度类型
    private BigDecimal effectiveCredit;//当前生效额度
    private BigDecimal usedCredit;//已使用额度
    private BigDecimal noUsedAmount;//未对消授权金额
    private BigDecimal payNoComingAmount;//实时还款未入账金额
    private String expirationDate;//失效日期
    private String creditStartDate;//当前额度生效日期
    private String contractNo;//合同号

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(String creditNum) {
        this.creditNum = creditNum;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public BigDecimal getEffectiveCredit() {
        return effectiveCredit;
    }

    public void setEffectiveCredit(BigDecimal effectiveCredit) {
        this.effectiveCredit = effectiveCredit;
    }

    public BigDecimal getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(BigDecimal usedCredit) {
        this.usedCredit = usedCredit;
    }

    public BigDecimal getNoUsedAmount() {
        return noUsedAmount;
    }

    public void setNoUsedAmount(BigDecimal noUsedAmount) {
        this.noUsedAmount = noUsedAmount;
    }

    public BigDecimal getPayNoComingAmount() {
        return payNoComingAmount;
    }

    public void setPayNoComingAmount(BigDecimal payNoComingAmount) {
        this.payNoComingAmount = payNoComingAmount;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCreditStartDate() {
        return creditStartDate;
    }

    public void setCreditStartDate(String creditStartDate) {
        this.creditStartDate = creditStartDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }


}
