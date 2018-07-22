package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.Bankcard;
import cn.edu.zju.shopkeeper.domain.BankcardUserRelationship;
import cn.edu.zju.shopkeeper.domain.req.BankcardUserRelationshipReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.vo.BankcardVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.BankcardMapper;
import cn.edu.zju.shopkeeper.mapper.BankcardUserRelationshipMapper;
import cn.edu.zju.shopkeeper.service.BankcardUserRelationshipService;
import cn.edu.zju.shopkeeper.utils.DozerBeanUtil;
import cn.edu.zju.shopkeeper.utils.PasswordUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/21 下午3:44
 * Description 银行卡-用户关系服务实现类
 */
@Service
public class BankcardUserRelationshipServiceImpl implements BankcardUserRelationshipService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BankcardUserRelationshipServiceImpl.class);
    private BankcardUserRelationshipMapper bankcardUserRelationshipMapper;
    private BankcardMapper bankcardMapper;

    @Autowired
    public BankcardUserRelationshipServiceImpl(BankcardUserRelationshipMapper bankcardUserRelationshipMapper, BankcardMapper bankcardMapper) {
        this.bankcardUserRelationshipMapper = bankcardUserRelationshipMapper;
        this.bankcardMapper = bankcardMapper;
    }

    /**
     * 创建银行卡-用户关联关系
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createRelationship(BankcardUserRelationshipReq req) throws ShopkeeperException {
        logger.info("invoke BankcardUserRelationshipServiceImpl createRelationship, req:{}", req);
        //参数校验
        if (req.getUserId() == null || req.getBankcardNumber() == null ||
                req.getPassword() == null) {
            logger.error("BankcardUserRelationshipServiceImpl createRelationship missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        try {
            //首先搜索该银行卡，然后比较密码
            Bankcard bankcardInDatabase = bankcardMapper.getBankcard(req.getBankcardNumber());
            if (bankcardInDatabase == null) {
                res.setResultCode(ResultEnum.BANKCARD_NOT_EXIST.getCode());
                res.setResultMsg(ResultEnum.BANKCARD_NOT_EXIST.getMsg());
                return res;
            } else if (!bankcardInDatabase.getPassword().equals(PasswordUtil.passwordToHash(req.getPassword()))) {
                res.setResultCode(ResultEnum.PASSWORD_ERROR.getCode());
                res.setResultMsg(ResultEnum.PASSWORD_ERROR.getMsg());
                return res;
            }
            BankcardUserRelationship entity = DozerBeanUtil.map(req, BankcardUserRelationship.class);
            entity.setBankcardId(bankcardInDatabase.getId());
            entity.setState(ShopkeeperConstant.VALID);
            bankcardUserRelationshipMapper.createRelationship(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardUserRelationshipServiceImpl createRelationship error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 删除银行卡-用户关联关系
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes deleteRelationship(BankcardUserRelationshipReq req) throws ShopkeeperException {
        logger.info("invoke BankcardUserRelationshipServiceImpl deleteRelationship, req:{}", req);
        //参数校验
        if (req.getUserId() == null || req.getId() == null) {
            logger.error("BankcardUserRelationshipServiceImpl deleteRelationship missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        try {
            BankcardUserRelationship relationshipInDatabase = bankcardUserRelationshipMapper.getRelationshipById(req.getId());
            if (relationshipInDatabase == null) {
                //该银行卡不存在
                res.setResultCode(ResultEnum.BANKCARD_NOT_EXIST.getCode());
                res.setResultMsg(ResultEnum.BANKCARD_NOT_EXIST.getMsg());
                return res;
            }
            if (!relationshipInDatabase.getUserId().equals(req.getUserId())) {
                //用户主键不对应
                res.setResultCode(ResultEnum.BANKCARD_STATUS_ERROR.getCode());
                res.setResultMsg(ResultEnum.BANKCARD_STATUS_ERROR.getMsg());
                return res;
            }
            bankcardUserRelationshipMapper.deleteRelationship(req.getId());
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardUserRelationshipServiceImpl deleteRelationship error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 获取用户添加的银行卡列表
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<BankcardVO> queryBankcardList(BankcardUserRelationshipReq req) throws ShopkeeperException {
        logger.info("invoke BankcardUserRelationshipServiceImpl queryBankcardList, req:{}", req);
        //参数校验
        if (req.getUserId() == null) {
            logger.error("BankcardUserRelationshipServiceImpl deleteRelationship missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ListRes<BankcardVO> res = new ListRes<>();
        try {
            List<BankcardUserRelationship> list = bankcardUserRelationshipMapper.queryRelationshipList(req.getUserId());
            List<BankcardVO> bankcardVOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (BankcardUserRelationship b : list) {
                    Bankcard bankcard = bankcardMapper.getBankcardById(b.getBankcardId());
                    bankcardVOS.add(DozerBeanUtil.map(bankcard, BankcardVO.class));
                }
            }
            res.setResultList(bankcardVOS);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("BankcardUserRelationshipServiceImpl queryBankcardList error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }
}
