package com.tszj.chain.sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.tszj.chain"})
public class SdkServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(SdkServerApplication.class, args);
    }
}
