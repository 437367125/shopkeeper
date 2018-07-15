package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.Address;
import cn.edu.zju.shopkeeper.domain.req.AddressReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.AddressMapper;
import cn.edu.zju.shopkeeper.service.AddressService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午10:56
 * Description 地址服务实现类
 */
@Service
public class AddressServiceImpl implements AddressService {
    /**
     * dozer
     */
    DozerBeanMapper mapper = new DozerBeanMapper();
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    private AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    /**
     * 查询地址列表
     *
     * @param req 地址请求
     * @return 地址列表
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<Address> queryAddressList(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl queryAddressList, req:{}", req);
        //参数校验
        if (req.getUserId() == null) {
            logger.error("AddressServiceImpl queryAddressList missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ListRes<Address> res = new ListRes<>();
        try {
            List<Address> list = addressMapper.queryAddressList(req.getUserId());
            res.setResultList(list);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl queryAddressList error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 创建新地址
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl createAddress, req:{}", req);
        //参数校验
        if (req.getUserId() == null || StringUtils.isBlank(req.getAddressDescription()) ||
                req.getPhoneNumber() == null) {
            logger.error("AddressServiceImpl createAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Address entity = mapper.map(req, Address.class);
            entity.setState(ShopkeeperConstant.VALID);
            entity.setCreateTime(date);
            entity.setModifyTime(date);
            List<Address> curAddressList = addressMapper.queryAddressList(req.getUserId());
            if (CollectionUtils.isEmpty(curAddressList)) {
                entity.setType(ShopkeeperConstant.DEFAULT);
            } else {
                entity.setType(ShopkeeperConstant.NOT_DEFAULT);
            }
            addressMapper.createAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl createAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 删除地址（实际是设置地址为无效，数据库保留数据）
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes deleteAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl deleteAddress, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("AddressServiceImpl deleteAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Address entity = mapper.map(req, Address.class);
            entity.setModifyTime(date);
            addressMapper.deleteAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl deleteAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 更新默认地址
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseRes updateDefaultAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl updateDefaultAddress, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getUserId() == null) {
            logger.error("AddressServiceImpl updateDefaultAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            //首先清除默认地址
            Address entity = new Address();
            entity.setUserId(req.getUserId());
            entity.setModifyTime(date);
            addressMapper.clearDefaultAddress(entity);
            //接着设置新的默认地址
            entity = mapper.map(req, Address.class);
            entity.setModifyTime(date);
            addressMapper.setDefaultAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl updateDefaultAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 更新地址详情
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl updateAddress, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getPhoneNumber() == null ||
                StringUtils.isBlank(req.getAddressDescription())) {
            logger.error("AddressServiceImpl updateAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Address entity = mapper.map(req, Address.class);
            entity.setModifyTime(date);
            addressMapper.updateAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl updateAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 根据地址主键查询地址详情
     *
     * @param req 地址请求
     * @return 地址详情
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<Address> getAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl getAddress, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("AddressServiceImpl getAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<Address> res = new ObjectRes<>();
        try {
            Address address = addressMapper.getAddress(req.getId());
            res.setResultObj(address);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl getAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }
}
