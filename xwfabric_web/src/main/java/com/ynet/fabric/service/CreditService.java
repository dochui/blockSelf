package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.Credit;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 授信相关接口
 * @author xuhui
 *
 */
public interface CreditService {
	/**
	 * 处理授信信息
	 */
	List<Credit> handleCreditData(List<String> content);
	
	ResponseResult queryCreditData(Map<String, String> param);
}
