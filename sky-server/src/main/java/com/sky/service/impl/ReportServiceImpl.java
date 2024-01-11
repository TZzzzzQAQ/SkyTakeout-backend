package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public TurnoverReportVO getTurnoverByStartAndEnd(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        List<Double> turnOverList = new ArrayList<>();
        dateList.add(start);
        while (!start.equals(end)) {
            start = start.plusDays(1);
            dateList.add(start);


            Map<String, Object> map = new HashMap<>();
            map.put("begin", LocalDateTime.of(start, LocalTime.MIN));
            map.put("end", LocalDateTime.of(start, LocalTime.MAX));
            map.put("status", Orders.COMPLETED);
            Double turnoverByDate = reportMapper.getTurnoverByDate(map);
            turnoverByDate = (turnoverByDate == null ? 0.0 : turnoverByDate);
            turnOverList.add(turnoverByDate);

        }
        return TurnoverReportVO
                .builder()
                .dateList(JSON.toJSONString(dateList))
                .turnoverList(JSON.toJSONString(turnOverList))
                .build();
    }
}
