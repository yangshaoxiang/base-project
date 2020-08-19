package com.ysx.entity.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 注册输入参数
 * @Author: ysx
 */
@Data
public class RegisterInput {

    @ApiModelProperty(value = "密码")
    @NotBlank
    private String password;

    @ApiModelProperty(value = "用户名称")
    @NotBlank
    private String nickName;
    @Pattern(regexp = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))", message = "非法手机号")
    @ApiModelProperty(value = "手机号")
    @NotBlank
    private String phoneNum;



}
