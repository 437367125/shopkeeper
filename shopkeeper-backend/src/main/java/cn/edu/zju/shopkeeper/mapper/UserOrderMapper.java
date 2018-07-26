package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.UserOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 下午2:44
 * Description 用户订单mapper
 */
@Component("userOrderMapper")
public interface UserOrderMapper {
    /**
     * 创建新订单
     *
     * @param userOrder
     */
    void createOrder(UserOrder userOrder);

    /**
     * 根据订单主键删除订单（设置为无效）
     *
     * @param id
     */
    void deleteOrder(@Param("id") Integer id);

    /**
     * 更新发货状态
     *
     * @param userOrder
     */
    void updateDelivery(UserOrder userOrder);

    /**
     * 更新订单完成状态
     *
     * @param userOrder
     */
    void updateComplete(UserOrder userOrder);

    /**
     * 根据用户主键查询该用户的有效订单列表
     *
     * @param userId 用户主键
     * @return
     */
    List<UserOrder> queryUserOrderList(@Param("userId") Integer userId);

    /**
     * 根据订单主键获取订单详情
     *
     * @param userOrder 订单请求
     * @return
     */
    UserOrder getUserOrderById(UserOrder userOrder);

    /**
     * 根据类型获取所有订单列表（卖家适用）
     *
     * @return
     */
    List<UserOrder> queryAllOrderListByStatus(UserOrder userOrder);

    /**
     * 订单状态更新（包括发货、收货、取消、删除）
     *
     * @param userOrder
     */
    void updateOrder(UserOrder userOrder);

    /**
     * 通过日期查询订单列表
     *
     * @param userOrder
     * @return
     */
    List<UserOrder> queryUserOrderListByDate(UserOrder userOrder);
}
