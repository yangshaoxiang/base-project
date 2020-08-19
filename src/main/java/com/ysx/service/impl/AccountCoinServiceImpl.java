package com.ysx.service.impl;

import com.ysx.entity.AccountCoin;
import com.ysx.mapper.AccountCoinMapper;
import com.ysx.service.AccountCoinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author ysx
 */
@Service
public class AccountCoinServiceImpl extends ServiceImpl<AccountCoinMapper, AccountCoin> implements AccountCoinService {
}
