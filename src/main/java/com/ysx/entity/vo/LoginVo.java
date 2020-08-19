package com.ysx.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description: 登录响应对象
 * @Author: ysx
 */
@Data
@ApiModel(value="注册登录响应实体")
public class LoginVo {
    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "性别：0：女  1:男 2:保密")
    private Integer gender;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "身份 1:普通 2:官方")
    private Integer identity;

    @ApiModelProperty(value = "状态 :0正常，1禁言")
    private Integer state;

    @ApiModelProperty(value = "用户token")
    private String token;

}
