package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.BfjFlow;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 备付金流水接口
 * @author xuhui
 *
 */
public interface BfjFlowService {
	
	/**
	 * 处理备付金流水
	 */
	List<BfjFlow> handleBfjFlowData(List<String> content);
	
	ResponseResult queryBfjFlowData(Map<String, String> param);
}
