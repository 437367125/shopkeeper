package cn.edu.zju.shopkeeper.domain.res;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午11:19
 * Description 集合返回类
 */
public class ListRes<T> extends BaseRes {
    /**
     * 返回结果列表
     */
    private List<T> resultList;

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return "ListRes{" +
                "resultList=" + resultList +
                '}';
    }
}
