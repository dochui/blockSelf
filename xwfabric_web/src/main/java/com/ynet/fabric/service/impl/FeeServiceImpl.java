package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.FeeService;
import com.ynet.xwfabric.domain.Fee;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * Created by kuzi on 2018/5/22.
 */
@Service
public class FeeServiceImpl implements FeeService {

    private static Logger logger = LoggerFactory.getLogger(FeeServiceImpl.class);
    @Override
    public List<Fee> handleFeeData(List<String> content) {

        logger.info("开始处理扣费明细");
        List<Fee> feeList = new ArrayList<Fee>();
        if (content != null && content.size() > 0) {
            for (String str : content) {
                // 将每行的内容进行分割为一个字符串数组
                String[] str_arr = str.split("[|][+][|]");
                Fee fee = new Fee();
                fee.setDueDate(str_arr[0]);
                fee.setPayableAmount(str_arr[1] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[1]));
                fee.setActualTransactionDate(str_arr[2]);
                fee.setActualDeductionAmount(str_arr[3] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[3]));
                fee.setDeductionStatus(str_arr[4]);
                fee.setTransactionSerialNo(str_arr[5]);
                fee.setDeductionRule(str_arr[6]);
                fee.setDeductionRuleName(str_arr[7]);
                fee.setDeductionRuleCode(str_arr[8]);
                fee.setBasedOn(str_arr[9] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[9]));
                fee.setRate(str_arr[10] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[10]));
                feeList.add(fee);
            }
            logger.info("扣费明细数据处理结果：" + JSON.toJSONString(feeList));

            /**
             * 写链操作
             */
//		if(!specialBusinessList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("fee", feeList);
//		}
            return feeList;
        } else {
            return null;
        }
    }
    
    
	@Override
	public ResponseResult queryFeeData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");		
		String transactionSerialNo = param.get("transactionSerialNo") == null ? "" : param.get("transactionSerialNo");
		String actualTransactionDate = param.get("actualTransactionDate") == null ? "" : param.get("actualTransactionDate");
		return client.queryFee(transactionSerialNo, actualTransactionDate);
	}
}
