package com.ysx.common.response;

/**
 *  @Description: 针对请求的响应码及信息 实际文案展示由客户端根据响应码决定，后端信息仅供各客户端参考
 * @author ysx
 */
public enum ResponseCodeEnum {
	// -1 其他 未知异常
	ERROR(-1, "服务处理异常"),

	// 数据操作错误定义
	SUCCESS(200, "成功!"),
	// 6XX 系统相关错误码
	ERROR_SYSTEM_NO_LOGIN(601,"未登录或登录已过期"),
	ERROR_SYSTEM_PARAM_CHECK(603,"参数错误"),
	ERROR_SYSTEM_SIGN_NOT_ACCESS(604,"接口验签不予通过"),
	ERROR_SYSTEM_LOCK_PARAM_ERROR(605,"RedisLock 参数名列表和值不匹配"),
	ERROR_SYSTEM_LOCK_TIMEOUT_ERROR(606,"获取分布式锁超时"),


	// 7XX 第三方相关错误码

	// 8xx 用户相关错误码
	ERROR_USER_LOGIN(801,"手机号或密码错误"),
	ERROR_USER_REGISTER_PHONE_EXIST(802,"手机号已注册"),
	ERROR_USER_REGISTER_NICKNAME_EXIST(803,"昵称已存在"),
	ERROR_USER_NOT_EXIST(804,"用户不存在"),

	;

	/** 错误码 */
	public int resultCode;

	/** 错误描述 */
	public String resultMsg;

	ResponseCodeEnum(int resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}



}