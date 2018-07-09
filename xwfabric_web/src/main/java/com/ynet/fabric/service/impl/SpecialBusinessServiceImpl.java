package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.SpecialBusinessService;
import com.ynet.xwfabric.domain.SpecialBusiness;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 特殊交易相关的业务接口实现
 * @author qiangjiyi
 * @date 2018年2月26日 下午6:22:43
 */
@Service
public class SpecialBusinessServiceImpl implements SpecialBusinessService {

	private static Logger logger = LoggerFactory.getLogger(SpecialBusinessServiceImpl.class);
	
	@Override
	public List<SpecialBusiness> handleSpecialBusinessData(List<String> content) {
		List<SpecialBusiness> specialBusinessList = new ArrayList<SpecialBusiness>();
		if(content != null && content.size()>0){
		for(String str : content) {
			// 将每行的内容进行分割为一个字符串数组
			String[] str_arr = str.split("[|][+][|]");
			
			SpecialBusiness specialBusiness = new SpecialBusiness();
			specialBusiness.setLoanNumber(str_arr[0]);
			specialBusiness.setHappenDate(str_arr[1]);
			specialBusiness.setBusinessType(str_arr[2]);
			specialBusiness.setBusinessAmount(str_arr[3] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[3]));
			specialBusiness.setChangePeriod(str_arr[4] == "" ? 0 : Integer.parseInt(str_arr[4]));
			specialBusiness.setDetailInfo(str_arr[5]);
			specialBusiness.setPayPlanChangeFlag(str_arr[6]);
			
			// 将该条特殊交易添加到集合中
			specialBusinessList.add(specialBusiness);
		}
		
		logger.info("特殊交易数据处理结果：" + JSON.toJSONString(specialBusinessList));
		
		/**
		 * 写链操作
		 */
//		if(!specialBusinessList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("specialBusiness", specialBusinessList);
//		}
		return specialBusinessList;
		}else{
			return null;
		}
	}

	@Override
	public ResponseResult querySpecialBusinessData(Map<String, String> param) {
		/*JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");

		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String date = param.get("date") == null ? "" : param.get("date");
		return client.querySpecialBusiness(loanNumber, date);*/
		return null;
	}

}
