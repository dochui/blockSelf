package com.ynet.fabric.controller;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.AcctFlowService;
import com.ynet.fabric.service.AcctService;
import com.ynet.fabric.service.AssertService;
import com.ynet.fabric.service.BfjFlowService;
import com.ynet.fabric.service.BfjSumService;
import com.ynet.fabric.service.CreditService;
import com.ynet.fabric.service.FeeService;
import com.ynet.fabric.service.RevTransService;
import com.ynet.xwfabric.util.ResponseResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * 与联合贷相关的请求控制器
 * @author qiangjiyi
 * @date 2018年2月26日 下午5:19:14
 */
@Controller
@RequestMapping("/fabric")
public class FabricQueryController {
	
	private static Logger logger = LoggerFactory.getLogger(FabricQueryController.class);
		
	@Autowired
	private AcctFlowService acctFlowService;
	
	@Autowired
	private AssertService assertService;
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private BfjFlowService bfjFlowService;
	
	@Autowired
	private AcctService acctService;
	
	@Autowired
	private BfjSumService bfjSumService;
	
	@Autowired
	private FeeService feeService;
	
	@Autowired
	private RevTransService revTransService;
	
	/**
	 * 查询资产信息
	 */
	@RequestMapping("/queryAssertData.do")
	@ResponseBody
	public ResponseResult queryAssertData(@RequestBody Map<String,String> param){
		
		ResponseResult result = assertService.queryAssertData(param);
		return result;
	}

	/**
	 * 查询授信
	 */
	@RequestMapping("/queryCreditData.do")
	@ResponseBody
	public ResponseResult queryCreditData(@RequestBody Map<String,String> param){
		
		ResponseResult result = creditService.queryCreditData(param);
		return result;
	}
	/**
	 * 查询备付金流水
	 */
	@RequestMapping("/queryBfjFlowData.do")
	@ResponseBody
	public ResponseResult queryBfjFlowData(@RequestBody Map<String,String> param){
		
		ResponseResult result = bfjFlowService.queryBfjFlowData(param);
		return result;
	}
	/**
	 * 查询日总台账
	 */
	@RequestMapping("/queryAcctData.do")
	@ResponseBody
	public ResponseResult queryAcctData(@RequestBody Map<String,String> param){
		
		ResponseResult result = acctService.queryAcctData(param);
		return result;
	}
	/**
	 * 查询备付金总额
	 */
	@RequestMapping("/queryBfjSumData.do")
	@ResponseBody
	public ResponseResult queryBfjSumData(@RequestBody Map<String,String> param){
		
		ResponseResult result = bfjSumService.queryBfjSumData(param);
		return result;
	}
	/**
	 * 查询扣费
	 */
	@RequestMapping("/queryFeeData.do")
	@ResponseBody
	public ResponseResult queryFeeData(@RequestBody Map<String,String> param){
		
		ResponseResult result = feeService.queryFeeData(param);
		return result;
	}
	/**
	 * 查询冲正
	 */
	@RequestMapping("/queryRevTransData.do")
	@ResponseBody
	public ResponseResult queryRevTransData(@RequestBody Map<String,String> param){
		
		ResponseResult result = revTransService.queryRevTransData(param);
		return result;
	}
		
	/**
	 * 查询会计分录
	 */
	@RequestMapping("/queryAcctFlowData.do")
	@ResponseBody
	public ResponseResult queryAcctFlowData(@RequestBody Map<String, String> param) {
		ResponseResult result = acctFlowService.queryAcctFlowData(param);
		logger.info("查询结果：" + JSON.toJSONString(result));
		return result;
	}
	
	
}
