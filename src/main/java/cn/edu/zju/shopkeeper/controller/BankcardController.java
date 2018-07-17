package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.BuyerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.BankcardReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.BankcardService;
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
 * @date 2018/7/17 下午2:35
 * Description 银行卡控制器
 */
@Api(value = "/bankcard", description = "银行卡控制器")

@RestController
@RequestMapping("/bankcard")
public class BankcardController extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BankcardController.class);

    private BankcardService bankcardService;

    @Autowired
    public BankcardController(BankcardService bankcardService) {
        this.bankcardService = bankcardService;
    }

    @ApiOperation(value = "买家查询自己的银行卡列表", response = JSONObject.class)
    @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @BuyerLoginRequired
    public JSONObject getBankcardList(@RequestHeader String token) {
        JSONObject jsonObject = new JSONObject();
        ListRes<BankcardVO> res = new ListRes<>();
        BankcardReq req = new BankcardReq();
        try {
            req.setUserId(getUser(token));
            res = bankcardService.queryBankcardList(req);
        } catch (ShopkeeperException e) {
            logger.error("BankcardController getBankcardList error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.BANKCARD_LIST, res.getResultList());
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家添加银行卡", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "bankcardNumber", value = "银行卡号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bankName", value = "银行名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "balance", value = "余额", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @BuyerLoginRequired
    public JSONObject createBankcard(@RequestHeader String token,
                                     @RequestParam String bankcardNumber,
                                     @RequestParam String bankName,
                                     @RequestParam Double balance) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        BankcardReq req = new BankcardReq();
        req.setBalance(balance);
        req.setBankName(bankName);
        try {
            req.setUserId(getUser(token));
            req.setBankcardNumber(Long.parseLong(bankcardNumber));
            res = bankcardService.createBankcard(req);
        } catch (ShopkeeperException e) {
            logger.error("BankcardController createBankcard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("BankcardController createBankcard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家删除银行卡", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "bankcardId", value = "银行卡主键", required = true, paramType = "query"),
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @BuyerLoginRequired
    public JSONObject deleteBankcard(@RequestHeader String token,
                                     @RequestParam Integer bankcardId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        BankcardReq req = new BankcardReq();
        req.setId(bankcardId);
        try {
            req.setUserId(getUser(token));
            res = bankcardService.deleteBankcard(req);
        } catch (ShopkeeperException e) {
            logger.error("BankcardController deleteBankcard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
