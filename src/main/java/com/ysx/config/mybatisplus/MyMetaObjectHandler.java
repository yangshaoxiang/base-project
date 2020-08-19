package com.ysx.config.mybatisplus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @Description: mybatis-plus 自动填充策略
 * @author ysx
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略 插入时，自动填充创建时间，更新时间
     */
  @Override
  public void insertFill(MetaObject metaObject) {
      Date currentTime = new Date();
      this.setFieldValByName("createTime",currentTime,metaObject);
      this.setFieldValByName("updateTime", currentTime,metaObject);
 }

    /**
     * 更新时的填充策略
     */
  @Override
  public void updateFill(MetaObject metaObject) {}
}