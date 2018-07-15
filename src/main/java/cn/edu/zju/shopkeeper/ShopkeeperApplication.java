package cn.edu.zju.shopkeeper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.zju.shopkeeper.mapper")
public class ShopkeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopkeeperApplication.class, args);
	}
}
