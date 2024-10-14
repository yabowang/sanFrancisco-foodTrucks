package com.yabo.food.controller;

import com.yabo.food.service.FoodTruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "food/truck/")
public class FoodTruckController {

    @Autowired
    private FoodTruckService foodTruckService;

    @GetMapping("list")
    public List<Map> list() {
        return foodTruckService.getData();
    }

    @GetMapping("search")
    public List<Map> search(String food) {
        return foodTruckService.search(food);
    }
}
