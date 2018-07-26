package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.domain.req.CommodityReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityVO;
import cn.edu.zju.shopkeeper.service.CommodityService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午10:27
 * Description 商品服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityServiceImplTest {
    @Autowired
    private CommodityService commodityService;

    @Test
    public void getCommodity() {
        CommodityReq req = new CommodityReq();
        req.setId(1);
        try {
            ObjectRes<CommodityVO> res = commodityService.getCommodity(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void queryCommodityListByType() {
        CommodityReq req = new CommodityReq();
        req.setType(1);
        req.setId(1);
        try {
            ListRes<CommodityVO> res = commodityService.queryCommodityListByType(req);
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void createCommodity() {
        CommodityReq req = new CommodityReq();
        req.setCommodityName("饼干");
        req.setDescription("阿斯顿撒多");
        req.setCreater("wzj");
        req.setModifier("wzj");
        req.setInventory(1);
        req.setLocation("A-01");
        req.setPicture("/asdasd");
        req.setType(1);
        req.setPrice(123.331);
        try {
            BaseRes res = commodityService.createCommodity(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void deleteCommodity() {
        CommodityReq req = new CommodityReq();
        req.setId(1);
        req.setModifier("Wzj");
        try {
            BaseRes res = commodityService.deleteCommodity(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateCommodity() {
        CommodityReq req = new CommodityReq();
        req.setId(1);
        req.setCommodityName("饼干");
        req.setDescription("阿斯顿撒多");
        req.setCreater("wzj");
        req.setModifier("wzj");
        req.setInventory(1);
        req.setLocation("A-01");
        req.setPicture("/asdasd");
        req.setType(1);
        req.setPrice(123.331);
        try {
            BaseRes res = commodityService.updateCommodity(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}