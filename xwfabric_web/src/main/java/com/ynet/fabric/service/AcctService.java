package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.Acct;
import com.ynet.xwfabric.util.ResponseResult;

/**
 *  日总额核对台账 接口
 * @author xuhui
 *
 */
public interface AcctService {

	/**
	 * 处理日总额核对台账 
	 */
	List<Acct> handleAcctData(List<String> content);
	
	/**
	 * 查询日总台账
	 */
	ResponseResult queryAcctData(Map<String, String> param);
}
