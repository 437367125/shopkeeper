package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.domain.req.AddressReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.AddressVO;
import cn.edu.zju.shopkeeper.service.AddressService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
            ListRes<AddressVO> res = addressService.queryAddressList(req);
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void queryAddressList2() {
        AddressReq req = new AddressReq();
        try {
            ListRes<AddressVO> res = addressService.queryAddressList(req);
            System.out.println(res.getResultList());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void createAddress() {
        AddressReq req = new AddressReq();
        req.setUserId(41);
        req.setAddressDescription("浙江大学紫金港校区");
        req.setPhoneNumber(18883389999L);
        try {
            BaseRes res = addressService.createAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void createAddress2() {
        AddressReq req = new AddressReq();
        req.setUserId(41);
        req.setAddressDescription("sadsasadsad");
        req.setPhoneNumber(18883389999L);
        try {
            BaseRes res = addressService.createAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void createAddress3() {
        AddressReq req = new AddressReq();
        req.setAddressDescription("浙江大学紫金港校区");
        req.setPhoneNumber(18883389999L);
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
        req.setUserId(41);
        req.setId(1);
        try {
            BaseRes res = addressService.deleteAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void deleteAddress2() {
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
        req.setUserId(41);
        try {
            BaseRes res = addressService.updateDefaultAddress(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateDefaultAddress2() {
        AddressReq req = new AddressReq();
        req.setId(2);
        req.setUserId(41);
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
        req.setPhoneNumber(17865788L);
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
        req.setUserId(1);
        try {
            ObjectRes<AddressVO> res = addressService.getAddress(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}