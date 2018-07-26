package cn.edu.zju.shopkeeper.service;

import cn.edu.zju.shopkeeper.domain.Bankcard;
import cn.edu.zju.shopkeeper.domain.req.BankcardReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 下午1:41
 * Description 银行卡服务接口
 */
public interface BankcardService {
    /**
     * 创建新银行卡
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes createBankcard(BankcardReq req) throws ShopkeeperException;

    /**
     * 删除银行卡
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes deleteBankcard(BankcardReq req) throws ShopkeeperException;

    /**
     * 修改银行卡余额
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes updateBalance(BankcardReq req) throws ShopkeeperException;

    /**
     * 获取银行卡列表
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ListRes<BankcardVO> queryBankcardList(BankcardReq req) throws ShopkeeperException;

    /**
     * 通过主键获取银行卡信息
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<BankcardVO> getBankcardById(BankcardReq req) throws ShopkeeperException;
}
