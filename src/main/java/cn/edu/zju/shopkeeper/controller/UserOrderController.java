package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.BuyerLoginRequired;
import cn.edu.zju.shopkeeper.annotation.SellerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.OrderCommodityRelationshipReq;
import cn.edu.zju.shopkeeper.domain.req.UserOrderReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserOrderVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.UserOrderService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 下午5:49
 * Description 用户订单控制器
 */
@Api(description = "用户订单控制器")
@RestController
@RequestMapping("/userorder")
public class UserOrderController extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UserOrderController.class);
    private UserOrderService userOrderService;

    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }


    @ApiOperation(value = "卖家根据订单状态获取所有订单", response = JSONObject.class)
    @ApiImplicitParam(name = "status", value = "订单状态（0待付款，1已付款，2待发货，3已发货，4已取消，5已完成，不填表示获取所有订单）", paramType = "query")
    @RequestMapping(value = "/all-list", method = RequestMethod.GET)
    @SellerLoginRequired
    public JSONObject getUserOrderListByStatus(@RequestParam(required = false) Integer status) {
        JSONObject jsonObject = new JSONObject();
        ListRes<UserOrderVO> res = new ListRes<>();
        UserOrderReq req = new UserOrderReq();
        req.setStatus(status);
        try {
            res = userOrderService.queryAllOrderListByStatus(req);
            jsonObject.put(ShopkeeperConstant.ORDER_LIST, res.getResultList());
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController getUserOrderListByStatus error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家下单", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "type", value = "订单类型（0无需配送，1需要配送）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bankcardId", value = "银行卡主键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址主键", paramType = "query"),
            @ApiImplicitParam(name = "list", value = "商品列表", required = true, paramType = "body")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @BuyerLoginRequired
    public JSONObject createOrder(@RequestHeader String token,
                                  @RequestParam Integer type,
                                  @RequestParam Integer bankcardId,
                                  @RequestParam(required = false) Integer addressId,
                                  @RequestBody List<OrderCommodityRelationshipReq> list) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserOrderReq req = new UserOrderReq();
        req.setType(type);
        req.setBankcardId(bankcardId);
        req.setAddressId(addressId);
        req.setCommodityList(list);
        try {
            req.setUserId(getUser(token).getId());
            res = userOrderService.createOrder(req);
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController createOrder error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家确认发货", response = JSONObject.class)
    @ApiImplicitParam(name = "userOrderId", value = "订单主键", required = true, paramType = "query")
    @RequestMapping(value = "/delivery", method = RequestMethod.PUT)
    @SellerLoginRequired
    public JSONObject updateDelivery(@RequestParam Integer userOrderId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserOrderReq req = new UserOrderReq();
        req.setId(userOrderId);
        try {
            res = userOrderService.updateDelivery(req);
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController updateDelivery error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家确认收货", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userOrderId", value = "订单主键", required = true, paramType = "query")
    })
    @RequestMapping(value = "/complete", method = RequestMethod.PUT)
    @BuyerLoginRequired
    public JSONObject updateComplete(@RequestHeader String token,
                                     @RequestParam Integer userOrderId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserOrderReq req = new UserOrderReq();
        req.setId(userOrderId);
        try {
            req.setUserId(getUser(token).getId());
            res = userOrderService.updateComplete(req);
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController updateComplete error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家根据订单状态获取自己的有效订单列表", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "status", value = "订单状态（0待付款，1已付款，2待发货，3已发货，4已取消，5已完成，不填表示获取所有订单）", paramType = "query")
    })
    @RequestMapping(value = "/buyer-list", method = RequestMethod.GET)
    @BuyerLoginRequired
    public JSONObject getOrderListOfBuyer(@RequestHeader String token,
                                          @RequestParam(required = false) Integer status) {
        JSONObject jsonObject = new JSONObject();
        ListRes<UserOrderVO> res = new ListRes<>();
        UserOrderReq req = new UserOrderReq();
        req.setStatus(status);
        req.setState(ShopkeeperConstant.VALID);
        try {
            req.setUserId(getUser(token).getId());
            res = userOrderService.queryAllOrderListByStatus(req);
            jsonObject.put(ShopkeeperConstant.ORDER_LIST, res.getResultList());
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController getOrderListOfBuyer error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家根据订单主键获取自己的订单详情", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userOrderId", value = "订单主键", paramType = "query")
    })
    @RequestMapping(value = "/buyer", method = RequestMethod.GET)
    @BuyerLoginRequired
    public JSONObject getOrderInfoOfBuyer(@RequestHeader String token,
                                          @RequestParam Integer userOrderId) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<UserOrderVO> res = new ObjectRes<>();
        UserOrderReq req = new UserOrderReq();
        req.setId(userOrderId);
        req.setState(ShopkeeperConstant.VALID);
        try {
            req.setUserId(getUser(token).getId());
            res = userOrderService.getUserOrderById(req);
            if (res.getResultObj() != null) {
                jsonObject.put(ShopkeeperConstant.ORDER_INFO, res.getResultObj());
            }
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController getOrderListOfBuyer error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家取消订单", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userOrderId", value = "订单主键", paramType = "query")
    })
    @RequestMapping(value = "/cancel", method = RequestMethod.PUT)
    @BuyerLoginRequired
    public JSONObject cancelOrder(@RequestHeader String token,
                                  @RequestParam Integer userOrderId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserOrderReq req = new UserOrderReq();
        req.setId(userOrderId);
        req.setStatus(ShopkeeperConstant.CANCEL);
        try {
            req.setUserId(getUser(token).getId());
            res = userOrderService.updateOrder(req);
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController cancelOrder error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家删除订单", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "userOrderId", value = "订单主键", paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @BuyerLoginRequired
    public JSONObject deleteOrder(@RequestHeader String token,
                                  @RequestParam Integer userOrderId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        UserOrderReq req = new UserOrderReq();
        req.setId(userOrderId);
        req.setState(ShopkeeperConstant.INVALID);
        try {
            req.setUserId(getUser(token).getId());
            res = userOrderService.updateOrder(req);
        } catch (ShopkeeperException e) {
            logger.error("UserOrderController deleteOrder error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
