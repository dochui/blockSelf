package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.AcctFlow;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 会计分录相关的业务接口
 * @author qiangjiyi
 * @date 2018年2月26日 下午6:09:58
 */
public interface AcctFlowService {

	/**
	 * 处理会计分录
	 */
	List<AcctFlow> handleAcctFlowData(List<String> content);
	/**
	 * 查询会计分录
	 * 
	 */
	ResponseResult queryAcctFlowData(Map<String, String> param);
}
