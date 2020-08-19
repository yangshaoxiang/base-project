package com.ysx.common.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

/**
 * @Description:  全局异常处理
 * @author ysx
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	
	/**
	 * 处理自定义的业务异常
	 * @param e 异常对象
	 */
    @ExceptionHandler(value = BusinessException.class)
	public ResultDto bizExceptionHandler(BusinessException e){
		ResponseCodeEnum responseCodeEnum = e.getResponseCodeEnum();
		log.warn("请求异常 原因是：{}",responseCodeEnum.resultMsg);
    	return ResultDto.error(responseCodeEnum);
    }


	/**
	 * HttpRequestMethodNotSupportedException
	 * @param e 异常对象
	 */
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResultDto bizExceptionHandler(HttpRequestMethodNotSupportedException e){
		log.warn("请求异常 原因是",e);
		return ResultDto.error("当前请求类型不支持:"+e.getMessage());
	}

	/**
	 *  若使用 @Valid 参数校验框架，参数校验不通过 异常处理
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultDto<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		String defaultMessage = getBindingResultErrMsg(e.getBindingResult());
		ResponseCodeEnum errorSystemParamCheck = ResponseCodeEnum.ERROR_SYSTEM_PARAM_CHECK;
		if(!Objects.equals(defaultMessage.trim(),"")){
			errorSystemParamCheck.resultMsg = defaultMessage;
		}
		return ResultDto.error(errorSystemParamCheck);
	}


	/**
	 * 处理框架参数绑定异常
	 */
	@ExceptionHandler(value = {BindException.class,MethodArgumentTypeMismatchException.class,HttpMessageNotReadableException.class})
	public ResultDto bindExceptionHandler(Exception e){
		ResponseCodeEnum errorSystemParamCheck = ResponseCodeEnum.ERROR_SYSTEM_PARAM_CHECK;
		// 当 get 方式提交数据  @Valid 参数校验不通过时 抛出 BindException 异常
		if(e instanceof BindException){
			BindException bindException = (BindException) e;
			String bindingResultErrMsg = getBindingResultErrMsg(bindException.getBindingResult());
			if(!Objects.equals(bindingResultErrMsg.trim(),"")){
				errorSystemParamCheck.resultMsg = bindingResultErrMsg;
				return ResultDto.error(errorSystemParamCheck);
			}
		}
		errorSystemParamCheck.resultMsg = "参数绑定错误 注意数字或布尔类型参数不可传字符串null等";
		log.warn("框架参数绑定异常",e);
		return ResultDto.error(errorSystemParamCheck);
	}


    /**
	 * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
	public ResultDto exceptionHandler(Exception e){
    	log.error("未知异常！原因是:",e);
       	return ResultDto.error();
    }



	/**
	 *  参数校验异常结果转化为 字符串提示信息
	 * @param bindingResult 参数校验异常结果
	 * @return 字符串提示信息
	 */
	private String getBindingResultErrMsg(BindingResult bindingResult){
		if(bindingResult == null){
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (FieldError error : bindingResult.getFieldErrors()) {
			String field = error.getField();
			Object value = error.getRejectedValue();
			String msg = error.getDefaultMessage();
			String message = String.format("参数错误 错误字段：%s，错误值：%s，原因：%s；", field, value, msg);
			stringBuilder.append(message).append(System.getProperty("line.separator")).append("    ");
		}
		return stringBuilder.toString();
	}
}
