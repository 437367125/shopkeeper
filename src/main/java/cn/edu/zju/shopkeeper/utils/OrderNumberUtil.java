package cn.edu.zju.shopkeeper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 下午2:35
 * Description 订单号生成工具
 */
public class OrderNumberUtil {
    public static Long getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();
        // 获取5位随机数
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;

        return Long.parseLong(rannum + str);
    }
}
