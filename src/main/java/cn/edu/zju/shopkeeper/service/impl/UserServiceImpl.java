package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.User;
import cn.edu.zju.shopkeeper.domain.req.UserReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.UserMapper;
import cn.edu.zju.shopkeeper.service.UserService;
import cn.edu.zju.shopkeeper.utils.DozerBeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午9:43
 * Description 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 创建新用户
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createUser(UserReq req) throws ShopkeeperException {
        logger.info("invoke UserServiceImpl createUser, req:{}", req);
        //参数校验
        if (req.getPhoneNumber() == null || req.getType() == null ||
                StringUtils.isBlank(req.getUsername()) || StringUtils.isBlank(req.getNickname()) ||
                StringUtils.isBlank(req.getPassword())) {
            logger.error("UserServiceImpl createUser missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        //校验用户名
        if (!checkUsername(req.getUsername())) {
            throw new ShopkeeperException(ResultEnum.USERNAME_EXIST);
        }
        //校验手机号
        if (!checkPhoneNumber(req.getPhoneNumber())) {
            throw new ShopkeeperException(ResultEnum.PHONE_NUMBER_EXIST);
        }
        //给密码做hash
        req.setPassword(passwordToHash(req.getPassword()));
        try {
            User entity = DozerBeanUtil.map(req, User.class);
            entity.setCreateTime(date);
            entity.setModifyTime(date);
            entity.setState(ShopkeeperConstant.VALID);
            userMapper.createUser(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserServiceImpl createUser error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 修改用户信息（不包括密码）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<UserVO> updateUser(UserReq req) throws ShopkeeperException {
        logger.info("invoke UserServiceImpl updateUser, req:{}", req);
        //参数校验
        if (req.getPhoneNumber() == null || StringUtils.isBlank(req.getNickname()) ||
                req.getId() == null) {
            logger.error("UserServiceImpl updateUser missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<UserVO> res = new ObjectRes<>();
        Date date = new Date();
        try {
            User entity = DozerBeanUtil.map(req, User.class);
            entity.setModifyTime(date);
            userMapper.updateUser(entity);
            res.setResultObj(DozerBeanUtil.map(userMapper.getUserById(req.getId()), UserVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserServiceImpl updateUser error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 修改密码
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updatePassword(UserReq req) throws ShopkeeperException {
        logger.info("invoke UserServiceImpl updatePassword, req:{}", req);
        //参数校验
        if (req.getId() == null || StringUtils.isBlank(req.getPassword()) ||
                StringUtils.isBlank(req.getOldPassword())) {
            logger.error("UserServiceImpl updatePassword missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        req.setPassword(passwordToHash(req.getPassword()));
        try {
            User userInDatabase = userMapper.getUserById(req.getId());
            //先判断原密码是否正确
            if (!userInDatabase.getPassword().equals(passwordToHash(req.getOldPassword()))) {
                res.setResultCode(ResultEnum.OLD_PASSWORD_ERROR.getCode());
                res.setResultMsg(ResultEnum.OLD_PASSWORD_ERROR.getMsg());
                return res;
            }
            User entity = DozerBeanUtil.map(req, User.class);
            entity.setModifyTime(date);
            userMapper.updatePassword(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserServiceImpl updatePassword error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 获取用户信息
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<UserVO> getUser(UserReq req) throws ShopkeeperException {
        logger.info("invoke UserServiceImpl getUser, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("UserServiceImpl updatePassword missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<UserVO> res = new ObjectRes<>();
        try {
            User user = userMapper.getUserById(req.getId());
            res.setResultObj(DozerBeanUtil.map(user, UserVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserServiceImpl getUser error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 密码校验
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<UserVO> comparePassword(UserReq req) throws ShopkeeperException {
        logger.info("invoke UserServiceImpl comparePassword, req:{}", req);
        //参数校验
        if (req.getLoginMethod() == null || StringUtils.isBlank(req.getPassword()) ||
                (StringUtils.isBlank(req.getUsername()) && ShopkeeperConstant.USERNAME_LOGIN.equals(req.getLoginMethod())) ||
                (req.getPhoneNumber() == null && ShopkeeperConstant.PHONE_LOGIN.equals(req.getLoginMethod()))) {
            logger.error("UserServiceImpl comparePassword missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<UserVO> res = new ObjectRes<>();
        User user;
        //获取用户信息
        if (ShopkeeperConstant.USERNAME_LOGIN.equals(req.getLoginMethod())) {
            user = userMapper.getUserByUsername(req.getUsername());
        } else {
            user = userMapper.getUserByPhone(req.getPhoneNumber());
        }
        //用户不存在则直接抛异常
        if (user == null) {
            throw new ShopkeeperException(ResultEnum.USER_NOT_EXIST);
        }
        //密码校验
        if (user.getPassword().equals(passwordToHash(req.getPassword()))) {
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
            //校验成功后要生成token
            try {
                String token = JWT.create()
                        // 将 user id 保存到 token 里面
                        .withAudience(user.getId().toString())
                        // 以 password 作为 token 的密钥
                        .sign(Algorithm.HMAC256(user.getPassword()));
                res.setResultObj(DozerBeanUtil.map(user, UserVO.class));
                res.getResultObj().setToken(token);
            } catch (Exception e) {
                logger.error("UserServiceImpl comparePassword error:{}", ExceptionUtils.getStackTrace(e));
                throw new ShopkeeperException(ResultEnum.SYSTEM_ERROR);
            }
        } else {
            throw new ShopkeeperException(ResultEnum.PASSWORD_ERROR);
        }
        return res;
    }

    /**
     * 验证联系电话唯一性
     *
     * @param phoneNumber
     * @return
     * @throws ShopkeeperException
     */
    private boolean checkPhoneNumber(Long phoneNumber) throws ShopkeeperException {
        User user;
        try {
            user = userMapper.getUserByPhone(phoneNumber);
        } catch (Exception e) {
            logger.error("UserServiceImpl checkPhoneNumber error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return user == null;
    }

    /**
     * 验证用户名唯一性
     *
     * @param username
     * @return
     * @throws ShopkeeperException
     */
    private boolean checkUsername(String username) throws ShopkeeperException {
        User user;
        try {
            user = userMapper.getUserByUsername(username);
        } catch (Exception e) {
            logger.error("UserServiceImpl checkUsername error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return user == null;
    }

    /**
     * 验证电子邮箱唯一性
     *
     * @param email
     * @return
     * @throws ShopkeeperException
     */
    private boolean checkEmail(String email) throws ShopkeeperException {
        User user;
        try {
            user = userMapper.getUserByEmail(email);
        } catch (Exception e) {
            logger.error("UserServiceImpl checkEmail error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return user == null;
    }

    /**
     * 给密码做hash
     *
     * @param password 要hash的密码
     * @return hash结果
     * @throws ShopkeeperException
     */
    private String passwordToHash(String password) throws ShopkeeperException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] src = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            // 字节数组转16进制字符串
            for (byte aSrc : src) {
                String s = Integer.toHexString(aSrc & 0xFF);
                if (s.length() < 2) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("UserServiceImpl passwordToHash error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.SYSTEM_ERROR);
        }
    }
}
