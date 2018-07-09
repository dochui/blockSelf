package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.RevTrans;
import com.ynet.xwfabric.util.ResponseResult;

/**
 *  冲正类交易 接口
 * @author chengcaiyi
 *
 */
public interface RevTransService {

	/**
	 * 处理冲正类交易
	 */
	List<RevTrans> handleRevTransData(List<String> content);
	
	ResponseResult queryRevTransData(Map<String, String> param);

}
