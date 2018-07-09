package com.ynet.fabric.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.LoanDetailService;
import com.ynet.xwfabric.domain.LoanDetail;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * 贷款明细相关的业务接口实现
 * @author qiangjiyi
 * @date 2018年2月23日 下午4:12:36
 */
@Service
public class LoanDetailServiceImpl implements LoanDetailService {
	
	private static Logger logger = LoggerFactory.getLogger(LoanDetailServiceImpl.class);
	
	@Override
	public List<LoanDetail> handleLoanDetailData(List<String> content) {
		List<LoanDetail> loanDetailList = new ArrayList<LoanDetail>();
		if(content != null && content.size()>0){
		for(String str : content) {
			// 将每行的内容进行分割为一个字符串数组
			String[] str_arr = str.split("[|][+][|]");
			
			LoanDetail loanDetail = new LoanDetail();
			loanDetail.setLoanNumber(str_arr[0]);
			loanDetail.setPeriods(str_arr[1] == "" ? 0 : Integer.parseInt(str_arr[1]));
			loanDetail.setLoanDate(str_arr[2]);
			loanDetail.setLoanEndDate(str_arr[3]);
			loanDetail.setFirstPayDate(str_arr[4]);
			loanDetail.setLoanAmount(str_arr[5] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[5]));
			loanDetail.setLoanPurpose(str_arr[6]);
			loanDetail.setPayMethod(str_arr[7]);
			loanDetail.setRate(str_arr[8] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[8]));
			loanDetail.setCustomerName(str_arr[9]);
			loanDetail.setTransactionDate(str_arr[10]);
			loanDetail.setStopInterestFlag(str_arr[11]);
			loanDetail.setRepaidPeriod(str_arr[12] == "" ? 0 : Integer.parseInt(str_arr[12]));
			loanDetail.setSettleOnDate(str_arr[13]);
			loanDetail.setRateType(str_arr[14]);
			loanDetail.setRepaymentPeriod(str_arr[15]);
			loanDetail.setLoanType(str_arr[16]);
			loanDetail.setBorrowerType(str_arr[17]);
			loanDetail.setProductNo(str_arr[18]);
			loanDetail.setProductName(str_arr[19]);
			loanDetail.setAccountState(str_arr[20]);
			loanDetail.setGuaranteeMethod(str_arr[21]);
			loanDetail.setNormalBalance(str_arr[22] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[22]));
			loanDetail.setDelayBalance(str_arr[23] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[23]));
			loanDetail.setPenaltyAmount(str_arr[24] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[24]));
			loanDetail.setDebitInterestAmount(str_arr[25] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[25]));
			loanDetail.setPenaltyRate(str_arr[26] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[26]));
			loanDetail.setCustomerNo(str_arr[27]);
			loanDetail.setContractNo(str_arr[28]);
			loanDetail.setMaxDelayDays(str_arr[29] == "" ? 0 : Integer.parseInt(str_arr[29]));// 最大延滞天数 截止目前为止，该笔借据逾期且未结清最小一期的延滞天数（订单最大逾期天数）
			loanDetail.setReceiverBankNo(str_arr[30]);// 收款人银行账号
			loanDetail.setReceiverBankType(str_arr[31]);// 收款人账户银行
			loanDetail.setReceiverName(str_arr[32]);// 收款人名称
			loanDetail.setUpdateflag(str_arr[33]);//更新标识
			loanDetail.setTransactionFlowNo(str_arr[34]);// 交易流水号
			
			// 将该条贷款明细添加到集合中
			loanDetailList.add(loanDetail);
		}
		
		logger.info("贷款明细数据处理结果：" + JSON.toJSONString(loanDetailList));
		
		/**
		 * 写链操作
		 */
//		if(!loanDetailList.isEmpty()) {
//			JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
//			client.batchWriteData("loanDetail", loanDetailList);
//		}
		return loanDetailList;
		}else{
			return null;
		}
		/*List<LoanDetail> result = new ArrayList<LoanDetail>();
		result.addAll(loanDetailList);
		
		String chaincodeName = "loandetail";
		
		try {
			DemoClient belinkClient = new DemoClient().timeoutMillis(30 * 1000).ccId(chaincodeName, "1.0")
					.initClient("BelinkMSP").createChannel("belink-config.yaml");
			DemoClient xinWangClient = new DemoClient().timeoutMillis(30 * 1000).ccId(chaincodeName, "1.0")
					.initClient("XinWangMSP").createChannel("xinwang-config.yaml");
			List<DemoClient> clients = new ArrayList<>();
			clients.add(belinkClient);
			clients.add(xinWangClient);
			
			// 写数据到区块链
			if(writeBlockChain(loanDetailList, 1, belinkClient, clients)) {
				logger.info("数据写入区块链成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("区块链网络出现异常");
		}*/
		
	}

	/**
	 * 将指定内容写入区块链
	 */
	/*private boolean writeBlockChain(List<LoanDetail> loanDetailList, int num, DemoClient belinkClient, List<DemoClient> clients) {
		Iterator<LoanDetail> iterator = loanDetailList.iterator();
		while(iterator.hasNext()) {
			try {
				belinkClient.callMutilEndorseChaincode(clients, "insert", JSON.toJSONString(iterator.next()));
				// Thread.sleep(5000);
				// 如果进行到这一步，则代表写入成功，从集合中移除该元素
				iterator.remove();
			} catch (Exception e) {
				e.printStackTrace();
				if(num < 5) {
					if(loanDetailList.size() > 0){
						logger.info("第" + num + "次往区块链写入数据中断，开始尝试第" + ++num + "继续写入");
						return this.writeBlockChain(loanDetailList, num, belinkClient, clients);
					}
				} else {
					throw new JoinLoanException(ErrorCodeConstants.WRITE_BLOCK_CHAIN_ERROR);
				}
			}
		}
		
		return true;
	}*/

	@Override
	public ResponseResult queryLoanDetailData(Map<String, String> param) {
		/*JoinLoanClient client = JoinLoanClient.getInstance("BelinkMSP");
		String loanNumber = param.get("loanNumber") == null ? "" : param.get("loanNumber");
		String date = param.get("date") == null ? "" : param.get("date");
		return client.queryLoanDetail(loanNumber, date);*/
		return null;
		
	}
}
