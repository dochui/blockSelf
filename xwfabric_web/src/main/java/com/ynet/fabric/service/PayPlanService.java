package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.PayPlan;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 贷款归还计划（还款计划）相关的业务接口
 * @author qiangjiyi
 * @date 2018年2月23日 下午5:07:55
 */
public interface PayPlanService {

	/**
	 * 处理贷款归还计划
	 */
	List<PayPlan> handlePayPlanData(List<String> content);
	
	/**
	 * 查询还款计划
	 */
	ResponseResult queryPayPlanData(Map<String, String> param);
}
