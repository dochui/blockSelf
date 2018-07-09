package com.ynet.xwfabric.fabric;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ynet.xwfabric.domain.Acct;
import com.ynet.xwfabric.domain.AcctFlow;
import com.ynet.xwfabric.domain.Assert;
import com.ynet.xwfabric.domain.BfjFlow;
import com.ynet.xwfabric.domain.BfjSum;
import com.ynet.xwfabric.domain.Credit;
import com.ynet.xwfabric.domain.Fee;
import com.ynet.xwfabric.domain.PayLog;
import com.ynet.xwfabric.domain.RevTrans;
import com.ynet.xwfabric.domain.SpecialBusiness;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 联合贷相关接口调用的客户端提供给SDK调用方的入口实例类
 *
 * @author qiangjiyi
 * @date 2018年3月11日 上午11:13:33
 */
public class JoinLoanClient {

    private static Logger logger = LoggerFactory.getLogger(JoinLoanClient.class);

    private static Properties props = null;

    private String mspid = null;

    public JoinLoanClient(String mspid) {
        super();
        this.mspid = mspid;
    }

    /**
     * 获得client客户端对象
     *
     * @param mspid 成员服务提供者ID
     * @return
     */
    public static JoinLoanClient getInstance(String mspid) {
        try {
            props = new Properties();
            props.load(JoinLoanClient.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new JoinLoanClient(mspid);
    }

    /**
     * 批量将资产写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeAssertList(List<Assert> assertList) {
        logger.info("*************** 批量写入资产信息开始 ***************");
        int count1 = 0;// 用于记录更新资产的记录数
        int count2 = 0;// 用于记录新增资产的记录数
        for (int i = 0; i<assertList.size();i++) {
            Assert assertInfo = assertList.get(i);
            ResponseResult result = this.writeAssert(assertInfo);
            Map<String, Boolean> map = (Map) result.getData();
            if (map.get("updated")) {
                count1++;
            } else {
                count2++;
            }
        }
        logger.info("*************** 批量写入授信信息成功【共写入" + (count1 + count2) + "条资产】 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将授信信息写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeCreditList(List<Credit> creditList) {
        logger.info("*************** 批量写入授信信息开始 ***************");
        for (int i = 0; i<creditList.size();i++) {
            this.writeCredit(creditList.get(i));
        }
        logger.info("*************** 批量写入授信信息成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将日总额核对台帐写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeAcctList(List<Acct> acctList) {
        logger.info("*************** 批量写入日总额核对台帐开始 ***************");
        for (int i = 0; i<acctList.size();i++) {
            this.writeAcct(acctList.get(i));
        }
        logger.info("*************** 批量写入日总额核对台帐成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将备付金流水写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeBfjFlowList(List<BfjFlow> bfjFlowList) {
        logger.info("*************** 批量写入备付金流水开始 ***************");
        for (int i = 0; i<bfjFlowList.size();i++) {
            this.writeBfjFlow(bfjFlowList.get(i));
        }
        logger.info("*************** 批量写入备付金流水成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将备付金总金额写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeBfjSumList(List<BfjSum> bfjSumList) {
        logger.info("*************** 批量写入备付金总金额开始 ***************");
        for (int i = 0; i<bfjSumList.size();i++) {
            this.writeBfjSum(bfjSumList.get(i));
        }
        logger.info("*************** 批量写入备付金总金额成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将扣费明细写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeFeeList(List<Fee> feeList) {
        logger.info("*************** 批量写入扣费明细开始 ***************");
        for (int i = 0; i<feeList.size();i++) {
            this.writeFee(feeList.get(i));
        }
        logger.info("*************** 批量写入扣费明细成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将冲正交易写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeRevTransList(List<RevTrans> revTransList) {
        logger.info("*************** 批量写入冲正交易开始 ***************");
        for (int i = 0; i<revTransList.size();i++) {
            this.writeRevTrans(revTransList.get(i));
        }
        logger.info("*************** 批量写入冲正交易成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 批量将会计分录写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeAcctFlowList(List<AcctFlow> acctFlowList) {
        logger.info("*************** 批量写入会计分录开始 ***************");
        for (int i = 0; i<acctFlowList.size();i++) {
            this.writeAcctFlow(acctFlowList.get(i));
        }
        logger.info("*************** 批量写入会计分录成功 ***************");
        return ResponseResult.ok();
    }

    /**
     * 将资产写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeAssert(Assert assertInfo) {
        if (assertInfo == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }
        boolean updated = false;
        String loanNumber = assertInfo.getLoanDetailInfo().getLoanNumber();
        Map map = new HashMap<String, String>();
        map.put("loanNumber", loanNumber);
        map.put("type", "8");
        //查询相关贷款编号是否有记录
        ResponseResult res = this.queryData("assert", map);
        if (res.getData() != null && !"".equals(res.getData())) {
            Assert as = (Assert) JSONObject.parseObject(res.getData().toString(), Assert.class);
            if (assertInfo.getLoanDetailInfo() != null) {
                as.setLoanDetailInfo(assertInfo.getLoanDetailInfo());
            }
            if (assertInfo.getPayPlanInfo() != null) {
                as.setPayPlanInfo(assertInfo.getPayPlanInfo());
            }
            if (assertInfo.getPayLogListInfo() != null) {
                List<PayLog> list = assertInfo.getPayLogListInfo();
                List<PayLog> oldList = as.getPayLogListInfo();
                if (oldList != null && oldList.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        oldList.add(list.get(j));
                    }
                    as.setPayLogListInfo(oldList);
                } else {
                    as.setPayLogListInfo(list);
                }
            }
            if (assertInfo.getSpecialBusinessListInfo() != null) {
                List<SpecialBusiness> list = assertInfo.getSpecialBusinessListInfo();
                List<SpecialBusiness> oldList = as.getSpecialBusinessListInfo();
                if (oldList != null && oldList.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        oldList.add(list.get(j));
                    }
                    as.setSpecialBusinessListInfo(oldList);
                } else {
                    as.setSpecialBusinessListInfo(list);
                }
            }

            if (assertInfo.getCustomerInfo() != null) {
                as.setCustomerInfo(assertInfo.getCustomerInfo());
            }
            if (assertInfo.getDocumentInfo() != null) {
                as.setDocumentInfo(assertInfo.getDocumentInfo());
            }
            //插入资产
            try {
                logger.info("更新资产后的资产——————" + JSONObject.toJSONString(as));
                ResponseResult response = writeData("assert", JSONObject.toJSONString(as));
                if ("000000".equals(response.getReturnCode())) {
                    logger.info("更新资产成功");
                    updated = true;
                }
            } catch (Exception e) {
                throw new RuntimeException("区块链写入资产失败，请重新写入");
            }
        } else {
            //插入资产
            try {
                logger.info("新插入的资产——————" + JSONObject.toJSONString(assertInfo));
                ResponseResult response = writeData("assert", JSONObject.toJSONString(assertInfo));
                if ("000000".equals(response.getReturnCode())) {
                    logger.info("插入资产成功");
                }
            } catch (Exception e) {
                throw new RuntimeException("区块链写入资产失败，请重新写入");
            }
        }
        Map<String, Boolean> dataMap = new HashMap<String, Boolean>();
        dataMap.put("updated", updated);
        return ResponseResult.ok(dataMap);
    }

    /**
     * 将授信信息写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeCredit(Credit credit) {
        if (credit == null || credit.getCustomerNo() == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("credit", JSON.toJSONString(credit));
    }

    /**
     * 将日总额核对台帐写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeAcct(Acct acct) {
        if (acct == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("acct", JSON.toJSONString(acct));
    }

    /**
     * 将备付金流水写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeBfjFlow(BfjFlow bfjFlow) {
        if (bfjFlow == null || bfjFlow.getBfjNo() == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("bfjflow", JSON.toJSONString(bfjFlow));
    }

    /**
     * 将备付金总金额写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeBfjSum(BfjSum bfjSum) {
        if (bfjSum == null || bfjSum.getBfjNo() == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("bfjsum", JSON.toJSONString(bfjSum));
    }

    /**
     * 将会计分录写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeAcctFlow(AcctFlow acctFlow) {
        if (acctFlow == null || acctFlow.getLoanNumber() == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("acctflow", JSON.toJSONString(acctFlow));
    }

    /**
     * 将扣费明细写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeFee(Fee fee) {
        if (fee == null || fee.getTransactionSerialNo() == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("fee", JSON.toJSONString(fee));
    }

    /**
     * 将冲正交易写入区块链
     *
     * @param
     * @return 写入结果
     */
    public ResponseResult writeRevTrans(RevTrans revTrans) {
        if (revTrans == null || revTrans.getLoanNumber() == null) {
            return ResponseResult.build("888889", "要写入区块链的数据不能为空");
        }

        return writeData("revTrans", JSON.toJSONString(revTrans));
    }

    /**
     * 统一的写入方法
     *
     * @param dataType 要写入的数据类型
     * @param jsonData 写入的具体数据JSON串
     * @return 写入结果
     */
    public ResponseResult writeData(String dataType, String jsonData) {
    	int count = 1; // 计数器，如果超时出错，最多重试5次
        if ("acct".equalsIgnoreCase(dataType)) {
            String orgNumber = props.getProperty("org_number");
            return FabricApi.writeDataToBlockChain2(count,mspid, dataType.toLowerCase(), props.getProperty(dataType.toLowerCase()), orgNumber, jsonData);
        }
        return FabricApi.writeDataToBlockChain(count,mspid, dataType.toLowerCase(), props.getProperty(dataType.toLowerCase()), jsonData);
    }


    /**
     * 从区块链中查询资产信息（type==8）
     *
     * @param
     * @return 查询结果
     */
    public ResponseResult queryAssert(String loanNumber) {
        if (loanNumber == null || StringUtils.isEmpty(loanNumber)) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("loanNumber", loanNumber);
        return queryData("assert", param);
    }

    /**
     * 从区块链中查询资产明细信息（type!=8）
     *
     * @param loanNumber 贷款编号
     * @param date       日期
     * @return 查询结果
     */
    public ResponseResult queryAssertDetail(String queryType, String loanNumber, String date) {
        if (queryType == null || StringUtils.isEmpty(queryType)) {
            return ResponseResult.build("888887", "查询的资产明细类型不能为空");
        }
        if ((loanNumber == null || StringUtils.isEmpty(loanNumber)) && (date == null || StringUtils.isEmpty(date))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("queryType", queryType);
        param.put("loanNumber", loanNumber);
        param.put("date", date);
        return queryData("assertDetail", param);
    }

    /**
     * 从区块链中查询授信
     *
     * @param customerNo 客户编号
     * @param creditType 授信类型
     * @return 查询结果
     */
    public ResponseResult queryCredit(String customerNo, String creditType) {
        if ((customerNo == null || StringUtils.isEmpty(customerNo)) && (creditType == null || StringUtils.isEmpty(creditType))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("customerNo", customerNo);
        param.put("creditType", creditType);
        return queryData("credit", param);
    }

    /**
     * 从区块链中查询日总额核对台帐
     *
     * @param reconciliationDate 对账日期
     * @return 查询结果
     */
    public ResponseResult queryAcct(String reconciliationDate) {
        if (reconciliationDate == null || StringUtils.isEmpty(reconciliationDate)) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("reconciliationDate", reconciliationDate);
        return queryData("acct", param);
    }

    /**
     * 从区块链中查询备付金流水
     *
     * @param
     * @return 查询结果
     */
    public ResponseResult queryBfjFlow(String bfjNo, String transactionDate, String loanNumber) {
        if ((bfjNo == null || StringUtils.isEmpty(bfjNo)) && (transactionDate == null || StringUtils.isEmpty(transactionDate)) && (loanNumber == null || StringUtils.isEmpty(loanNumber))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("bfjNo", bfjNo);
        param.put("transactionDate", transactionDate);
        param.put("loanNumber", loanNumber);
        return queryData("bfjflow", param);
    }

    /**
     * 从区块链中查询扣费明细
     *
     * @param
     * @return 查询结果
     */
    public ResponseResult queryFee(String transactionSerialNo, String actualTransactionDate) {
        if ((transactionSerialNo == null || StringUtils.isEmpty(transactionSerialNo)) && (actualTransactionDate == null || StringUtils.isEmpty(actualTransactionDate))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("transactionSerialNo", transactionSerialNo);
        param.put("actualTransactionDate", actualTransactionDate);
        return queryData("fee", param);
    }

    /**
     * 从区块链中查询冲正交易
     *
     * @param
     * @return 查询结果
     */
    public ResponseResult queryRevTrans(String loanNumber, String rushReason, String rushDate) {
        if ((loanNumber == null || StringUtils.isEmpty(loanNumber)) && (rushReason == null || StringUtils.isEmpty(rushReason)) && (rushDate == null || StringUtils.isEmpty(rushDate))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("loanNumber", loanNumber);
        param.put("rushReason", rushReason);
        param.put("rushDate", rushDate);
        return queryData("revTrans", param);
    }

    /**
     * 从区块链中查询备付金总余额
     *
     * @param
     * @return 查询结果
     */
    public ResponseResult queryBfjSum(String bfjNo, String transactionDate) {
        if ((bfjNo == null || StringUtils.isEmpty(bfjNo)) && (transactionDate == null || StringUtils.isEmpty(transactionDate))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("bfjNo", bfjNo);
        param.put("transactionDate", transactionDate);
        return queryData("bfjsum", param);
    }

    /**
     * 从区块链中查询会计分录
     *
     * @param
     * @return 查询结果
     */
    public ResponseResult queryAcctFlow(String loanNumber, String date, String uuid) {
        if ((loanNumber == null || StringUtils.isEmpty(loanNumber)) && (date == null || StringUtils.isEmpty(date)) && (uuid == null || StringUtils.isEmpty(uuid))) {
            return ResponseResult.build("888887", "查询条件不能为空");
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("loanNumber", loanNumber);
        param.put("date", date);
        if(StringUtils.isNotEmpty(uuid)) {
            param.put("uuid", uuid);
        }
        return queryData("acctflow", param);
    }

    /**
     * 统一的查询方法
     *
     * @param dataType 要查询的数据类型
     * @param param    查询条件
     * @return 查询结果
     */
    public ResponseResult queryData(String dataType, Map<String, String> param) {
    	int count = 1; // 计数器，如果超时出错，最多重试5次
        param.put("dataType", dataType);
        if ("assertDetail".equalsIgnoreCase(dataType)) {
            return FabricApi.queryDataFromBlockChain(count,mspid, "assert", props.getProperty("assert"), param);
        } else if ("acct".equalsIgnoreCase(dataType)) {
            param.put("orgNumber", props.getProperty("org_number"));
        }

        return FabricApi.queryDataFromBlockChain(count,mspid, dataType.toLowerCase(), props.getProperty(dataType.toLowerCase()), param);
    }

}
