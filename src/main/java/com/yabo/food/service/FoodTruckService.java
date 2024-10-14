package com.yabo.food.service;

import com.alibaba.fastjson.JSONObject;
import com.yabo.food.feign.FeignFoodTruck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodTruckService {

    @Autowired
    private FeignFoodTruck feignFoodTruck;

    private static List<Map> DATA_MAP;

    public List<Map> getData() {
        if (DATA_MAP == null) {
            this.remoteData();
            if (DATA_MAP == null) {
                return new ArrayList<>();
            }
        }
        return DATA_MAP;
    }

    public List<Map> search(String food) {
        if (food == null || food.isBlank()) {
            return this.getData();
        }

        if (DATA_MAP == null) {
            this.remoteData();
            if (DATA_MAP == null) {
                return new ArrayList<>();
            }
        }

        return DATA_MAP.stream().filter(item -> {
            String optionalText = String.valueOf(item.get("optionaltext"));
            return optionalText.contains(food);
        }).collect(Collectors.toList());
    }

    private void remoteData() {
        if (DATA_MAP != null) {
            return;
        }
        synchronized (FoodTruckService.class) {
            if (DATA_MAP == null) {
                String data;
                try {
                    data = feignFoodTruck.getData();
                } catch (Exception e) {
                    log.error("remote data error", e);
                    return;
                }

                if (data == null) {
                    return;
                }

                DATA_MAP = JSONObject.parseArray(data, Map.class);
            }
        }
    }
}
