package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.PayLog;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 还款计划明细相关业务接口
 * @author xuhui
 */
public interface PayLogService {

	/**
	 * 处理还款明细
	 */
	List<PayLog> handlePayLogData(List<String> content);
	
	/**
	 * 查询还款明细
	 */
	ResponseResult queryPayLogData(Map<String, String> param);
}
