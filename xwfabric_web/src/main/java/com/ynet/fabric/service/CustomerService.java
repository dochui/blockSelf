package com.ynet.fabric.service;

import java.util.List;

import com.ynet.xwfabric.domain.Customer;

/**
 * 客户相关接口
 * @author xuhui
 *
 */
public interface CustomerService {
	
	List<Customer> handleCustomerData(List<String> content);
}
