package cn.edu.zju.bank.mapper;

import cn.edu.zju.bank.domain.Bankcard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:55
 * Description 银行卡mapper
 */
@Component("bankcardMapper")
public interface BankcardMapper {
    /**
     * 创建银行卡
     *
     * @param bankcard
     */
    void createBankcard(Bankcard bankcard);

    /**
     * 删除银行卡
     *
     * @param bankcard
     */
    void deleteBankcard(Bankcard bankcard);

    /**
     * 修改银行卡余额
     *
     * @param bankcard
     */
    void updateBalance(Bankcard bankcard);

    /**
     * 获取用户的银行卡列表
     *
     * @param userId
     * @return
     */
    List<Bankcard> queryBankcardList(@Param("userId") Integer userId);

    /**
     * 根据主键获取银行卡详情
     *
     * @param id
     * @return
     */
    Bankcard getBankcardById(@Param("id") Integer id);

    /**
     * 根据银行卡号获取银行卡详情
     *
     * @param bankcardNumber
     * @return
     */
    Bankcard getBankcard(@Param("bankcardNumber") Long bankcardNumber);
}
