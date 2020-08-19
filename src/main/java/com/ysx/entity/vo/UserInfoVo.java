package com.ysx.entity.vo;

import com.ysx.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 用户详细信息
 * @Author: ysx
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="UserInfoVo", description="用户展示对象")
public class UserInfoVo extends UserInfo {
    @ApiModelProperty(value = "账户余额")
    private Integer coinCount;

}
