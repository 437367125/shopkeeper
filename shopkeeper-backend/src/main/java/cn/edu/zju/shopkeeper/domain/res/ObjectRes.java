package cn.edu.zju.shopkeeper.domain.res;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午11:20
 * Description 对象返回类
 */
public class ObjectRes<T> extends BaseRes {
    /**
     * 返回对象
     */
    private T resultObj;

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }

    @Override
    public String toString() {
        return "ObjectRes{" +
                "resultObj=" + resultObj +
                '}';
    }
}
