package com.imooc.mall.dao;

import com.imooc.mall.pojo.Category;
import com.imooc.mall.pojo.CategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    @Select("select * from mall_category where id in (#{ids})")
    List<Category> selectByExampleKeepOrder(@Param("ids") List<Integer> ids);

    @Select("select * from mall_category where id = #{id}")
    Category selectOneKeepOrder(@Param("id") Integer ids);
}