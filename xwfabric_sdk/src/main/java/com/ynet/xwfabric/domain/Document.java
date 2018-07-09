package com.ynet.xwfabric.domain;

import java.io.Serializable;
/**
 * 贷款合同 身份证正反面hash
 * @author chengcaiyi
 *
 */
public class Document implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String hash;  //文件摘要hash

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
}
