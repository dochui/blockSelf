package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.BfjSum;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 备付金总额 接口
 * @author xuhui
 *
 */
public interface BfjSumService {
	/**
	 * 处理备付金总额
	 * @param content
	 * @return
	 */
	List<BfjSum> handleBfjSumData(List<String> content);
	
	ResponseResult queryBfjSumData(Map<String, String> param);
}
