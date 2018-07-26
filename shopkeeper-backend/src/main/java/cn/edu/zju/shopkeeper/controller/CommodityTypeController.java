package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.annotation.SellerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.CommodityTypeReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityTypeVO;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.CommodityTypeService;
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
 * @date 2018/7/17 下午4:05
 * Description 商品类型控制器
 */
@Api(description = "商品类别控制器")
@RestController
@RequestMapping("/commodity-type")
public class CommodityTypeController extends BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CommodityTypeController.class);

    private CommodityTypeService commodityTypeService;

    @Autowired
    public CommodityTypeController(CommodityTypeService commodityTypeService) {
        this.commodityTypeService = commodityTypeService;
    }

    @ApiOperation(value = "卖家获取商品类型列表", response = JSONObject.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SellerLoginRequired
    public JSONObject getCommodityTypeList() {
        JSONObject jsonObject = new JSONObject();
        ListRes<CommodityTypeVO> res = new ListRes<>();
        try {
            res = commodityTypeService.queryCommodityTypeList();
            jsonObject.put(ShopkeeperConstant.COMMODITY_TYPE_LIST, res.getResultList());
        } catch (ShopkeeperException e) {
            logger.error("CommodityTypeController getCommodityTypeList error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家创建新的商品类型", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "typeName", value = "类型名", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @SellerLoginRequired
    public JSONObject createCommodityType(@RequestHeader String token,
                                          @RequestParam String typeName) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        CommodityTypeReq req = new CommodityTypeReq();
        req.setTypeName(typeName);
        try {
            UserVO userVO = getUser(token);
            req.setCreater(userVO.getUsername());
            req.setModifier(userVO.getUsername());
            res = commodityTypeService.createCommodityType(req);
        } catch (ShopkeeperException e) {
            logger.error("CommodityTypeController createCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家根据商品类型主键获取商品类型详情", response = JSONObject.class)
    @ApiImplicitParam(name = "commodityTypeId", value = "商品类型主键", required = true, paramType = "query")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @SellerLoginRequired
    public JSONObject getCommodityTypeInfo(@RequestParam Integer commodityTypeId) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<CommodityTypeVO> res = new ObjectRes<>();
        CommodityTypeReq req = new CommodityTypeReq();
        req.setId(commodityTypeId);
        try {
            res = commodityTypeService.getCommodityType(req);
            jsonObject.put(ShopkeeperConstant.COMMODITY_TYPE_INFO, res.getResultObj());
        } catch (ShopkeeperException e) {
            logger.error("CommodityTypeController deleteCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家删除商品类型", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "commodityTypeId", value = "商品类型主键", required = true, paramType = "query"),
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @SellerLoginRequired
    public JSONObject deleteCommodityType(@RequestHeader String token,
                                          @RequestParam Integer commodityTypeId) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        CommodityTypeReq req = new CommodityTypeReq();
        req.setId(commodityTypeId);
        try {
            req.setModifier(getUser(token).getUsername());
            res = commodityTypeService.deleteCommodityType(req);
        } catch (ShopkeeperException e) {
            logger.error("CommodityTypeController deleteCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @ApiOperation(value = "卖家修改商品类型详情", response = JSONObject.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "用户令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "commodityTypeId", value = "商品类型主键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "typeName", value = "类型名", required = true, paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @SellerLoginRequired
    public JSONObject updateCommodityType(@RequestHeader String token,
                                          @RequestParam Integer commodityTypeId,
                                          @RequestParam String typeName) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        CommodityTypeReq req = new CommodityTypeReq();
        req.setTypeName(typeName);
        req.setId(commodityTypeId);
        try {
            UserVO userVO = getUser(token);
            req.setModifier(userVO.getUsername());
            res = commodityTypeService.updateCommodityType(req);
        } catch (ShopkeeperException e) {
            logger.error("CommodityTypeController updateCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }


}
