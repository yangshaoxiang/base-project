package com.ysx.common.response;




/**
 *  业务异常
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private ResponseCodeEnum responseCodeEnum;


	public BusinessException(ResponseCodeEnum errorInfoInterface) {
		this.responseCodeEnum = errorInfoInterface;
	}

	ResponseCodeEnum getResponseCodeEnum(){
		return this.responseCodeEnum;
	}





}