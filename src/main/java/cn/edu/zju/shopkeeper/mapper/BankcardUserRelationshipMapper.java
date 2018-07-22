package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.BankcardUserRelationship;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/21 下午3:29
 * Description 银行卡-用户关系mapper
 */
@Component("bankcardUserRelationshipMapper")
public interface BankcardUserRelationshipMapper {

    /**
     * 创建银行卡-用户关联关系
     *
     * @param bankcardUserRelationship
     */
    void createRelationship(BankcardUserRelationship bankcardUserRelationship);

    /**
     * 删除银行卡-用户关联关系
     *
     * @param id
     */
    void deleteRelationship(@Param("id") Integer id);

    /**
     * 根据用户主键查询该用户绑定的银行卡列表
     *
     * @param userId
     * @return
     */
    List<BankcardUserRelationship> queryRelationshipList(@Param("userId") Integer userId);

    /**
     * 根据主键获取关系
     *
     * @param id
     * @return
     */
    BankcardUserRelationship getRelationshipById(@Param("id") Integer id);
}
