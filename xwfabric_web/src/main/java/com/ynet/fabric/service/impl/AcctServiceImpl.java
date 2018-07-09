package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.AcctService;
import com.ynet.xwfabric.domain.Acct;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 日总额核对台账 接口实现 
 * @author xuhui
 *
 */
@Service
public class AcctServiceImpl implements AcctService {

	private static Logger logger = LoggerFactory.getLogger(AcctServiceImpl.class);

	@Override
	public List<Acct> handleAcctData(List<String> content) {
		List<Acct> acctList = new ArrayList<Acct>();
		if(content != null && content.size()>0){
		for(String str : content) {
			// 将每行的内容进行分割为一个字符串数组
			String[] str_arr = str.split("[|][+][|]");
			
			Acct acct= new Acct();
			acct.setReconciliationDate(str_arr[0]);
			acct.setLastLoanPrincipalBalance(str_arr[1] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[1]));
			acct.setDayIssuePrincipalTotal(str_arr[2] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[2]));
			acct.setDayRePrincipalTotal(str_arr[3] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[3]));
			acct.setDayLoanPrincipalTotal(str_arr[4] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[4]));
			acct.setLastComeInterestBalance(str_arr[5] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[5]));
			acct.setDayPlanComeInterest(str_arr[6] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[6]));
			acct.setDayComeInterest(str_arr[7] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[7]));
			acct.setDayComeInterestBalance(str_arr[8] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[8]));
			acct.setLastComeRenaltyBalance(str_arr[9] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[9]));
			acct.setDayPlanComeRenalty(str_arr[10] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[10]));
			acct.setDayComeRenalty(str_arr[11] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[11]));
			acct.setDayComeRenaltyBalance(str_arr[12] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[12]));
			acct.setDayReverseTotal(str_arr[13] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[13]));
			acct.setDayReverseInterest(str_arr[14] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[14]));
			acct.setDayReverseRenalty(str_arr[15] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[15]));
			acct.setLastComePayNormalBalance(str_arr[16] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[16]));
			acct.setDayComePayNormalCost(str_arr[17] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[17]));
			acct.setDayBuckleNormalCost(str_arr[18] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[18]));
			acct.setDayComePayNormalCostBalance(str_arr[19] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[19]));
			acct.setDayReverseNormalCost(str_arr[20] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[20]));
			acct.setLastComePayArrearageCostBalance(str_arr[21] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[21]));
			acct.setDayComePayArrearageCost(str_arr[22] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[22]));
			acct.setDayBuckleArrearageCost(str_arr[23] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[23]));
			acct.setDayComePayArrearageCostBalance(str_arr[24] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[24]));
			acct.setDayReverseArrearageCost(str_arr[25] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[25]));

			acctList.add(acct);
		
			}
			logger.info("日总额核对台账 数据处理结果：" + JSON.toJSONString(acctList));		
			return acctList;
		}else{
			return null;
		}

	}
	
	/**
	 * 查询日总台账
	 */
	@Override
	public ResponseResult queryAcctData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
		
		String reconciliationDate = param.get("reconciliationDate") == null ? "" : param.get("reconciliationDate");
		return client.queryAcct(reconciliationDate);
	}
}