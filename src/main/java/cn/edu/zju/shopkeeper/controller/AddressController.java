package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.BuyerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.AddressReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.vo.AddressVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.AddressService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午9:30
 * Description 地址控制器
 */
@Api(value = "/address", description = "地址控制器")
@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(AddressController.class);

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @ApiOperation(value = "用户查询自己的地址列表", response = JSONObject.class)
    @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @BuyerLoginRequired
    public JSONObject getAddressList(@RequestHeader String token) {
        JSONObject jsonObject = new JSONObject();
        ListRes<AddressVO> res = new ListRes<>();
        AddressReq req = new AddressReq();
        try {
            req.setUserId(getUser(token));
            res = addressService.queryAddressList(req);
        } catch (ShopkeeperException e) {
            logger.error("AddressController getAddressList error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.ADDRESS_LIST, res.getResultList());
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家创建新地址", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "addressDescription", value = "地址详情", required = true, paramType = "query"),
            @ApiImplicitParam(name = "phoneNumber", value = "联系电话", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @BuyerLoginRequired
    public JSONObject createAddress(@RequestHeader String token,
                                    @RequestParam String addressDescription,
                                    @RequestParam String phoneNumber) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        AddressReq req = new AddressReq();
        req.setAddressDescription(addressDescription);
        //解析手机号
        try {
            req.setPhoneNumber(Long.parseLong(phoneNumber));
            req.setUserId(getUser(token));
            res = addressService.createAddress(req);
        } catch (ShopkeeperException e) {
            logger.error("AddressController createAddress error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("AddressController createAddress error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家更新某个地址的详情", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "addressId", value = "地址主键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "addressDescription", value = "地址详情", required = true, paramType = "query"),
            @ApiImplicitParam(name = "phoneNumber", value = "联系电话", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @BuyerLoginRequired
    public JSONObject updateAddress(@RequestHeader String token,
                                    @RequestParam Integer addressId,
                                    @RequestParam String addressDescription,
                                    @RequestParam String phoneNumber) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        AddressReq req = new AddressReq();
        req.setAddressDescription(addressDescription);
        req.setId(addressId);
        try {
            req.setUserId(getUser(token));
            req.setPhoneNumber(Long.parseLong(phoneNumber));
            res = addressService.updateAddress(req);
        } catch (ShopkeeperException e) {
            logger.error("AddressController updateAddress error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("AddressController updateAddress error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家删除地址", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "addressId", value = "地址主键", required = true, paramType = "query"),
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @BuyerLoginRequired
    public JSONObject deleteAddress(@RequestHeader String token,
                                    @RequestParam Integer addressId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        AddressReq req = new AddressReq();
        req.setId(addressId);
        try {
            req.setUserId(getUser(token));
            res = addressService.deleteAddress(req);
        } catch (ShopkeeperException e) {
            logger.error("AddressController deleteAddress error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家修改默认地址", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "addressId", value = "地址主键", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/default-address", method = RequestMethod.PUT)
    @BuyerLoginRequired
    public JSONObject updateDefaultAddress(@RequestHeader String token,
                                           @RequestParam Integer addressId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        AddressReq req = new AddressReq();
        req.setId(addressId);
        try {
            req.setUserId(getUser(token));
            res = addressService.updateDefaultAddress(req);
        } catch (ShopkeeperException e) {
            logger.error("AddressController updateDefaultAddress error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
