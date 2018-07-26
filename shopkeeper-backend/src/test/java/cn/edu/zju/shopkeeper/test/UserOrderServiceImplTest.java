package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.domain.req.OrderCommodityRelationshipReq;
import cn.edu.zju.shopkeeper.domain.req.UserOrderReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserOrderVO;
import cn.edu.zju.shopkeeper.service.UserOrderService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 下午3:18
 * Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserOrderServiceImplTest {
    @Autowired
    private UserOrderService userOrderService;

    @Test
    public void createOrder() {
        List<OrderCommodityRelationshipReq> list = new ArrayList<>();
        OrderCommodityRelationshipReq relationshipReq = new OrderCommodityRelationshipReq();
        relationshipReq.setCommodityId(1);
        relationshipReq.setCount(5);
        list.add(relationshipReq);
        relationshipReq = new OrderCommodityRelationshipReq();
        relationshipReq.setCommodityId(4);
        relationshipReq.setCount(2);
        list.add(relationshipReq);


        UserOrderReq req = new UserOrderReq();
        req.setCommodityList(list);
        req.setUserId(1);
//        req.setTotalNum(1);
//        req.setTotalPrice(500.0);
        req.setBankcardId(2);
        req.setType(1);
        req.setAddressId(4);
        try {
            BaseRes res = userOrderService.createOrder(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void deleteOrder() {
        UserOrderReq req = new UserOrderReq();
        req.setId(1);
        try {
            BaseRes res = userOrderService.deleteOrder(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateComplete() {
        UserOrderReq req = new UserOrderReq();
//        req.setId();
        req.setId(5);
        try {
            BaseRes res = userOrderService.updateComplete(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateDelivery() {
        UserOrderReq req = new UserOrderReq();
        req.setId(5);
        try {
            BaseRes res = userOrderService.updateDelivery(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void queryUserOrderList() {
        UserOrderReq req = new UserOrderReq();
        req.setUserId(1);
        try {
            ListRes<UserOrderVO> res = userOrderService.queryUserOrderList(req);
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void getUserOrderById() {
        UserOrderReq req = new UserOrderReq();
        req.setId(5);
        try {
            ObjectRes<UserOrderVO> res = userOrderService.getUserOrderById(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}