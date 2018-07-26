package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午9:33
 * Description 用户mapper
 */
@Component("userMapper")
public interface UserMapper {
    /**
     * 创建新用户
     *
     * @param user
     */
    void createUser(User user);

    /**
     * 更新用户信息（不包括密码）
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 修改密码
     *
     * @param user
     */
    void updatePassword(User user);

    /**
     * 根据用户主键获取用户详情
     *
     * @param id 用户主键
     * @return 用户信息
     */
    User getUserById(@Param("id") Integer id);

    /**
     * 根据手机号获取用户详情
     *
     * @param phoneNumber
     * @return
     */
    User getUserByPhone(@Param("phoneNumber") Long phoneNumber);

    /**
     * 根据用户名获取用户详情
     *
     * @param username 用户名
     * @return
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 根据邮箱地址获取用户详情
     *
     * @param email
     * @return
     */
    User getUserByEmail(@Param("email") String email);
}
