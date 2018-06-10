package com.manager.dao;

import com.manager.pojo.Output;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutputMapper {

    List<Output> getAll(@Param("year") int year, @Param("month") int month);

    Output getOne(Output output);

    int insertOne(Output output);

    int changeOutput(Output output);

    int updateOutput(Output output);
}
