package com.yabo.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.yabo.food.feign"})
@SpringBootApplication
public class SanFranciscoFoodTruckApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanFranciscoFoodTruckApplication.class, args);
    }

}
