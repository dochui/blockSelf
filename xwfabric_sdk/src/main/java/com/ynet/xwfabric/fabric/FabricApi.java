package com.ynet.xwfabric.fabric;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ynet.xwfabric.client.ClientCons;
import com.ynet.xwfabric.client.ClientsUtils;
import com.ynet.xwfabric.client.FabricClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * SDK相关的业务接口实现
 *
 * @author chengcaiyi
 * @date 2018年3月11日 上午11:37:52
 */
public class FabricApi {

    private static Logger logger = LoggerFactory.getLogger(FabricApi.class);


    /**
     * 写数据到区块链
     *
     * @param mspid         成员服务提供者ID
     * @param chainCodeName 链码名称
     * @param version       版本号
     * @param jsonData      需要写入的数据
     * @return 写入结果
     */
    public static ResponseResult writeDataToBlockChain(int count,String mspid, String chainCodeName, String version, String jsonData) {
        
        try {
//			logger.info("参数：{org: "+org+", mspid: "+mspid+", chainCodeName: "+chainCodeName+", version: "+version+"}");
            ClientCons clts = ClientsUtils.getClients(mspid, chainCodeName, version);

            FabricClient client = clts.getClient();
            client.callChaincodeWithMutilEndorse("insert", jsonData);

            logger.info("数据写入区块链成功");
            return ResponseResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("区块链网络出现异常");
            if (count > 4) {
                return ResponseResult.build("500", "写入失败，区块链网络出现异常");
            }
            logger.info("尝试第" + ++count + "次写入");
            return writeDataToBlockChain(count,mspid, chainCodeName, version, jsonData);
        } finally {
            ClientsUtils.destroy();
        }
    }

    /**
     * 写数据到区块链2(专门为需要传入指定机构ID的业务所服务)
     *
     * @param mspid         成员服务提供者ID
     * @param chainCodeName 链码名称
     * @param version       版本号
     * @param jsonData      需要写入的数据
     * @return 写入结果
     */
    public static ResponseResult writeDataToBlockChain2(int count,String mspid, String chainCodeName, String version, String orgNumber, String jsonData) {
        try {
//			logger.info("参数：{org: "+org+", mspid: "+mspid+", chainCodeName: "+chainCodeName+", version: "+version+"}");
            ClientCons clts = ClientsUtils.getClients(mspid, chainCodeName, version);

            FabricClient client = clts.getClient();
            client.callChaincodeWithMutilEndorse("insert", orgNumber, jsonData);

            logger.info("数据写入区块链成功");
            return ResponseResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("区块链网络出现异常");
            if (count > 4) {
                return ResponseResult.build("500", "写入失败，区块链网络出现异常");
            }
            logger.info("尝试第" + ++count + "次写入");
           return writeDataToBlockChain2(count,mspid, chainCodeName, version, orgNumber, jsonData);
        } finally {
            ClientsUtils.destroy();
        }
    }

    /**
     * 从区块链中查询数据
     *
     * @param mspid         成员服务提供者ID
     * @param chainCodeName 链码名称
     * @param version       版本
     * @param queryParam    查询条件参数
     * @return 查询结果
     */
    public static ResponseResult queryDataFromBlockChain(int count,String mspid, String chainCodeName, String version, Map<String, String> queryParam) {
        
        try {
            logger.info("查询条件信息：" + JSON.toJSONString(queryParam));
            ClientCons clts = ClientsUtils.getClients(mspid, chainCodeName, version);

            FabricClient client = clts.getClient();

            String result = null;

            switch (queryParam.get("dataType")) {
                case "assert"://资产
                    result = client.callChaincode(System.out::println, true, "query", "8",
                            queryParam.get("loanNumber") == null ? "" : queryParam.get("loanNumber"), 
                            queryParam.get("date") == null ? "" : queryParam.get("date"));
                    break;
                case "assertDetail"://资产明细
                    logger.info(queryParam.toString());
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("queryType"),
                            queryParam.get("loanNumber") == null ? "" : queryParam.get("loanNumber"),
                            queryParam.get("date") == null ? "" : queryParam.get("date"));
                    break;
                case "credit"://授信
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("customerNo") == null ? "" : queryParam.get("customerNo"),
                            queryParam.get("creditType") == null ? "" : queryParam.get("creditType"));
                    break;
                case "acct"://日总额核对台帐
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("orgNumber") == null ? "" : queryParam.get("orgNumber"),
                            queryParam.get("reconciliationDate") == null ? "" : queryParam.get("reconciliationDate"));
                    break;
                case "bfjflow"://备付金流水
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("bfjNo") == null ? "" : queryParam.get("bfjNo"),
                            queryParam.get("transactionDate") == null ? "" : queryParam.get("transactionDate"),
                            queryParam.get("loanNumber") == null ? "" : queryParam.get("loanNumber"));
                    break;
                case "fee"://扣费明细
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("transactionSerialNo") == null ? "" : queryParam.get("transactionSerialNo"),
                            queryParam.get("actualTransactionDate") == null ? "" : queryParam.get("actualTransactionDate"));
                    break;
                case "revTrans"://冲正交易
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("loanNumber") == null ? "" : queryParam.get("loanNumber"),
                            queryParam.get("rushReason") == null ? "" : queryParam.get("rushReason"),
                            queryParam.get("rushDate") == null ? "" : queryParam.get("rushDate"));
                    break;
                case "bfjsum"://备付金总余额
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("bfjNo") == null ? "" : queryParam.get("bfjNo"),
                            queryParam.get("transactionDate") == null ? "" : queryParam.get("transactionDate"));
                    break;
                case "acctflow":// 会计分录
                    result = client.callChaincode(System.out::println, true, "query",
                            queryParam.get("loanNumber") == null ? "" : queryParam.get("loanNumber"),
                            queryParam.get("date") == null ? "" : queryParam.get("date"),
                            queryParam.get("uuid") == null ? "" : queryParam.get("uuid"));
                    break;
                default:
                    logger.error("传入的数据类型错误");
                    break;
            }

            // TransactionInfo transactionInfo = belinkClient.getChannel().queryTransactionByID(param.get("transactionId"));
            logger.info("查询结果：" + result);
            return ResponseResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("区块链网络出现异常");
            if (count > 4) {
                return ResponseResult.build("500", "查数据失败，区块链网络出现异常");
            }
            logger.info("尝试第" + ++count + "次查数据");
            return queryDataFromBlockChain(count,mspid, chainCodeName, version, queryParam);
        } finally {
            ClientsUtils.destroy();
        }

    }

}
