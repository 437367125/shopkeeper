package cn.edu.zju.bank.service.impl;

import cn.edu.zju.bank.constants.ShopkeeperConstant;
import cn.edu.zju.bank.domain.Bankcard;
import cn.edu.zju.bank.domain.req.BankcardReq;
import cn.edu.zju.bank.domain.res.BaseRes;
import cn.edu.zju.bank.domain.res.ListRes;
import cn.edu.zju.bank.domain.res.ObjectRes;
import cn.edu.zju.bank.domain.vo.BankcardVO;
import cn.edu.zju.bank.enums.ResultEnum;
import cn.edu.zju.bank.exception.BankcardException;
import cn.edu.zju.bank.mapper.BankcardMapper;
import cn.edu.zju.bank.service.BankcardService;
import cn.edu.zju.bank.utils.DozerBeanUtil;
import cn.edu.zju.bank.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 下午1:42
 * Description 银行卡服务实现类
 */
@Service
public class BankcardServiceImpl implements BankcardService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BankcardServiceImpl.class);

    private BankcardMapper bankcardMapper;

    @Autowired
    public BankcardServiceImpl(BankcardMapper bankcardMapper) {
        this.bankcardMapper = bankcardMapper;
    }

    /**
     * 创建新银行卡
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    @Override
    public BaseRes createBankcard(BankcardReq req) throws BankcardException {
        logger.info("invoke BankcardServiceImpl createBankcard, req:{}", req);
        //参数校验
        if (req.getBankcardNumber() == null || req.getPassword() == null) {
            logger.error("BankcardServiceImpl createBankcard missing param, req:{}", req);
            throw new BankcardException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        //给密码做hash
        req.setPassword(PasswordUtil.passwordToHash(req.getPassword()));
        try {
            Bankcard entity = DozerBeanUtil.map(req, Bankcard.class);
            entity.setCreateTime(date);
            entity.setModifyTime(date);
            entity.setState(ShopkeeperConstant.VALID);
            bankcardMapper.createBankcard(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl createBankcard error:{}", ExceptionUtils.getStackTrace(e));
            throw new BankcardException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 删除银行卡
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    @Override
    public BaseRes deleteBankcard(BankcardReq req) throws BankcardException {
        logger.info("invoke BankcardServiceImpl deleteBankcard, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getUserId() == null) {
            logger.error("BankcardServiceImpl deleteBankcard missing param, req:{}", req);
            throw new BankcardException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Bankcard entity = DozerBeanUtil.map(req, Bankcard.class);
            entity.setModifyTime(date);
            bankcardMapper.deleteBankcard(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl deleteBankcard error:{}", ExceptionUtils.getStackTrace(e));
            throw new BankcardException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 修改银行卡余额
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    @Override
    public BaseRes updateBalance(BankcardReq req) throws BankcardException {
        //todo 决定废弃，先放着
        return null;
    }

    /**
     * 获取银行卡列表
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    @Override
    public ListRes<BankcardVO> queryBankcardList(BankcardReq req) throws BankcardException {
        logger.info("invoke BankcardServiceImpl queryBankcardList, req:{}", req);
        //参数校验
        if (req.getUserId() == null) {
            logger.error("BankcardServiceImpl queryBankcardList missing param, req:{}", req);
            throw new BankcardException(ResultEnum.MISSING_PARAM);
        }
        ListRes<BankcardVO> res = new ListRes<>();
        try {
            List<Bankcard> list = bankcardMapper.queryBankcardList(req.getUserId());
            res.setResultList(DozerBeanUtil.mapList(list, BankcardVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl queryBankcardList error:{}", ExceptionUtils.getStackTrace(e));
            throw new BankcardException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 通过主键获取银行卡信息
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    @Override
    public ObjectRes<BankcardVO> getBankcardById(BankcardReq req) throws BankcardException {
        logger.info("invoke BankcardServiceImpl getBankcardById, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("BankcardServiceImpl getBankcardById missing param, req:{}", req);
            throw new BankcardException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<BankcardVO> res = new ObjectRes<>();
        try {
            Bankcard bankcard = bankcardMapper.getBankcardById(req.getId());
            res.setResultObj(DozerBeanUtil.map(bankcard, BankcardVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl getBankcardById error:{}", ExceptionUtils.getStackTrace(e));
            throw new BankcardException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 通过银行卡号获取银行卡信息
     *
     * @param req
     * @return
     * @throws BankcardException
     */
    @Override
    public ObjectRes<BankcardVO> getBankcardByNumber(BankcardReq req) throws BankcardException {
        logger.info("invoke BankcardServiceImpl getBankcardByNumber, req:{}", req);
        //参数校验
        if (req.getBankcardNumber() == null || StringUtils.isBlank(req.getPassword())) {
            logger.error("BankcardServiceImpl getBankcardByNumber missing param, req:{}", req);
            throw new BankcardException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<BankcardVO> res = new ObjectRes<>();
        try {
            Bankcard bankcard = bankcardMapper.getBankcard(req.getBankcardNumber());
            if (bankcard == null) {
                throw new BankcardException(ResultEnum.BANKCARD_NOT_EXIST);
            }
            if (!bankcard.getPassword().equals(PasswordUtil.passwordToHash(req.getPassword()))) {
                throw new BankcardException(ResultEnum.PASSWORD_ERROR);
            }
            bankcard.setPassword(null);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
            res.setResultObj(DozerBeanUtil.map(bankcard, BankcardVO.class));
        } catch (BankcardException e) {
            throw new BankcardException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl getBankcardByNumber error:{}", ExceptionUtils.getStackTrace(e));
            throw new BankcardException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }
}
