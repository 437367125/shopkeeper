package cn.edu.zju.bank.service;

import cn.edu.zju.bank.domain.req.BankcardReq;
import cn.edu.zju.bank.domain.res.BaseRes;
import cn.edu.zju.bank.domain.res.ListRes;
import cn.edu.zju.bank.domain.res.ObjectRes;
import cn.edu.zju.bank.domain.vo.BankcardVO;
import cn.edu.zju.bank.exception.BankcardException;

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
     * @throws BankcardException
     */
    BaseRes createBankcard(BankcardReq req) throws BankcardException;

    /**
     * 删除银行卡
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    BaseRes deleteBankcard(BankcardReq req) throws BankcardException;

    /**
     * 修改银行卡余额
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    BaseRes updateBalance(BankcardReq req) throws BankcardException;

    /**
     * 获取银行卡列表
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    ListRes<BankcardVO> queryBankcardList(BankcardReq req) throws BankcardException;

    /**
     * 通过主键获取银行卡信息
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    ObjectRes<BankcardVO> getBankcardById(BankcardReq req) throws BankcardException;

    /**
     * 通过银行卡号获取银行卡信息
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    ObjectRes<BankcardVO> getBankcardByNumber(BankcardReq req) throws BankcardException;
}
