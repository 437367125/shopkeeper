package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.LoginRequired;
import cn.edu.zju.shopkeeper.annotation.SellerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.CommodityReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityVO;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.CommodityService;
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
 * @date 2018/7/17 下午3:06
 * Description 商品控制器
 */
@Api(description = "商品控制器")
@RestController
@RequestMapping("/commodity")
public class CommodityController extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CommodityController.class);

    private CommodityService commodityService;

    @Autowired
    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @ApiOperation(value = "卖家根据商品名模糊查询商品列表（无输入则获取全部）", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "commodityName", value = "商品名", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SellerLoginRequired
    public JSONObject getCommodityList(@RequestParam(required = false) String commodityName) {
        JSONObject jsonObject = new JSONObject();
        ListRes<CommodityVO> res = new ListRes<>();
        CommodityReq req = new CommodityReq();
        req.setCommodityName(commodityName);
        try {
            res = commodityService.queryCommodityList(req);
            jsonObject.put(ShopkeeperConstant.COMMODITY_LIST, res.getResultList());
        } catch (ShopkeeperException e) {
            logger.error("CommodityController getCommodityList error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "买家或卖家根据商品主键获取商品详情", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "commodityId", value = "商品主键", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @LoginRequired
    public JSONObject getCommodityInfo(@RequestParam String commodityId) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<CommodityVO> res = new ObjectRes<>();
        CommodityReq req = new CommodityReq();
        try {
            req.setId(Integer.parseInt(commodityId));
            res = commodityService.getCommodity(req);
            jsonObject.put(ShopkeeperConstant.COMMODITY_INFO, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("CommodityController getCommodityInfo error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("CommodityController getCommodityInfo error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }


    @ApiOperation(value = "卖家添加商品", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "commodityName", value = "商品名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "description", value = "商品描述", required = true, paramType = "query"),
            @ApiImplicitParam(name = "location", value = "商品所在位置", required = true, paramType = "query"),
            @ApiImplicitParam(name = "inventory", value = "商品库存", required = true, paramType = "query"),
            @ApiImplicitParam(name = "price", value = "商品价格", required = true, paramType = "query"),
            @ApiImplicitParam(name = "picture", value = "商品图片（传地址）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "商品类型（传编号）", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @SellerLoginRequired
    public JSONObject createCommodity(@RequestHeader String token,
                                      @RequestParam String commodityName,
                                      @RequestParam String description,
                                      @RequestParam String location,
                                      @RequestParam String inventory,
                                      @RequestParam String price,
                                      @RequestParam String picture,
                                      @RequestParam String type) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<Integer> res = new ObjectRes<>();
        CommodityReq req = new CommodityReq();
        req.setCommodityName(commodityName);
        req.setDescription(description);
        req.setLocation(location);
        req.setPicture(picture);
        try {
            UserVO userVO = getUser(token);
            req.setCreater(userVO.getUsername());
            req.setModifier(userVO.getUsername());
            req.setInventory(Integer.parseInt(inventory));
            req.setPrice(Double.parseDouble(price));
            req.setType(Integer.parseInt(type));
            res = commodityService.createCommodity(req);
            jsonObject.put(ShopkeeperConstant.COMMODITY_ID, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("CommodityController createCommodity error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("CommodityController createCommodity error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家删除某个商品", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "commodityId", value = "商品主键", required = true, paramType = "query")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @SellerLoginRequired
    public JSONObject deleteCommodity(@RequestHeader String token,
                                      @RequestParam String commodityId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        CommodityReq req = new CommodityReq();
        try {
            req.setModifier(getUser(token).getUsername());
            req.setId(Integer.parseInt(commodityId));
            res = commodityService.deleteCommodity(req);
        } catch (ShopkeeperException e) {
            logger.error("CommodityController deleteCommodity error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("CommodityController deleteCommodity error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家修改商品详情", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "commodityId", value = "商品主键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "commodityName", value = "商品名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "description", value = "商品描述", required = true, paramType = "query"),
            @ApiImplicitParam(name = "location", value = "商品所在位置", required = true, paramType = "query"),
            @ApiImplicitParam(name = "inventory", value = "商品库存", required = true, paramType = "query"),
            @ApiImplicitParam(name = "price", value = "商品价格", required = true, paramType = "query"),
            @ApiImplicitParam(name = "picture", value = "商品图片（传地址）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "商品类型（传编号）", required = true, paramType = "query")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SellerLoginRequired
    public JSONObject updateCommodity(@RequestHeader String token,
                                      @RequestParam String commodityId,
                                      @RequestParam String commodityName,
                                      @RequestParam String description,
                                      @RequestParam String location,
                                      @RequestParam String inventory,
                                      @RequestParam String price,
                                      @RequestParam String picture,
                                      @RequestParam String type) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        CommodityReq req = new CommodityReq();
        req.setCommodityName(commodityName);
        req.setDescription(description);
        req.setLocation(location);
        req.setPicture(picture);
        try {
            UserVO userVO = getUser(token);
            req.setModifier(userVO.getUsername());
            req.setInventory(Integer.parseInt(inventory));
            req.setType(Integer.parseInt(type));
            req.setId(Integer.parseInt(commodityId));
            req.setPrice(Double.parseDouble(price));
            res = commodityService.updateCommodity(req);
        } catch (ShopkeeperException e) {
            logger.error("CommodityController updateCommodity error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("CommodityController updateCommodity error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
