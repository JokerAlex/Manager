package com.manager.service;

import com.manager.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    private YearsMapper yearsMapper;
    @Autowired
    public DateService(YearsMapper yearsMapper) {
        this.yearsMapper = yearsMapper;
    }


    public int[] getAllYears(){
        return yearsMapper.getAllYears();
    }

}
