package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.CreditService;
import com.ynet.xwfabric.domain.Credit;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 处理授信信息接口
 * @author xuhui
 *
 */
@Service
public class CreditServiceImpl implements CreditService {
	
	private static Logger logger = LoggerFactory.getLogger(CreditServiceImpl.class);

	@Override
	public List<Credit> handleCreditData(List<String> content) {
		List<Credit> creditList = new ArrayList<Credit>();
		if(content != null && content.size() > 0){
			for(String str:content){
				String[] str_arr = str.split("[|][+][|]");
				Credit credit = new Credit();
				credit.setCustomerNo(str_arr[0]);
				credit.setCreditNum(str_arr[1]);
				credit.setCreditType(str_arr[2]);
				credit.setEffectiveCredit(str_arr[3] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[3]));
				credit.setUsedCredit(str_arr[4] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[4]));
				credit.setNoUsedAmount(str_arr[5] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[5]));
				credit.setPayNoComingAmount(str_arr[6] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[6]));
				credit.setExpirationDate(str_arr[7]);
				credit.setCreditStartDate(str_arr[8]);
				credit.setContractNo(str_arr[9]);
				creditList.add(credit);
			}
			logger.info("授信数据处理结果：" + JSON.toJSONString(creditList));
			return creditList;
		}else{
			return null;
		}

	}
	@Override
	public ResponseResult queryCreditData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");		
		String customerNo = param.get("customerNo") == null ? "" : param.get("customerNo");
		String creditType = param.get("creditType") == null ? "" : param.get("creditType");
		return client.queryCredit(customerNo, creditType);
	}
}
