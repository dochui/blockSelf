package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.RevTransService;
import com.ynet.xwfabric.domain.RevTrans;
import com.ynet.xwfabric.fabric.JoinLoanClient;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * Created by kuzi on 2018/5/22.
 */

@Service
public class RevTransServiceImpl implements RevTransService {

    private static Logger logger = LoggerFactory.getLogger(RevTransServiceImpl.class);

    @Override
    public List<RevTrans> handleRevTransData(List<String> content) {

        logger.info("开始处理冲正交易");
        List<RevTrans> revTransList = new ArrayList<RevTrans>();
        if (content != null && content.size() > 0) {
            for (String str : content) {
                // 将每行的内容进行分割为一个字符串数组
                String[] str_arr = str.split("[|][+][|]");
                RevTrans revTrans = new RevTrans();
                revTrans.setLoanNumber(str_arr[0]);
                revTrans.setRushReason(str_arr[1]);
                revTrans.setOriginalTransactionDate(str_arr[2]);
                revTrans.setRushDate(str_arr[3]);
                revTrans.setRushAmount(str_arr[4] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[4]));
                revTrans.setRushPrincipal(str_arr[5] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[5]));
                revTrans.setRusInterest(str_arr[6] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[6]));
                revTrans.setRushPenaltyInterest(str_arr[7] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[7]));
                revTrans.setRushCost(str_arr[8] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[8]));
                revTransList.add(revTrans);
            }
            logger.info("冲正交易数据处理结果：" + JSON.toJSONString(revTransList));

            /**
             * 写链操作
             */
//		if(!specialBusinessList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("revTrans", revTransList);
//		}
            return revTransList;
        } else {
            return null;
        }
    }
    
	@Override
	public ResponseResult queryRevTransData(Map<String, String> param) {
		JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");		
		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String rushReason = param.get("rushReason") == null ? "" : param.get("rushReason");
		String rushDate = param.get("rushDate") == null ? "" : param.get("rushDate");		
		return client.queryRevTrans(loanNumber, rushReason, rushDate);
	}
}
