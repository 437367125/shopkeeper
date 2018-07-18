package cn.edu.zju.shopkeeper.service;

import cn.edu.zju.shopkeeper.domain.req.UserReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午9:43
 * Description 用户服务接口
 */
public interface UserService {
    /**
     * 创建新用户
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes createUser(UserReq req) throws ShopkeeperException;

    /**
     * 修改用户信息（不包括密码）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<UserVO> updateUser(UserReq req) throws ShopkeeperException;

    /**
     * 修改密码
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes updatePassword(UserReq req) throws ShopkeeperException;

    /**
     * 获取用户信息
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<UserVO> getUser(UserReq req) throws ShopkeeperException;

    /**
     * 密码校验
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<UserVO> comparePassword(UserReq req) throws ShopkeeperException;
}
