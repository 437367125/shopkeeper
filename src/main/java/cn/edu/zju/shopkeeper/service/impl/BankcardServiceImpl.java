package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.Bankcard;
import cn.edu.zju.shopkeeper.domain.req.BankcardReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.BankcardMapper;
import cn.edu.zju.shopkeeper.service.BankcardService;
import cn.edu.zju.shopkeeper.utils.DozerBeanUtil;
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
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createBankcard(BankcardReq req) throws ShopkeeperException {
        logger.info("invoke BankcardServiceImpl createBankcard, req:{}", req);
        //参数校验
        if (req.getUserId() == null || req.getBalance() == null ||
                req.getBankcardNumber() == null || StringUtils.isBlank(req.getBankName())) {
            logger.error("BankcardServiceImpl createBankcard missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
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
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 删除银行卡
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes deleteBankcard(BankcardReq req) throws ShopkeeperException {
        logger.info("invoke BankcardServiceImpl deleteBankcard, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("BankcardServiceImpl deleteBankcard missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
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
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 修改银行卡余额
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateBalance(BankcardReq req) throws ShopkeeperException {
        //todo 决定废弃，先放着
        return null;
    }

    /**
     * 获取银行卡列表
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<BankcardVO> queryBankcardList(BankcardReq req) throws ShopkeeperException {
        logger.info("invoke BankcardServiceImpl queryBankcardList, req:{}", req);
        //参数校验
        if (req.getUserId() == null) {
            logger.error("BankcardServiceImpl queryBankcardList missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ListRes<BankcardVO> res = new ListRes<>();
        try {
            List<Bankcard> list = bankcardMapper.queryBankcardList(req.getUserId());
            res.setResultList(DozerBeanUtil.mapList(list, BankcardVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl queryBankcardList error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 通过主键获取银行卡信息
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<BankcardVO> getBankcardById(BankcardReq req) throws ShopkeeperException {
        logger.info("invoke BankcardServiceImpl getBankcardById, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("BankcardServiceImpl getBankcardById missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<BankcardVO> res = new ObjectRes<>();
        try {
            Bankcard bankcard = bankcardMapper.getBankcardById(req.getId());
            res.setResultObj(DozerBeanUtil.map(bankcard, BankcardVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardServiceImpl getBankcardById error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }
}
