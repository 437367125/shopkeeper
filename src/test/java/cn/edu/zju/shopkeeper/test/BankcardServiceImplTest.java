package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.domain.req.BankcardReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.service.BankcardService;
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
 * @date 2018/7/16 下午1:58
 * Description 银行卡服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BankcardServiceImplTest {
    @Autowired
    private BankcardService bankcardService;

    @Test
    public void createBankcard() {
        BankcardReq req = new BankcardReq();
        req.setUserId(1);
        req.setBankcardNumber(99999999999L);
        req.setBankName("工商银行");
        req.setBalance(10000.00);
        try {
            BaseRes res = bankcardService.createBankcard(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void deleteBankcard() {
        BankcardReq req = new BankcardReq();
        req.setId(1);
        try {
            BaseRes res = bankcardService.deleteBankcard(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateBalance() {
    }

    @Test
    public void queryBankcardList() {
        BankcardReq req = new BankcardReq();
        req.setUserId(1);
        try {
            ListRes<BankcardVO> res = bankcardService.queryBankcardList(req);
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void getBankcardById() {
        BankcardReq req = new BankcardReq();
        req.setId(1);
        try {
            ObjectRes<BankcardVO> res = bankcardService.getBankcardById(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}