package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /***
     * 插入菜品
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    int insertDish(Dish dish);

    void inserBatchFlavors(List<DishFlavor> flavors);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id=#{id}")
    Dish queryById(Long id);

    List<Long> querySetMealDish(List<Long> ids);

    @Delete("delete FROM dish WHERE id = #{ids}")
    void delete(Long ids);

    @Delete("delete from dish_flavor where dish_id =#{id}")
    void deleteFlavor(Long id);

    List<DishFlavor> queryByIdFlavor(Long id);

    void update(Dish dish);
}
