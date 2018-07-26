package cn.edu.zju.shopkeeper.utils;

import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/21 下午1:57
 * Description 密码工具类
 */
public class PasswordUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    /**
     * 给密码做hash
     *
     * @param password 要hash的密码
     * @return hash结果
     * @throws ShopkeeperException
     */
    public static String passwordToHash(String password) throws ShopkeeperException {
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
            logger.error("PasswordUtil passwordToHash error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.SYSTEM_ERROR);
        }
    }
}
