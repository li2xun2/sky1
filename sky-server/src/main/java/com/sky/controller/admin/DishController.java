package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DIshService;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
@ApiOperation(value = "菜品相关接口")
public class DishController {
    @Autowired
    private DIshService dishService;
    @PostMapping("/dish")
    @ApiOperation(value = "插入菜品")
    public Result insertDish(@RequestBody DishDTO dishDTO)
    {
        dishService.insertDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/dish/page")
    @ApiOperation(value = "分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO)
    {
       PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping("/dish")
    @ApiOperation("批量删除菜品")
    public Result deletePageBatch(@RequestParam List<Long> ids)
    {
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/dish/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> queryByIdDish(@PathVariable Long id)
    {
        DishVO dishVO = dishService.queryVo(id);
        return Result.success(dishVO);
    }

    @PutMapping("/dish")
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody  DishDTO dishDTO)
    {

        dishService.update(dishDTO);
        return Result.success();
    }
}
