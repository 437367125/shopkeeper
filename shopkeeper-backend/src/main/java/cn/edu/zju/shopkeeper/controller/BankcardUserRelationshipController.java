package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.BuyerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.BankcardUserRelationshipReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.BankcardUserRelationshipService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/22 下午3:45
 * Description 银行卡-用户关系控制器
 */
@Api(value = "/bankcard-user", description = "银行卡-用户关系控制器")
@RestController
@RequestMapping("/bankcard-user")
public class BankcardUserRelationshipController extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BankcardUserRelationshipController.class);

    private BankcardUserRelationshipService bankcardUserRelationshipService;

    public BankcardUserRelationshipController(BankcardUserRelationshipService bankcardUserRelationshipService) {
        this.bankcardUserRelationshipService = bankcardUserRelationshipService;
    }

    @ApiOperation(value = "买家绑定银行卡", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "bankcardNumber", value = "银行卡号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bankcardPassword", value = "银行卡密码", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @BuyerLoginRequired
    public JSONObject createRelationship(@RequestHeader String token,
                                         @RequestParam String bankcardNumber,
                                         @RequestParam String bankcardPassword) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        BankcardUserRelationshipReq req = new BankcardUserRelationshipReq();
        req.setPassword(bankcardPassword);
        try {
            req.setUserId(getUser(token).getId());
            req.setBankcardNumber(Long.parseLong(bankcardNumber));
            res = bankcardUserRelationshipService.createRelationship(req);
        } catch (ShopkeeperException e) {
            logger.error("BankcardUserRelationshipController createRelationship error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("BankcardUserRelationshipController createRelationship error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家解除银行卡绑定", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "bankcardUserRelationshipId", value = "银行卡-用户关系表的主键", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @BuyerLoginRequired
    public JSONObject deleteRelationship(@RequestHeader String token,
                                         @RequestParam Integer bankcardUserRelationshipId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        BankcardUserRelationshipReq req = new BankcardUserRelationshipReq();
        req.setId(bankcardUserRelationshipId);
        try {
            req.setUserId(getUser(token).getId());
            res = bankcardUserRelationshipService.deleteRelationship(req);
        } catch (ShopkeeperException e) {
            logger.error("BankcardUserRelationshipController deleteRelationship error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家获取绑定的银行卡列表", response = JSONObject.class)
    @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @BuyerLoginRequired
    public JSONObject getBankcardList(@RequestHeader String token) {
        JSONObject jsonObject = new JSONObject();
        ListRes<BankcardVO> res = new ListRes<>();
        BankcardUserRelationshipReq req = new BankcardUserRelationshipReq();
        try {
            req.setUserId(getUser(token).getId());
            res = bankcardUserRelationshipService.queryBankcardList(req);
            jsonObject.put(ShopkeeperConstant.BANKCARD_LIST, res.getResultList());
        } catch (ShopkeeperException e) {
            logger.error("BankcardUserRelationshipController getBankcardList error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
