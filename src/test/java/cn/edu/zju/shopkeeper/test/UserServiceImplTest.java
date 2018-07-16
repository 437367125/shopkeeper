package cn.edu.zju.shopkeeper.test;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.req.UserReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.service.UserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午10:44
 * Description 用户服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        UserReq req = new UserReq();
        req.setUsername("mwb");
        req.setPhoneNumber(199999999L);
        req.setNickname("高手");
        req.setType(ShopkeeperConstant.SELLER);
        req.setPassword("123");
        try {
            BaseRes res = userService.createUser(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updateUser() {
        UserReq req = new UserReq();
        req.setPhoneNumber(199999999L);
        req.setNickname("咸鱼");
        req.setId(1);
        req.setEmail("aaa@zju.edu.cn");
        try {
            BaseRes res = userService.updateUser(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void updatePassword() {
        UserReq req = new UserReq();
        req.setId(1);
        req.setPassword("111");
        try {
            BaseRes res = userService.updatePassword(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void getUser() {
        UserReq req = new UserReq();
        req.setId(1);
        try {
            ObjectRes<UserVO> res = userService.getUser(req);
            System.out.println(res.getResultObj());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void comparePassword() {
        UserReq req = new UserReq();
        req.setUsername("wzj");
        req.setLoginMethod(ShopkeeperConstant.USERNAME_LOGIN);
        req.setPassword("111");
        try {
            BaseRes res = userService.comparePassword(req);
            System.out.println(res.getResultMsg());
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}