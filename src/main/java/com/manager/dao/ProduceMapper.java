package com.manager.dao;

import com.manager.pojo.Produce;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProduceMapper {

    List<Produce> getAll(@Param("year") int year, @Param("month") int month, @Param("day") int day);

    int insertOne(Produce produce);

    int insertBatch(List<Produce> produceList);

    int updateOne(Produce produce);

    int changeProduce(Produce produce);
}
