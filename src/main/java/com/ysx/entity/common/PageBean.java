package com.ysx.entity.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 分页统一实体
 * @author ysx
 */
@Data
@ApiModel(value="分页统一实体")
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = -9202109574544652243L;
    @ApiModelProperty(value = "总记录数")
    private long total;
    @ApiModelProperty(value = "当前请求的第几页")
    private long pageNum;
    @ApiModelProperty(value = "请求的条数")
    private long pageSize;
    @ApiModelProperty(value = "总页数")
    private long pages;
    @ApiModelProperty(value = "结果集")
    private List<T> list;

    public PageBean (){}

    /**
     *  使用 pagehelper 分页产生的结果
     * @param page 分页结果
     */
    public PageBean(com.github.pagehelper.Page<T> page) {
        initGitHubBasePage(page);
        initGitHubDataPage(page);
    }


    /**
     *  使用 pagehelper 分页产生的结果 并转换
     * @param page 分页结果
     */
    public PageBean(com.github.pagehelper.Page page,List<T> targetList){
        initGitHubBasePage(page);
        setList(targetList);
    }


    /**
     *  使用 mybatis-plus 分页产生的结果
     * @param page 分页结果
     */
    public PageBean(Page<T> page) {
        initBasePage(page);
        initDataPage(page.getRecords());
    }

    /**
     *  使用 mybatis-plus 分页产生的结果 并转换
     * @param page 分页结果
     */
    public PageBean(Page page,List<T> targetList){
        initBasePage(page);
        setList(targetList);
    }

    /**
     * 初始化分页相关信息 要求 list 必须是 com.github.pagehelper.Page 类型
     */
    private void initGitHubBasePage(com.github.pagehelper.Page page){
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }

    /**
     * 初始化分页相关信息
     */
    private void initBasePage(Page page){
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }


    /**
     * 初始化分页 集合数据
     */
    private void initGitHubDataPage(com.github.pagehelper.Page<T> page){
        this.list = page.getResult();
    }

    /**
     * 初始化分页 集合数据
     */
    private void initDataPage(List<T> list){
          this.list = list;
    }



}
