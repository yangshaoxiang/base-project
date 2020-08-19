package com.ysx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户账户
 * </p>
 *
 * @author ysx
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AccountCoin对象", description="用户账户")
public class AccountCoin implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "account_id", type = IdType.AUTO)
    private Long accountId;

    @ApiModelProperty(value = "账户归属的用户id")
    private Long userId;

    @ApiModelProperty(value = "可用币量")
    private Integer coinUsable;

    @ApiModelProperty(value = "冻结币量")
    private Integer coinFreeze;

    @ApiModelProperty(value = "账户状态 :1正常，2冻结")
    private Integer state;

    @ApiModelProperty(value = "逻辑删除 1 表示删除，0 表示未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;


}
