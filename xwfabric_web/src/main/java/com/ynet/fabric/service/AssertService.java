package com.ynet.fabric.service;

import java.util.Map;

import com.ynet.xwfabric.util.ResponseResult;


/**
 * 资产查询接口
 * @author xuhui
 *
 */
public interface AssertService {
	
	/**
	 * 查询资产
	 */
	ResponseResult queryAssertData(Map<String, String> param);
}
