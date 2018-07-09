package com.ynet.fabric.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ynet.fabric.service.AcctFlowService;
import com.ynet.fabric.service.AcctService;
import com.ynet.fabric.service.BfjFlowService;
import com.ynet.fabric.service.BfjSumService;
import com.ynet.fabric.service.CreditService;
import com.ynet.fabric.service.CustomerService;
import com.ynet.fabric.service.FeeService;
import com.ynet.fabric.service.LoanDetailService;
import com.ynet.fabric.service.PayLogService;
import com.ynet.fabric.service.PayPlanService;
import com.ynet.fabric.service.RevTransService;
import com.ynet.fabric.service.SpecialBusinessService;
import com.ynet.xwfabric.domain.Acct;
import com.ynet.xwfabric.domain.AcctFlow;
import com.ynet.xwfabric.domain.Assert;
import com.ynet.xwfabric.domain.BfjFlow;
import com.ynet.xwfabric.domain.BfjSum;
import com.ynet.xwfabric.domain.Credit;
import com.ynet.xwfabric.domain.Customer;
import com.ynet.xwfabric.domain.Fee;
import com.ynet.xwfabric.domain.LoanDetail;
import com.ynet.xwfabric.domain.PayLog;
import com.ynet.xwfabric.domain.PayPlan;
import com.ynet.xwfabric.domain.RevTrans;
import com.ynet.xwfabric.domain.SpecialBusiness;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.FileUtil;
import com.ynet.xwfabric.util.ResponseResult;


@Component
public class FabricWriteHandler {

	private static Logger logger = LoggerFactory.getLogger(FabricWriteHandler.class);

    @Autowired
    private LoanDetailService loanDetailService;

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private AcctFlowService acctFlowService;

    @Autowired
    private PayPlanService payPlanService;

    @Autowired
    private SpecialBusinessService specialBusinessService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CreditService creditService;

    @Autowired
    private AcctService acctService;

    @Autowired
    private BfjFlowService bfjFlowService;

    @Autowired
    private BfjSumService bfjSumService;

    @Autowired
    private FeeService feeService;

    @Autowired
    private RevTransService revTransService;

    JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
	
	public ResponseResult writeData(String filePath, String dataday) {
        Map map = paseFile(filePath+"\\"+dataday);
        List<Assert> assertList = map.get("assertList") != null ? (List<Assert>) map.get("assertList") : null;
        List<Credit> creditList = map.get("creditList") != null ? (List<Credit>) map.get("creditList") : null;
        List<Acct> acctList = map.get("acctList") != null ? (List<Acct>) map.get("acctList") : null;
        List<BfjFlow> bfjFlowList = map.get("bfjFlowList") != null ? (List<BfjFlow>) map.get("bfjFlowList") : null;
        List<BfjSum> bfjSumList = map.get("bfjSumList") != null ? (List<BfjSum>) map.get("bfjSumList") : null;
        List<AcctFlow> acctFlowList = map.get("acctFlowList") != null ? (List<AcctFlow>) map.get("acctFlowList") : null;
        List<Fee> feeList = map.get("feeList") != null ? (List<Fee>) map.get("feeList") : null;
        List<RevTrans> revTransList = map.get("revTransList") != null ? (List<RevTrans>) map.get("revTransList") : null;
        if (assertList != null) {
            try {
                //写入资产
                ResponseResult assertRes = client.writeAssertList(assertList);
                if ("000000".equals(assertRes.getReturnCode())) {
                    //写入授信信息
                    if (creditList != null) {
                        client.writeCreditList(creditList);
                    }
                    //写入日总额台账
                    if (acctList != null) {
                        client.writeAcctList(acctList);
                    }
                    //写入备付金流水
                    if (bfjFlowList != null) {
                        client.writeBfjFlowList(bfjFlowList);
                    }
                    //写入备付金总额
                    if (bfjSumList != null) {
                        client.writeBfjSumList(bfjSumList);
                    }
                    //写入会计分录
                    if (acctFlowList != null) {
                        client.writeAcctFlowList(acctFlowList);
                    }
                    //写入扣费明细
                    if (feeList != null) {
                        client.writeFeeList(feeList);
                    }
                    //写入冲正交易
                    if (revTransList != null) {
                        client.writeRevTransList(revTransList);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("资产写入失败");
            }

        }
        return null;
    }
	public Map paseFile(String filePath) {
        Map<String, Object> map = new HashMap<String, Object>();
        File file = new File(filePath);
        List<LoanDetail> loanDetailList = null;
        List<PayPlan> payPlanList = null;
        List<PayLog> payLogList = null;
        List<SpecialBusiness> specialBusinessList = null;
        List<Customer> customerList = null;
        List<Credit> creditList = null;
        List<Acct> acctList = null;
        List<BfjFlow> bfjFlowList = null;
        List<BfjSum> bfjSumList = null;
        List<AcctFlow> acctFlowList = null;
        List<Fee> feeList = null;
        List<RevTrans> revTransList = null;
        if (!file.isDirectory()) {
            throw new RuntimeException("传入的必须是文件夹");
        } else {
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                File readFile = new File(filePath + "\\" + fileList[i]);
                List<String> content = null;
                try {
                    content = FileUtil.readFile(readFile.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!readFile.isDirectory()) {
                    String fileName = readFile.getName();
                    String[] arrStr = null;
                    if (fileName != null) {
                        arrStr = fileName.split("_");
                        if (arrStr != null && arrStr.length > 1) {
                            switch (arrStr[1]) {
                                case "loandetail":// 贷款明细
                                    loanDetailList = loanDetailService.handleLoanDetailData(content);
                                    map.put("loanDetailList", loanDetailList);
                                    break;
                                case "payplan":// 还款计划
                                    payPlanList = payPlanService.handlePayPlanData(content);
                                    map.put("payPlanList", payPlanList);
                                    map.put("payPlanOldCount",content==null?0:content.size());
                                    break;
                                case "paylog":// 还款明细
                                    payLogList = payLogService.handlePayLogData(content);
                                    map.put("payLogList", payLogList);
                                    map.put("payLogOldCount",content==null?0:content.size());
                                    break;
                                case "specialbusiness":// 特殊交易
                                    specialBusinessList = specialBusinessService.handleSpecialBusinessData(content);
                                    map.put("specialBusinessList", specialBusinessList);
                                    map.put("specialBusinessOldCount",content==null?0:content.size());
                                    break;
                                case "customer"://客户信息
                                    customerList = customerService.handleCustomerData(content);
                                    map.put("customerList", customerList);
                                    map.put("customerOldCount",content==null?0:content.size());
                                    break;
                                case "credit": //授信信息
                                    creditList = creditService.handleCreditData(content);
                                    map.put("creditList", creditList);
                                    break;
                                case "acct": //日总台账
                                    acctList = acctService.handleAcctData(content);
                                    map.put("acctList", acctList);
                                    break;
                                case "bfjflow": //备付金流水
                                    bfjFlowList = bfjFlowService.handleBfjFlowData(content);
                                    map.put("bfjFlowList", bfjFlowList);
                                    break;
                                case "bfjsum": //备付金总额
                                    bfjSumList = bfjSumService.handleBfjSumData(content);
                                    map.put("bfjSumList", bfjSumList);
                                    break;
                                case "acctflow": //会计分录
                                    acctFlowList = acctFlowService.handleAcctFlowData(content);
                                    map.put("acctFlowList", acctFlowList);
                                    break;
                                case "revTrans": //冲正类交易
                                    revTransList = revTransService.handleRevTransData(content);
                                    map.put("revTransList", revTransList);
                                    break;
                                case "fee": //扣费明细
                                    feeList = feeService.handleFeeData(content);
                                    map.put("feeList", feeList);
                                    break;
                                default:
                                    break;
                            }
                        }

                    }
                }
            }
            return parseList(map);
        }
    }

    /**
     * 将文件的信息打包成 资产、授信、会计分录、备付金等
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> parseList(Map map) {
        Map resMap = new HashMap<String, Object>();
        List<LoanDetail> loanDetailList = (List<LoanDetail>) map.get("loanDetailList");
        List<PayPlan> payPlanList = (List<PayPlan>) map.get("payPlanList");
        List<PayLog> payLogList = (List<PayLog>) map.get("payLogList");
        List<SpecialBusiness> specialBusinessList = (List<SpecialBusiness>) map.get("specialBusinessList");
        List<Customer> customerList = (List<Customer>) map.get("customerList");
        List<Credit> creditList = (List<Credit>) map.get("creditList");
        List<Acct> acctList = (List<Acct>) map.get("acctList");
        List<BfjFlow> bfjFlowList = (List<BfjFlow>) map.get("bfjFlowList");
        List<BfjSum> bfjSumList = (List<BfjSum>) map.get("bfjSumList");
        List<AcctFlow> acctFlowList = (List<AcctFlow>) map.get("acctFlowList");
        List<Fee> feeList = (List<Fee>) map.get("feeList");
        List<RevTrans> revTransList = (List<RevTrans>) map.get("revTransList");

        List<Assert> assertList = new ArrayList<Assert>();
        //设置总的资产
        if (loanDetailList != null && loanDetailList.size() > 0) {
            int payPlanCount = 0;
            int payLogCount = 0;
            int specialBusinessCount = 0;
            int customerCount = 0;
            for (int i = 0; i < loanDetailList.size(); i++) {
                //设置贷款明细
                Assert ass = new Assert();
                String loanNumber = loanDetailList.get(i).getLoanNumber();
                String customerNo = loanDetailList.get(i).getCustomerNo();
                ass.setLoanDetailInfo(loanDetailList.get(i));
                //设置还款计划
                if (payPlanList != null && payPlanList.size() > 0) {
                    for (int j = 0; j < payPlanList.size(); j++) {
                        if (loanNumber.equals(payPlanList.get(j).getLoanNumber())) {
                            ass.setPayPlanInfo(payPlanList.get(j));
                            payPlanCount+=payPlanList.get(j).getPayPlanItemList().size();
                        }
                    }

                }
                //设置还款明细
                List<PayLog> list = new ArrayList<PayLog>();
                if (payLogList != null && payLogList.size() > 0) {
                    for (int z = 0; z < payLogList.size(); z++) {
                        if (loanNumber.equals(payLogList.get(z).getLoanNumber())) {
                            list.add(payLogList.get(z));
                            payLogCount++;
                        }
                    }
                }
                ass.setPayLogListInfo(list);
                //设置特殊交易
                List<SpecialBusiness> lists = new ArrayList<SpecialBusiness>();
                if (specialBusinessList != null && specialBusinessList.size() > 0) {
                    for (int k = 0; k < specialBusinessList.size(); k++) {
                        if (loanNumber.equals(specialBusinessList.get(k).getLoanNumber())) {
                            lists.add(specialBusinessList.get(k));
                            specialBusinessCount++;
                        }
                    }
                }
                ass.setSpecialBusinessListInfo(lists);

                //设置客户信息
                if (customerList != null && customerList.size() > 0) {
                    for (int m = 0; m < customerList.size(); m++) {
                        if (customerNo.equals(customerList.get(m).getCustomerNo())) {
                            customerCount++;
                            ass.setCustomerInfo(customerList.get(m));
                        }
                    }
                }
                assertList.add(ass);
            }
            if(map.get("payPlanOldCount").equals(payPlanCount+"")){
                throw new RuntimeException("还款计划数据不一致！");
            }
            if(map.get("payLogOldCount").equals(payLogCount+"")){
                throw new RuntimeException("还款明细数据不一致！");
            }
            if(map.get("specialBusinessOldCount").equals(specialBusinessCount+"")){
                throw new RuntimeException("特殊交易数据不一致！");
            }
            if(map.get("customerOldCount").equals(customerCount+"")){
                throw new RuntimeException("用户数据不一致！");
            }
            //返回的资产信息集合
            resMap.put("assertList", assertList);
            //返回授信信息集合
            if (creditList != null && creditList.size() > 0) {
                resMap.put("creditList", creditList);
            }
            //备付金流水集合
            if (bfjFlowList != null && bfjFlowList.size() > 0) {
                resMap.put("bfjFlowList", bfjFlowList);
            }
            //备付金总额
            if (bfjSumList != null && bfjSumList.size() > 0) {
                resMap.put("bfjSumList", bfjSumList);
            }
            //日总台账
            if (acctList != null && acctList.size() > 0) {
                resMap.put("acctList", acctList);
            }
            //会计分录
            if (acctFlowList != null && acctFlowList.size() > 0) {
                resMap.put("acctFlowList", acctFlowList);
            }
            //扣费明细
            if (feeList != null && feeList.size() > 0) {
                resMap.put("feeList", feeList);
            }
            //冲正交易
            if (revTransList != null && revTransList.size() > 0) {
                resMap.put("revTransList", revTransList);
            }
            System.out.println("处理结果" + JSONObject.toJSONString(map));
            resMap.put("loanDetailCount",loanDetailList ==null ? 0:loanDetailList.size());
            resMap.put("payPlanCount",payPlanCount);
            resMap.put("payLogCount",payLogCount);
            resMap.put("specialBusinessCount",specialBusinessCount);
            resMap.put("customerCount",customerCount);
            resMap.put("creditCount",creditList ==null ? 0:creditList.size());
            resMap.put("acctCount",acctList ==null ? 0:acctList.size());
            resMap.put("bfjFlowCount",bfjFlowList ==null ? 0:bfjFlowList.size());
            resMap.put("bfjSumCount",bfjSumList ==null ? 0:bfjSumList.size());
            resMap.put("acctFlowCount",acctFlowList ==null ? 0:acctFlowList.size());
            resMap.put("feeCount",feeList ==null ? 0:feeList.size());
            resMap.put("revTransCount",revTransList ==null ? 0:revTransList.size());
            return resMap;
        } else {
            return null;
        }
    }
}
