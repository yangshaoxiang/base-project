package com.ysx.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysx.common.auth.Anonymous;
import com.ysx.common.auth.JwtTokenService;
import com.ysx.common.auth.TokenUserModel;
import com.ysx.common.constant.LockKeyPrefixConstant;
import com.ysx.common.frameworkext.jsonignore.JsonFieldIgnore;
import com.ysx.common.frameworkext.requestbodyparam.RequestBodyParam;
import com.ysx.common.frameworkext.staticvalue.StaticAutowired;
import com.ysx.common.frameworkext.staticvalue.StaticValue;
import com.ysx.common.response.BusinessException;
import com.ysx.common.response.ResponseCodeEnum;
import com.ysx.entity.AccountCoin;
import com.ysx.entity.UserInfo;
import com.ysx.entity.input.RegisterInput;
import com.ysx.entity.vo.LoginVo;
import com.ysx.entity.vo.UserInfoVo;
import com.ysx.service.AccountCoinService;
import com.ysx.service.UserInfoService;
import com.ysx.util.lock.RedisLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

import static com.ysx.common.constant.SystemConstant.BASE_APP_URL;

/**
 * 用户基础行为控制层
 *
 * @author ysx
 * @since 2020-03-31 10:30:28
 */
@RestController
@RequestMapping(BASE_APP_URL +"/user/action")
@Api(description = "用户基础行为",tags = {"APP-用户基础行为"})
public class UserBaseActionController {
    @Resource
    private JwtTokenService jwtTokenService;
    @Resource
    private UserInfoService userInfoService;

    @StaticAutowired
    private static AccountCoinService accountCoinService;
    @StaticValue("${server.port}")
    private static  Integer currentPort;


    @ApiOperation(value = "用户手机号密码登录",notes = "用户手机号密码登录 测试数据 {\"phone\":\"13832249872\",\"password\":\"123456\"}")
    @PostMapping("/login")
    public LoginVo login(@RequestBodyParam String phone, @RequestBodyParam  String password)  {
        if(StrUtil.hasBlank(phone,password)){
            throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_PARAM_CHECK);
        }
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("phone_num", phone));
        if(userInfo == null){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_NOT_EXIST);
        }
        if(!Objects.equals(password,userInfo.getPassword())){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_LOGIN);
        }
        return getLoginVo(userInfo);
    }


    @ApiOperation(value = "查询个人信息",notes = "查询个人信息")
    @GetMapping("/getSelfInfo")
    @JsonFieldIgnore({"isDeleted","updateTime"})
    public UserInfoVo getSelfInfo(TokenUserModel userModel){
        Long userId = userModel.getLoginId();
        UserInfo userInfo = userInfoService.getById(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        AccountCoin accountCoin = accountCoinService.getOne(new QueryWrapper<AccountCoin>().eq("user_id", userId));
        userInfoVo.setCoinCount(accountCoin.getCoinUsable());
        return userInfoVo;
    }

    @ApiOperation(value = "查询用户信息",notes = "查询用户信息")
    @GetMapping("/{userId}")
    @JsonFieldIgnore({"password","isDeleted","updateTime"})
    public UserInfoVo getUserInfo(@PathVariable("userId")Long userId){
        UserInfo userInfo = userInfoService.getById(userId);
        if(userInfo == null){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_NOT_EXIST);
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        AccountCoin accountCoin = accountCoinService.getOne(new QueryWrapper<AccountCoin>().eq("user_id", userId));
        userInfoVo.setCoinCount(accountCoin.getCoinUsable());
        return userInfoVo;
    }


    @ApiOperation(value = "登陆不登陆均可访问",notes = "登陆不登陆均可访问")
    @GetMapping("/testAnonymous")
    public String testAnonymous(@Anonymous TokenUserModel userModel){
        if(userModel==null){
            return "未登录的展示结果";
        }
        return "登陆的展示结果，id为:"+userModel.getLoginId();
    }

    @ApiOperation(value = "测试静态注入",notes = "测试静态注入")
    @GetMapping("/testStaticInjection")
    public String testStaticInjection(){
        return accountCoinService.toString()+currentPort;
    }




    @ApiOperation(value = "用户手机号密码注册",notes = "用户手机号密码注册")
    @PostMapping("/registered")
    @RedisLock(value = LockKeyPrefixConstant.USER_BASE_ACTION_REGISTER,methodDynamicParam = "#registerInput.phoneNum")
    public LoginVo registered(@RequestBody @Valid RegisterInput registerInput)  {
        String phone = registerInput.getPhoneNum();
        String nickName = registerInput.getNickName();
        String password = registerInput.getPassword();

        int phoneNumCount = userInfoService.count(new QueryWrapper<UserInfo>().eq("phone_num", phone).last("limit 1"));
        if(phoneNumCount>0){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_REGISTER_PHONE_EXIST);
        }
        int nickNameCount = userInfoService.count(new QueryWrapper<UserInfo>().eq("nick_name", nickName).last("limit 1"));
        if(nickNameCount>0){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_REGISTER_NICKNAME_EXIST);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhoneNum(phone);
        userInfo.setPassword(password);
        userInfo.setIdentity(1);
        userInfo.setState(0);
        userInfoService.register(userInfo);
        return getLoginVo(userInfo);
    }



    private LoginVo getLoginVo(UserInfo userInfo){
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(userInfo,loginVo);
        loginVo.setToken(getToken(userInfo));
        return loginVo;
    }

    private String getToken(UserInfo userInfo){
        TokenUserModel tokenUserModel = new TokenUserModel();
        tokenUserModel.setLoginId(userInfo.getId());
        tokenUserModel.setName(userInfo.getNickName());
        String tokenUserProfileJsonString = JSONUtil.parse(tokenUserModel).toJSONString(0);
        return jwtTokenService.createJwt("jwt", tokenUserProfileJsonString);
    }



}
