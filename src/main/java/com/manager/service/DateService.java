package com.manager.service;

import com.manager.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    private YearsMapper yearsMapper;
    private MonthMapper monthMapper;
    private DayMapper dayMapper;
    @Autowired
    public DateService(YearsMapper yearsMapper, MonthMapper monthMapper, DayMapper dayMapper) {
        this.yearsMapper = yearsMapper;
        this.monthMapper = monthMapper;
        this.dayMapper = dayMapper;
    }


    public int[] getAllYears(){
        return yearsMapper.getAllYears();
    }

}
