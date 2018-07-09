package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.PayPlanService;
import com.ynet.xwfabric.domain.PayPlan;
import com.ynet.xwfabric.domain.PayPlanItem;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 贷款归还计划（还款计划）相关的业务接口实现
 * @author qiangjiyi
 * @date 2018年2月23日 下午4:12:32
 */
@Service
public class PayPlanServiceImpl implements PayPlanService {

	private static Logger logger = LoggerFactory.getLogger(PayPlanServiceImpl.class);
	
	@Override
	public List<PayPlan> handlePayPlanData(List<String> content) {
		String tempLoanNumber = null;
		List<PayPlan> payPlanList = new ArrayList<PayPlan>();
		
		PayPlan payPlan = null;
		List<PayPlanItem> payPlanItemList = null;
		if(content != null && content.size()>0){
			int a = 0;
		for(String str : content) {
			a++;
			// 将每行的内容进行分割为一个字符串数组
			String[] str_arr = str.split("[|][+][|]");
			
			/**
			 * 如果临时贷款编号为空，则说明为第一条记录，
			 * 1.直接创建一个还款计划对象并设置贷款编号，
			 * 2.为临时贷款编号赋值
			 * 3.创建还款计划项集合
			 */
			if(tempLoanNumber == null) {
				payPlan = new PayPlan();
				payPlan.setLoanNumber(str_arr[0]);
				payPlan.setTransactionDate(str_arr[3]);
				tempLoanNumber = str_arr[0];
				payPlanItemList = new ArrayList<PayPlanItem>();
			}

			
			PayPlanItem payPlanItem = new PayPlanItem();
			payPlanItem.setPeriods(str_arr[1] == "" ? 0 : Integer.parseInt(str_arr[1]));
			payPlanItem.setRepaymentDate(str_arr[2]);
			payPlanItem.setTransactionDate(str_arr[3]);
			payPlanItem.setPayAmount(str_arr[4] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[4]));
			payPlanItem.setPlanPrincipal(str_arr[5] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[5]));
			payPlanItem.setPlanInterest(str_arr[6] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[6]));
			payPlanItem.setState(str_arr[7]);
			payPlanItem.setPeriod(str_arr[8] == "" ? 0 : Integer.parseInt(str_arr[8]));
			payPlanItem.setDelayDays(str_arr[9] == "" ? 0 : Integer.parseInt(str_arr[9]));
			payPlanItem.setTotalAmount(str_arr[10] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[10]));
			payPlanItem.setPrincipal(str_arr[11] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[11]));
			payPlanItem.setInterest(str_arr[12] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[12]));
			payPlanItem.setDelayAmount(str_arr[13] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[13]));
			payPlanItem.setPenalty(str_arr[14] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[14]));
			payPlanItem.setRealPrincipal(str_arr[15] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[15]));
			payPlanItem.setRealInterest(str_arr[16] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[16]));
			payPlanItem.setPayDate(str_arr[17]);
			payPlanItem.setRealPenalty(str_arr[18] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[18]));
			payPlanItem.setFlag(str_arr[19]);
			payPlanItem.setPlanPayDate(str_arr[20]);
			payPlanItem.setStartDate(str_arr[21]);
			payPlanItem.setInterestRate(str_arr[22] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[22]));
			payPlanItem.setResidualPrincipal(str_arr[23] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[23]));
		
			/**
			 * 如果当前贷款编号与临时贷款编号不一致，则
			 * 1.给还款计划设置还款计划项列表
			 * 2.将该还款计划添加至还款计划列表
			 * 3.新建一个还款计划对象并设置贷款编号
			 * 4.为临时贷款编号赋值
			 * 5.创建还款计划项集合并将该条还款计划项添加至还款计划项集合
			 */
			if(!tempLoanNumber.equals(str_arr[0])) {
				payPlan.setPayPlanItemList(payPlanItemList);
				payPlanList.add(payPlan);
				payPlan = new PayPlan();
				payPlan.setLoanNumber(str_arr[0]);
				payPlan.setTransactionDate(str_arr[3]);
				tempLoanNumber = str_arr[0];
				payPlanItemList = new ArrayList<PayPlanItem>();
				payPlanItemList.add(payPlanItem);
				if(content.size()!=a){
					continue;
				}
			}else{
				// 如果当前贷款编号与临时贷款编号一致，则将该条还款计划项添加到还款计划项集合中
				payPlanItemList.add(payPlanItem);
			}
			// 如果是最后一条贷款归还计划，则直接将当前还款计划项列表设置到当前还款计划并添加至还款计划列表
			if(str.equals(content.get(content.size() - 1))) {
				payPlan.setPayPlanItemList(payPlanItemList);
				payPlanList.add(payPlan);
			}
		}
		
		logger.info("贷款还款计划数据处理结果：" + JSON.toJSONString(payPlanList));
		
		/**
		 * 写链操作
		 */
//		if(!payPlanList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("payPlan", payPlanList);
//		}
		return payPlanList;
		}else{
			return null;
		}
	}
	
	@Override
	public ResponseResult queryPayPlanData(Map<String, String> param) {
		/*JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");

		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String date = param.get("date") == null ? "" : param.get("date");
		return client.queryPayPlan(loanNumber, date);*/
		return null;
	}
}
