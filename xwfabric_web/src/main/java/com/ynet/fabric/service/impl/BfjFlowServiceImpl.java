package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.BfjFlowService;
import com.ynet.xwfabric.domain.BfjFlow;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 备付金流水接口实现
 * @author xuhui
 *
 */
@Service
public class BfjFlowServiceImpl implements BfjFlowService {
	
	private static Logger logger = LoggerFactory.getLogger(BfjFlowServiceImpl.class);
	@Override
	public List<BfjFlow> handleBfjFlowData(List<String> content) {
		List<BfjFlow> bfjFlowList = new ArrayList<BfjFlow>();
		if(content != null && content.size()>0){
			for(String str : content) {
				// 将每行的内容进行分割为一个字符串数组
				String[] str_arr = str.split("[|][+][|]");
				
				BfjFlow bfjFlow= new BfjFlow();
				
				bfjFlow.setBfjNo(str_arr[0]);
				bfjFlow.setTransactionDate(str_arr[1]);
				bfjFlow.setSubject(str_arr[2]);
				bfjFlow.setTransactionAmount(str_arr[3] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[3]));
				bfjFlow.setTransactionDesc(str_arr[4]);
				bfjFlow.setLoanFlag(str_arr[5]);
				bfjFlow.setLoanNumber(str_arr[6]);
				bfjFlow.setTransactionSerialNo(str_arr[7]);
				bfjFlowList.add(bfjFlow);
			}
			
		  logger.info("备付金流水数据处理结果：" + JSON.toJSONString(bfjFlowList));		
		  return bfjFlowList;
		}else{
			return null;
		}

	}
	
	/**
	 * 查询备付金流水
	 */
	@Override
	public ResponseResult queryBfjFlowData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
		
		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String transactionDate = param.get("transactionDate") == null ? "" : param.get("transactionDate");
		String bfjNo = param.get("bfjNo") == null ? "" : param.get("bfjNo");
		return client.queryBfjFlow(bfjNo, transactionDate, loanNumber);
	}
}