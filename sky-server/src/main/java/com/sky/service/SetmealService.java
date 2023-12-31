package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;

import java.util.List;

public interface SetmealService {
    void insertMeal(SetmealDTO setmealDTO);

    List<Dish> queryByCid(Long cid);
}
