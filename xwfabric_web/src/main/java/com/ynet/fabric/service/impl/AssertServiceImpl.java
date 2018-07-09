package com.ynet.fabric.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ynet.fabric.service.AssertService;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;


/**
 * 查询资产信息
 * @author xuhui
 *
 */
@Service
public class AssertServiceImpl implements AssertService {
	
	private static Logger logger = LoggerFactory.getLogger(AssertServiceImpl.class);

	@Override
	public ResponseResult queryAssertData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
		
		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String date = param.get("date") == null ? "" : param.get("date");
		String dataType = param.get("dataType") == null ? "" : param.get("dataType");
		return client.queryAssertDetail(dataType, loanNumber, date);
	}

}
