package com.manager.service;

import com.manager.dao.ProduceMapper;
import com.manager.pojo.Produce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduceService {

    private ProduceMapper produceMapper;

    @Autowired

    public ProduceService(ProduceMapper produceMapper) {
        this.produceMapper = produceMapper;
    }

    /**
     * 获取当天进度
     * @param year
     * @param month
     * @param day
     * @return
     */
    public List<Produce> getAllProduce(int year,int month,int day){
        return produceMapper.getAll(year,month,day);
    }

    /**
     * 新增产品
     * @param produce
     * @return
     */
    public boolean insertOne(Produce produce){
        if (produce == null){
            return false;
        }
        if (produceMapper.insertOne(produce) == 1){
            return true;
        }
        return false;
    }

    /**
     * 导入产品
     * @param produceList
     * @return
     */
    public boolean insertBatchProduce(List<Produce> produceList){
        if (produceList == null || produceList.size() == 0){
            return false;
        }
        int successNum = produceMapper.insertBatch(produceList);
        if (successNum == produceList.size()){
            return true;
        }
        return false;
    }

    /**
     * 产品进度更新
     * @param produce
     * @return
     */
    public boolean updateProduce(Produce produce){
        if (produce == null){
            return false;
        }
        if (produceMapper.updateOne(produce) == 1){
            return true;
        }
        return false;
    }

    /**
     * 产品进度错误修正
     * @param produce
     * @return
     */
    public boolean changeProduce(Produce produce){
        if (produce == null){
            return false;
        }
        if (produceMapper.changeProduce(produce) == 1){
            return true;
        }
        return false;
    }
}
