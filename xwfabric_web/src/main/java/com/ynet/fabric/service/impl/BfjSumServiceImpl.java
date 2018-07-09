package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.BfjSumService;
import com.ynet.xwfabric.domain.BfjSum;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 备付金总额接口实现
 * @author xuhui
 *
 */
@Service
public class BfjSumServiceImpl implements BfjSumService {

	private static Logger logger = LoggerFactory.getLogger(AcctServiceImpl.class);
	@Override
	public List<BfjSum> handleBfjSumData(List<String> content) {
		List<BfjSum> bfjSumList = new ArrayList<BfjSum>();
		if(content != null && content.size()>0){
			for(String str : content) {
				// 将每行的内容进行分割为一个字符串数组
				String[] str_arr = str.split("[|][+][|]");
				
				BfjSum bfjSum= new BfjSum();
				bfjSum.setBfjNo(str_arr[0]);
				bfjSum.setTransactionDate(str_arr[1]);
				bfjSum.setAmountBalance(str_arr[2] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[2]));
				bfjSumList.add(bfjSum);
			}
			logger.info("备付金总额数据处理结果：" + JSON.toJSONString(bfjSumList));		
			return bfjSumList;
		}else{
			return null;
		}
		
	}
	
	@Override
	public ResponseResult queryBfjSumData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");		
		String transactionDate = param.get("transactionDate") == null ? "" : param.get("transactionDate");
		String bfjNo = param.get("bfjNo") == null ? "" : param.get("bfjNo");
		return client.queryBfjSum(bfjNo, transactionDate);
	}
}