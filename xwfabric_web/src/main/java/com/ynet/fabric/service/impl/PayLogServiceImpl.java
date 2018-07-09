package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.PayLogService;
import com.ynet.xwfabric.domain.PayLog;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 还款计划明细相关业务接口实现
 * @author qiangjiyi
 * @date 2018年3月8日 下午5:51:39
 */
@Service
public class PayLogServiceImpl implements PayLogService{
	
	private static Logger logger = LoggerFactory.getLogger(PayLogServiceImpl.class);
	@Override
	public List<PayLog> handlePayLogData(List<String> content) {
		if(content != null && content.size()>0){
			List<PayLog> payLogList = new ArrayList<PayLog>();
			for(String str : content) {
				// 将每行的内容进行分割为一个字符串数组
				String[] str_arr = str.split("[|][+][|]");
				PayLog  payLog = new PayLog();
				payLog.setLoanNumber(str_arr[0]);
				payLog.setTransactionDate(str_arr[1]);
				payLog.setTransactionPrincipal(str_arr[2] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[2]));
				payLog.setTransactionInterest(str_arr[3] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[3]));
				payLog.setTransactionCost(str_arr[4] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[4]));
				payLog.setTransactionPenalty(str_arr[5] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[5]));
				payLog.setEntryDate(str_arr[6]);
				payLog.setEntryAmount(str_arr[7] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[7]));
				payLog.setPeriod(str_arr[8] == "" ? 0 : Integer.parseInt(str_arr[8]));
				payLog.setRepayFlag(str_arr[9]);
				payLog.setTransactionSerialNo(str_arr[10]);
				
				// 将该条还款明细添加到集合中
				payLogList.add(payLog);
			}
		
		logger.info("还款明细数据处理结果：" + JSON.toJSONString(payLogList));
		
		/**
		 * 写链操作
		 */
//		if(!payLogList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("payLog", payLogList);
//		}
		return payLogList;
		}else{
			return null;
		}
	}

	@Override
	public ResponseResult queryPayLogData(Map<String, String> param) {
		/*JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");

		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String date = param.get("date") == null ? "" : param.get("date");
		String transactionSerialNo = param.get("transactionSerialNo") == null ? "" : param.get("transactionSerialNo");
		return client.queryPayLog(loanNumber, date, transactionSerialNo);*/
		return null;
	}
	
}