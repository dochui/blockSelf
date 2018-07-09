package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.SpecialBusiness;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 特殊交易相关的业务接口
 * @author qiangjiyi
 * @date 2018年2月26日 下午6:08:18
 */
public interface SpecialBusinessService {
	
	/**
	 * 处理特殊交易
	 */
	List<SpecialBusiness> handleSpecialBusinessData(List<String> content);
	/**
	 * 查询特殊交易
	 * 
	 */
	ResponseResult querySpecialBusinessData(Map<String, String> param);
}
