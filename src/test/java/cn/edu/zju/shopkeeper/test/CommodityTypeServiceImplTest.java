package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.domain.req.CommodityTypeReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityTypeVO;
import cn.edu.zju.shopkeeper.service.CommodityTypeService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午5:11
 * Description 商品类型服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityTypeServiceImplTest {
    @Autowired
    private CommodityTypeService commodityTypeService;

    @Test
    public void getCommodityType() {
        CommodityTypeReq req = new CommodityTypeReq();
        req.setId(2);
        try {
            ObjectRes<CommodityTypeVO> res = commodityTypeService.getCommodityType(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void queryCommodityTypeList() {
        try {
            ListRes<CommodityTypeVO> res = commodityTypeService.queryCommodityTypeList();
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void createCommodityType() {
        CommodityTypeReq req = new CommodityTypeReq();
        req.setCreater("wzj");
        req.setModifier("wzj");
        req.setTypeName("电脑");
//        req.setDescription();
        try {
            BaseRes res = commodityTypeService.createCommodityType(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void deleteCommodityType() {
        CommodityTypeReq req = new CommodityTypeReq();
        req.setId(2);
        req.setModifier("wzj1");
        try {
            BaseRes res = commodityTypeService.deleteCommodityType(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateCommodityType() {
        CommodityTypeReq req = new CommodityTypeReq();
        req.setId(1);
        req.setTypeName("高手的玩具");
        req.setModifier("wzj1");
        try {
            BaseRes res = commodityTypeService.updateCommodityType(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}