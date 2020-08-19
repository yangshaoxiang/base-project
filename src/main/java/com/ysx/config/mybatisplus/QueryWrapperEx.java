package com.ysx.config.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;

/**
 * 拓展 MyBatis Plus QueryWrapper 类，主要增加如下功能：
 *
 * 1. 拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 *
 * @param <T> 数据类型
 */
public class QueryWrapperEx<T> extends QueryWrapper<T> {

    public QueryWrapperEx<T> likeIfPresent(String column, String val) {
        if (StringUtils.hasText(val)) {
            return (QueryWrapperEx<T>) super.like(column, val);
        }
        return this;
    }

    public QueryWrapperEx<T> eqIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperEx<T>) super.eq(column, val);
        }
        return this;
    }

    public QueryWrapperEx<T> ltIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperEx<T>) super.lt(column, val);
        }
        return this;
    }

    public QueryWrapperEx<T> gtIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperEx<T>) super.gt(column, val);
        }
        return this;
    }


    public QueryWrapperEx<T> leIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperEx<T>) super.le(column, val);
        }
        return this;
    }

    public QueryWrapperEx<T> geIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperEx<T>) super.ge(column, val);
        }
        return this;
    }

    public QueryWrapperEx<T> neIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapperEx<T>) super.ne(column, val);
        }
        return this;
    }


}
