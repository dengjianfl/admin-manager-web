package com.admin.manager.web.vo;

import java.io.Serializable;
/**
 * 超时的时候，设置错误码
 * @author 18163
 *
 */
public class ErrorCodeVO implements Serializable{
	
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
