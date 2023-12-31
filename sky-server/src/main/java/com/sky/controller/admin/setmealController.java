package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@ApiOperation("套餐相关接口")
@Slf4j
public class setmealController {
    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("插入菜品")
    public void insertSetmeal(SetmealDTO setmealDTO)
    {
      setmealService.insertMeal(setmealDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据套餐id查询菜品")
    public Result<List<Dish>> queryById(@PathVariable Long cid)
    {
        setmealService.queryByCid(cid);
        return null;
    }
}
