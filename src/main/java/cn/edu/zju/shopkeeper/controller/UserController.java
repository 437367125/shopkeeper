package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.UserReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.UserService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午10:13
 * Description 用户控制类
 */
@Api(description = "用户控制器")
@RestController
@RequestMapping("")
public class UserController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户注册", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "用户类型（0卖家，1买家）", required = true, paramType = "query")
    })
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public JSONObject register(@RequestParam String username,
                               @RequestParam String phoneNumber,
                               @RequestParam String nickname,
                               @RequestParam String password,
                               @RequestParam Integer type) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserReq req = new UserReq();
        req.setUsername(username);
        req.setPhoneNumber(Long.parseLong(phoneNumber));
        req.setNickname(nickname);
        req.setPassword(password);
        req.setType(type);
        try {
            res = userService.createUser(req);
        } catch (ShopkeeperException e) {
            logger.error("UserController register error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "用户登录，登录成功后会返回token，之后每次请求要在请求头里加上token", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "account", value = "用户账户（可以使用户名或手机号）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "method", value = "登录方式（0为用户名登录，1为手机号登录）", required = true, paramType = "query")
    })
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public JSONObject login(@RequestParam String account,
                            @RequestParam String password,
                            @RequestParam Integer method) throws ShopkeeperException{
        JSONObject jsonObject = new JSONObject();
        ObjectRes<String> res = new ObjectRes<>();
        UserReq req = new UserReq();
        req.setLoginMethod(method);
        //验证登录方式
        if (ShopkeeperConstant.USERNAME_LOGIN.equals(method)) {
            req.setUsername(account);
        } else if (ShopkeeperConstant.PHONE_LOGIN.equals(method)) {
            try {
                req.setPhoneNumber(Long.parseLong(account));
            } catch (Exception e) {
                logger.error("UserController register error:{}", ExceptionUtils.getStackTrace(e));
                throw new ShopkeeperException(ResultEnum.SYSTEM_ERROR);
            }
        } else {
            jsonObject.put(ShopkeeperConstant.RESULT_CODE, ResultEnum.LOGIN_METHOD_ERROR.getCode());
            jsonObject.put(ShopkeeperConstant.RESULT_MSG, ResultEnum.LOGIN_METHOD_ERROR.getMsg());
            return jsonObject;
        }
        req.setPassword(password);
        try {
            res = userService.comparePassword(req);
            //设置token
            jsonObject.put(ShopkeeperConstant.TOKEN, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("UserController register error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
