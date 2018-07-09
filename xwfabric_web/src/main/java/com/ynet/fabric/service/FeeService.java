package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.Fee;
import com.ynet.xwfabric.util.ResponseResult;

/**
 *  扣费明细 接口
 * @author chengcaiyi
 *
 */
public interface FeeService {

	/**
	 * 处理扣费明细
	 */
	List<Fee> handleFeeData(List<String> content);
	
	ResponseResult queryFeeData(Map<String, String> param);
}
