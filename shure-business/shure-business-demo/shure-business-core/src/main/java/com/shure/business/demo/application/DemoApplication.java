package com.shure.business.demo.application;

import com.shure.common.web.apiversion.annotation.EnableApiVersion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Shure
 * @date 2022/1/20 10:47
 */
@SpringBootApplication
@EnableApiVersion
@EnableAsync
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
