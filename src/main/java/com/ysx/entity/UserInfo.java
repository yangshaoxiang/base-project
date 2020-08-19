package com.ysx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author ysx
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserInfo对象", description="用户基本信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID=1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "性别：0：女  1:男 2:保密")
    private Integer gender;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户会员编号")
    private String memberNo;

    @ApiModelProperty(value = "qq open id")
    private String qqOpenId;

    @ApiModelProperty(value = "微信 open id")
    private String weixinOpenId;

    @ApiModelProperty(value = "身份 1:普通 2:官方")
    private Integer identity;

    @ApiModelProperty(value = "状态 :0正常，1禁言")
    private Integer state;

    @ApiModelProperty(value = "逻辑删除 1 表示删除，0 表示未删除",hidden = true)
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;

}
