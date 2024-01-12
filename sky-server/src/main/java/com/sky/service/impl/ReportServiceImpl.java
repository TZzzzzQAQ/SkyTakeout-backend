package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public TurnoverReportVO getTurnoverByStartAndEnd(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = getDateList(start, end);
        List<Double> turnOverList = new ArrayList<>();
        dateList.add(start);
        dateList.forEach(localDate -> {
            Map<String, Object> map = new HashMap<>();
            map.put("begin", LocalDateTime.of(localDate, LocalTime.MIN));
            map.put("end", LocalDateTime.of(localDate, LocalTime.MAX));
            map.put("status", Orders.COMPLETED);

            Double turnoverByDate = reportMapper.getTurnoverByDate(map);
            turnoverByDate = (turnoverByDate == null ? 0.0 : turnoverByDate);
            turnOverList.add(turnoverByDate);
        });
        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnOverList, ","))
                .build();
    }

    @Override
    public UserReportVO getUserStaticsByStartAndEnd(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getDateList(begin, end);
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        dateList.add(begin);
        dateList.forEach(localDate -> {
            Map<String, Object> map = new HashMap<>();
            map.put("end", LocalDateTime.of(localDate, LocalTime.MAX));

            Integer totalUser = reportMapper.getAllUserStaticsByDate(map);
            totalUserList.add(totalUser);
            map.put("begin", LocalDateTime.of(localDate, LocalTime.MIN));

            Integer newUser = reportMapper.getAllUserStaticsByDate(map);
            newUserList.add(newUser);
        });
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    @Override
    public OrderReportVO getOrderStaticsByStartAndEnd(LocalDate begin, LocalDate end) {
        Map<String, Object> dbmap = new HashMap<>();
        List<LocalDate> localDateList = getDateList(begin, end);
        List<Integer> allOrdersList = new ArrayList<>();
        List<Integer> allValidOrdersList = new ArrayList<>();
        localDateList.forEach(localDate -> {
            dbmap.put("begin", LocalDateTime.of(localDate, LocalTime.MIN));
            dbmap.put("end", LocalDateTime.of(localDate, LocalTime.MAX));
            dbmap.put("status", null);
            allOrdersList.add(reportMapper.getOrders(dbmap));

            dbmap.put("status", Orders.COMPLETED);
            allValidOrdersList.add(reportMapper.getOrders(dbmap));
        });

        Integer allOrdersCount = allOrdersList.stream().reduce(Integer::sum).get();
        Integer allValidOrdersCount = allValidOrdersList.stream().reduce(Integer::sum).get();

        Double orderCompletionRate;
        if (allValidOrdersCount == 0 || allOrdersCount == 0) {
            orderCompletionRate = 0.0;
        }
        orderCompletionRate = (0.0 + allValidOrdersCount) / allOrdersCount;
        return OrderReportVO.builder()
                .dateList(StringUtils.join(localDateList, ","))
                .totalOrderCount(allOrdersCount)
                .validOrderCount(allValidOrdersCount)
                .orderCountList(StringUtils.join(allOrdersList, ","))
                .validOrderCountList(StringUtils.join(allValidOrdersList, ","))
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10ByStartAndEnd(LocalDate begin, LocalDate end) {
        List<GoodsSalesDTO> salesTop10List = reportMapper.getSalesTop10(
                LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        List<String> nameList = salesTop10List.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = salesTop10List.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
    }

    private List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }
}
