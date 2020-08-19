package com.ysx.entity.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 分页请求公共入参
 * @Author: ysx
 */
@Data
public class PageInput {
    @ApiModelProperty("页码 默认为1")
    private int pageNum = 1;
    @ApiModelProperty("每页条数 默认为20")
    private int pageSize = 20;

}
