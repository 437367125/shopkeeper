package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午10:57
 * Description 地址mapper
 */
@Component("addressMapper")
public interface AddressMapper {
    /**
     * 根据用户主键查询该用户有效的地址列表
     *
     * @param userId 用户主键
     * @return 有效地址列表
     */
    List<Address> queryAddressList(@Param("userId") Integer userId);

    /**
     * 创建新地址
     *
     * @param address 地址内容
     */
    void createAddress(Address address);

    /**
     * 根据地址主键删除地址（实际是设置地址为无效）
     *
     * @param address 地址内容（包括主键和时间）
     */
    void deleteAddress(Address address);

    /**
     * 清除默认地址
     *
     * @param address 地址内容（包括用户主键和修改时间）
     */
    void clearDefaultAddress(Address address);

    /**
     * 根据地址主键设置默认地址
     *
     * @param address 地址内容（包括主键和修改时间）
     */
    void setDefaultAddress(Address address);

    /**
     * 根据地址主键更新地址详情
     *
     * @param address 地址内容
     */
    void updateAddress(Address address);

    /**
     * 根据地址主键查询单条地址详情
     *
     * @param address 地址主键
     * @return 地址详情
     */
    Address getAddress(Address address);

    /**
     * 获取默认地址
     *
     * @param userId
     * @return
     */
    Address getDefaultAddress(@Param("userId") Integer userId);
}
