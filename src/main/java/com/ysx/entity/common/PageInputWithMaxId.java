package com.ysx.entity.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 公共分页参数接收对象 按时间排序 防止重复数据
 * @Author: ysx
 * @Date: 2020/4/29 13:39
 */
@Data
public class PageInputWithMaxId {
    @ApiModelProperty("当前页最后一条数据的id，无不传或传0x7fffffffffffffff的10进制数字")
    private Long lastId = Long.MAX_VALUE;
    @ApiModelProperty("每页条数 默认为20")
    private Integer pageSize = 20;
}
