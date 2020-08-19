package com.ysx.common.auth;


import lombok.Data;

/**
 * @Description: 生成 jwt token 元数据
 * @author ysx
 */
@Data
public class TokenUserModel {
	private Long loginId;
	private String name;

}
