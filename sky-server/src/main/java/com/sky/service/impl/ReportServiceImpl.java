package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnOverList, ","))
                .build();
    }

    @Override
    public UserReportVO getUserStaticsByStartAndEnd(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);

            Map<String, Object> map = new HashMap<>();
            map.put("end", LocalDateTime.of(begin, LocalTime.MAX));

            Integer totalUser = reportMapper.getAllUserStaticsByDate(map);
            totalUserList.add(totalUser);
            map.put("begin", LocalDateTime.of(begin, LocalTime.MIN));

            Integer newUser = reportMapper.getAllUserStaticsByDate(map);
            newUserList.add(newUser);
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }
}
