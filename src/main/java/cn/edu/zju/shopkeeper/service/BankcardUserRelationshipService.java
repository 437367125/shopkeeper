package cn.edu.zju.shopkeeper.service;

import cn.edu.zju.shopkeeper.domain.req.BankcardUserRelationshipReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/21 下午3:43
 * Description 银行卡-用户关系服务接口
 */
public interface BankcardUserRelationshipService {
    /**
     * 创建银行卡-用户关联关系
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes createRelationship(BankcardUserRelationshipReq req) throws ShopkeeperException;

    /**
     * 删除银行卡-用户关联关系
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes deleteRelationship(BankcardUserRelationshipReq req) throws ShopkeeperException;

    /**
     * 获取用户添加的银行卡列表
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ListRes<BankcardVO> queryBankcardList(BankcardUserRelationshipReq req) throws ShopkeeperException;
}
