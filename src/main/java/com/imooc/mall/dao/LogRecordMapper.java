package com.imooc.mall.dao;

import com.imooc.mall.pojo.LogRecord;
import com.imooc.mall.pojo.LogRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogRecordMapper {
    long countByExample(LogRecordExample example);

    int deleteByExample(LogRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogRecord record);

    int insertSelective(LogRecord record);

    List<LogRecord> selectByExample(LogRecordExample example);

    LogRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogRecord record, @Param("example") LogRecordExample example);

    int updateByExample(@Param("record") LogRecord record, @Param("example") LogRecordExample example);

    int updateByPrimaryKeySelective(LogRecord record);

    int updateByPrimaryKey(LogRecord record);
}