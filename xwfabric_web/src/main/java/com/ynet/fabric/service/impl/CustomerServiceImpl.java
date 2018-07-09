package com.ynet.fabric.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ynet.fabric.service.CustomerService;
import com.ynet.xwfabric.domain.Customer;

/**
 * 客户相关接口
 * @author xuhui
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{
	
	private static Logger logger = LoggerFactory.getLogger(LoanDetailServiceImpl.class);

	/**
	 * 处理贷款明细
	 */
	@Override
	public List<Customer> handleCustomerData(List<String> content) {
		List<Customer> customerList = new ArrayList<Customer>();
		if(content != null && content.size()>0){
			for(String str : content){
				String[] str_arr = str.split("[|][+][|]");
				Customer customer = new Customer();
				customer.setCustomerNo(str_arr[0]);
				customer.setCertificateType(str_arr[1]);
				customer.setCertificateNum(str_arr[2]);
				customer.setName(str_arr[3]);
				customer.setSex(str_arr[4]);
				customer.setBirthday(str_arr[5]);
				customer.setPhone(str_arr[6]);
				customer.setAddress(str_arr[7]);
				customer.setDistrict(str_arr[8]);
				customer.setCity(str_arr[9]);
				customer.setProvince(str_arr[10]);
				customer.setLinkMan(str_arr[11]);
				customer.setLinkManRelation(str_arr[12]);
				customer.setLinkManPhone(str_arr[13]);
				customer.setCompanyName(str_arr[14]);
				customer.setCompanyType(str_arr[15]);
				customer.setCompanyAddress(str_arr[16]);
				customer.setCompanyDistrict(str_arr[17]);
				customer.setCompanyCity(str_arr[18]);
				customer.setCompanyProvince(str_arr[19]);
				customer.setAddressCode(str_arr[20]);
				customer.setHomeAddress(str_arr[21]);
				customer.setCommunicationCode(str_arr[22]);
				customer.setCompanyCode(str_arr[23]);
				customer.setMaritaStatus(str_arr[24]);
				customer.setEducation(str_arr[25]);
				customer.setIncome(str_arr[26] == "" ? BigDecimal.ZERO : new BigDecimal(str_arr[26]));
				customer.setMobile(str_arr[27]);
				customer.setJob(str_arr[28]);
				customer.setJobTitle(str_arr[29]);
				customer.setJobLeval(str_arr[30]);
				customer.setUpdateFlag(str_arr[31]);
				customer.setCardAddress(str_arr[32]);
				customer.setCardStartDate(str_arr[33]);
				customer.setCardEndDate(str_arr[34]);
				customerList.add(customer);
			}
			logger.info("客户数据处理结果：" + JSON.toJSONString(customerList));
			return customerList;
		}else{
			return null;
		}
		
	}


}
