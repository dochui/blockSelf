package com.ynet.fabric.service;

import java.util.List;
import java.util.Map;

import com.ynet.xwfabric.domain.LoanDetail;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 贷款明细相关的业务接口
 * @author qiangjiyi
 * @date 2018年2月23日 下午4:05:08
 */
public interface LoanDetailService {
	
	/**
	 * 处理贷款明细
	 */
	List<LoanDetail> handleLoanDetailData(List<String> content);

	/**
	 * 根据传入的查询条件从区块链中查询满足条件的贷款明细
	 */
	ResponseResult queryLoanDetailData(Map<String, String> param);
	
}
