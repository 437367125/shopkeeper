package cn.edu.zju.shopkeeper.service;

import cn.edu.zju.shopkeeper.domain.req.AddressReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.AddressVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午10:56
 * Description 地址服务接口
 */
public interface AddressService {
    /**
     * 查询地址列表
     *
     * @param req 地址请求
     * @return 地址列表
     * @throws ShopkeeperException
     */
    ListRes<AddressVO> queryAddressList(AddressReq req) throws ShopkeeperException;

    /**
     * 创建新地址
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    BaseRes createAddress(AddressReq req) throws ShopkeeperException;

    /**
     * 删除地址（实际是设置地址为无效，数据库保留数据）
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    BaseRes deleteAddress(AddressReq req) throws ShopkeeperException;

    /**
     * 更新默认地址
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    BaseRes updateDefaultAddress(AddressReq req) throws ShopkeeperException;

    /**
     * 更新地址详情
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    BaseRes updateAddress(AddressReq req) throws ShopkeeperException;

    /**
     * 根据地址主键查询地址详情
     *
     * @param req 地址请求
     * @return 地址详情
     * @throws ShopkeeperException
     */
    ObjectRes<AddressVO> getAddress(AddressReq req) throws ShopkeeperException;
}
