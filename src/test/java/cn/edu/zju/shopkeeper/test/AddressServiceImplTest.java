package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.domain.Address;
import cn.edu.zju.shopkeeper.domain.req.AddressReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.service.AddressService;
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
 * @date 2018/7/15 下午2:10
 * Description 地址服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;

    public AddressServiceImplTest() {
    }

    @Test
    public void queryAddressList() {
        AddressReq req = new AddressReq();
        req.setUserId(1);
        try {
            ListRes<Address> res = addressService.queryAddressList(req);
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void createAddress() {
        AddressReq req = new AddressReq();
        req.setUserId(1);
        req.setAddressDescription("浙江大学玉泉校区");
        req.setPhoneNumber(188888889999L);
        try {
            BaseRes res = addressService.createAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void deleteAddress() {
        AddressReq req = new AddressReq();
        req.setId(1);
        try {
            BaseRes res = addressService.deleteAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateDefaultAddress() {
        AddressReq req = new AddressReq();
        req.setId(2);
        req.setUserId(1);
        try {
            BaseRes res = addressService.updateDefaultAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateAddress() {
        AddressReq req = new AddressReq();
        req.setId(1);
        req.setPhoneNumber(1999999L);
        req.setAddressDescription("浙江小学");
        try {
            BaseRes res = addressService.updateAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void getAddress() {
        AddressReq req = new AddressReq();
        req.setId(1);
        try {
            ObjectRes<Address> res = addressService.getAddress(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}