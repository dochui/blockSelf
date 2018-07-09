package com.ynet.fabric.service.impl;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.AcctFlowService;
import com.ynet.xwfabric.domain.AcctFlow;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 会计分录相关的业务接口实现
 * @author qiangjiyi
 * @date 2018年2月26日 下午6:23:20
 */
@Service
public class AcctFlowServiceImpl implements AcctFlowService {

	private static Logger logger = LoggerFactory.getLogger(AcctFlowServiceImpl.class);
	
	@Override
	public List<AcctFlow> handleAcctFlowData(List<String> content) {
		List<AcctFlow> acctFlowList = new ArrayList<AcctFlow>();
		if(content != null && content.size()>0){
			int number = 0;
		for(String str : content) {
			number++;
			// 将每行的内容进行分割为一个字符串数组
			String[] str_arr = str.split("[|][+][|]");
			
			AcctFlow acctFlow = new AcctFlow();
			acctFlow.setUuid(getMD5(number+str_arr[0]+str_arr[1]));
			acctFlow.setLoanNumber(str_arr[0]);
			acctFlow.setHappenDate(str_arr[1]);
			acctFlow.setCurrency(str_arr[2]);
			acctFlow.setBusinessCode(str_arr[3]);
			acctFlow.setBusinessDesc(str_arr[4]);
			acctFlow.setLoanFlag(str_arr[5]);
			acctFlow.setBusinessAmount(str_arr[6] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[6]));
			acctFlow.setBranchNo(str_arr[7]);
			acctFlow.setProductNo(str_arr[8]);
			acctFlow.setSubject(str_arr[9]);
			
			// 将该条会计分录添加到集合中
			acctFlowList.add(acctFlow);
		}
		
		logger.info("会计分录数据处理结果：" + JSON.toJSONString(acctFlowList));
		
		/**
		 * 写链操作
		 */
//		if(!acctFlowList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("acctFlow", acctFlowList);
//		}
		return acctFlowList;
		
		}else{
			return null;
		}
	}

	@Override
	public ResponseResult queryAcctFlowData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");

		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String date = param.get("date") == null ? "" : param.get("date");
		return client.queryAcctFlow(loanNumber, date, "");
	}


	private static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
