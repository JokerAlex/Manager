package com.manager.service;

import com.manager.dao.OutputMapper;
import com.manager.pojo.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutputService {

    private OutputMapper outputMapper;

    @Autowired

    public OutputService(OutputMapper outputMapper) {
        this.outputMapper = outputMapper;
    }

    /**
     * 获取产值表
     * @param year
     * @param month
     * @return
     */
    public List<Output> getAllOutput(int year,int month){
        return outputMapper.getAll(year,month);
    }

    public boolean getOne(Output output){
        if (output == null){
            return false;
        }
        if (outputMapper.getOne(output) != null){
            return true;
        }
        return false;
    }

    /**
     * 添加产值产品
     * @param output
     * @return
     */
    public boolean insertOneOutput(Output output){
        if (output == null){
            return false;
        }
        if (outputMapper.insertOne(output) == 1){
            return true;
        }
        return false;
    }

    /**
     * 产值错误修正
     * @param output
     * @return
     */
    public boolean changeOutput(Output output){
        if (output == null){
            return false;
        }
        if (outputMapper.changeOutput(output) == 1){
            return true;
        }
        return false;
    }

    /**
     * 产值更新
     * @param output
     * @return
     */
    public boolean updateOutput(Output output){
        if (output == null){
            return false;
        }
        if (outputMapper.updateOutput(output) == 1){
            return true;
        }
        return false;
    }
}
