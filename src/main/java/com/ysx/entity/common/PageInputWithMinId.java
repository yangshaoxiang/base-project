package com.ysx.entity.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 公共分页参数接收对象 按时间排序 防止重复数据
 * @Author: ysx
 * @Date: 2020/4/29 13:39
 */
@Data
public class PageInputWithMinId {
    @ApiModelProperty("当前页最后一条数据的id，无不传或传0")
    private Long lastId = 0L;
    @ApiModelProperty("每页条数 默认为20")
    private Integer pageSize = 20;
}
