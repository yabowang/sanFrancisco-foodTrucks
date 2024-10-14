package com.yabo.food.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "feignYbtAccount", url = "${food.host}")
public interface FeignFoodTruck {

    @GetMapping("/")
    String getData();
}
