package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.LoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.UserReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.UserService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import netscape.javascript.JSObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午10:13
 * Description 用户控制类
 */
@Api(description = "用户控制器")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
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
            @ApiImplicitParam(name = "email", value = "邮箱地址", required = true, paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "用户类型（0卖家，1买家）", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject register(@RequestParam String username,
                               @RequestParam String phoneNumber,
                               @RequestParam String email,
                               @RequestParam String nickname,
                               @RequestParam String password,
                               @RequestParam Integer type) throws ShopkeeperException {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserReq req = new UserReq();
        req.setUsername(username);
        try {
            req.setPhoneNumber(Long.parseLong(phoneNumber));
        } catch (Exception e) {
            logger.error("UserController register error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.SYSTEM_ERROR);
        }
        req.setEmail(email);
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
                            @RequestParam Integer method) throws ShopkeeperException {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<UserVO> res = new ObjectRes<>();
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
            jsonObject.put(ShopkeeperConstant.USER_INFO, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("UserController register error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "用户修改密码", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "query")
    })
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    @LoginRequired
    public JSONObject updatePassword(@RequestHeader String token,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserReq req = new UserReq();
        req.setOldPassword(oldPassword);
        req.setPassword(newPassword);
        try {
            req.setId(getUser(token).getId());
            res = userService.updatePassword(req);
        } catch (ShopkeeperException e) {
            logger.error("UserController updatePassword error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "用户修改个人信息", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "email", value = "电子邮箱", paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @LoginRequired
    public JSONObject updateUserInfo(@RequestHeader String token,
                                     @RequestParam String nickname,
                                     @RequestParam String phoneNumber,
                                     @RequestParam(required = false) String email) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<UserVO> res = new ObjectRes<>();
        UserReq req = new UserReq();
        req.setNickname(nickname);
        req.setEmail(email);
        try {
            req.setPhoneNumber(Long.parseLong(phoneNumber));
            req.setId(getUser(token).getId());
            res = userService.updateUser(req);
            jsonObject.put(ShopkeeperConstant.USER_INFO, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("UserController updateUserInfo error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("UserController updateUserInfo error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "根据用户令牌获取全部个人信息", response = JSONObject.class)
    @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @LoginRequired
    public JSONObject getUserInfo(@RequestHeader String token) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<UserVO> res = new ObjectRes<>();
        UserReq req = new UserReq();
        try {
            req.setId(getUser(token).getId());
            res = userService.getUser(req);
            jsonObject.put(ShopkeeperConstant.USER_INFO, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("UserController getUserInfo error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
