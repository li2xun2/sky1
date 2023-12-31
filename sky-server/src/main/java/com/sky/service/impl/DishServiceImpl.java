package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DIshService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DIshService {
    @Autowired
    private DishMapper dishMapper;

    /***
     * 新建菜品
     * @param dishDTO
     */
    @Transactional
    @Override
    public void insertDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        int i = dishMapper.insertDish(dish);
        Long id = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
         if(flavors.size()>0 && flavors!=null)
         {
             flavors.forEach(dishFlavor -> {
                 dishFlavor.setDishId(id);
             });
             dishMapper.inserBatchFlavors(flavors);

         }



    }

    /***
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page dishVOPageResult = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(dishVOPageResult.getTotal(),dishVOPageResult.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            //查询菜品是否起售 起售不删
            Dish dish = dishMapper.queryById(id);
            if(dish.getStatus()== StatusConstant.ENABLE)
            {
                throw  new RuntimeException("商品起售中，无法删除");
            }
        }

        List<Long> longs = dishMapper.querySetMealDish(ids);
        if(longs!=null&& longs.size()>0)
        {
            throw  new RuntimeException("商品和套餐相关联无法删除");
        }
        for (Long id : ids) {
            dishMapper.delete(id);
            dishMapper.deleteFlavor(id);
        }

    }

    @Override
    public DishVO queryVo(Long id) {
        Dish dish = dishMapper.queryById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        List<DishFlavor> dishFlavors = dishMapper.queryByIdFlavor(id);

        dishVO.setFlavors(dishFlavors);

        return dishVO;

    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        Long id = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors.size()>0 && flavors!=null)
        {   dishMapper.deleteFlavor(id);
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishMapper.inserBatchFlavors(flavors);
        }
    }
}
