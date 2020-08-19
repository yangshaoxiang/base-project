package com.ysx.common.response;

import com.ysx.util.CurrentTimeMillisClock;
import lombok.Data;

@Data
class ResultDto<T> {
	private int code;
	private String message;

	private T data;

	private Long currentTime;
	private ResultDto(T data){
		this.code = ResponseCodeEnum.SUCCESS.resultCode;
		this.message = ResponseCodeEnum.SUCCESS.resultMsg;
		this.data = data;
		this.currentTime = CurrentTimeMillisClock.getInstance().now();
	}

	private ResultDto(ResponseCodeEnum responseCodeEnum,String message){
		this.code = responseCodeEnum.resultCode;
		this.message = message;
		this.currentTime = CurrentTimeMillisClock.getInstance().now();
	}

	private ResultDto(ResponseCodeEnum responseCodeEnum) {
		this.code = responseCodeEnum.resultCode;
		this.message = responseCodeEnum.resultMsg;
		this.currentTime = CurrentTimeMillisClock.getInstance().now();
	}

	static <T> ResultDto<T> success(T data) {
		return new ResultDto<>(data);
	}

	static <T> ResultDto<T> error() {
		return new ResultDto<>(ResponseCodeEnum.ERROR);
	}

	static <T> ResultDto<T> error(String errorMsg) {
		return new ResultDto<>(ResponseCodeEnum.ERROR,errorMsg);
	}


	static <T> ResultDto<T> error(ResponseCodeEnum responseCodeEnum) {
		return new ResultDto<>(responseCodeEnum);
	}



}
