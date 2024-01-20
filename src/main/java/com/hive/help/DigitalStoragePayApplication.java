package com.hive.help;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.hive.help")//项目主路径
@EnableCreateCacheAnnotation
@Slf4j
public class DigitalStoragePayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalStoragePayApplication.class, args);

		try {
			log.info("加载C2C配置文件");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
