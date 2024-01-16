package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkSpaceService;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
    @Autowired
    private WorkSpaceService workSpaceService;

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
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
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

    @Override
    public void getReport(HttpServletResponse response) {
        LocalDate end = LocalDate.now().minusDays(1);
        LocalDate begin = end.minusDays(30);

        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        BusinessDataVO businessData = workSpaceService.getBusinessData(beginTime, endTime);

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/temp.xlsx");

        try {
            XSSFWorkbook excel = new XSSFWorkbook(resourceAsStream);
            XSSFSheet sheet = excel.getSheet("Sheet1");
            sheet.getRow(1).getCell(1).setCellValue("时间：" + begin + "至" + end);

            XSSFRow row = sheet.getRow(3);
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());

            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());
            for (int i = 0; i < 31; i++) {
                LocalDate date = begin.plusDays(i);
                BusinessDataVO businessData1 = workSpaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData1.getTurnover());
                row.getCell(3).setCellValue(businessData1.getValidOrderCount());
                row.getCell(4).setCellValue(businessData1.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData1.getUnitPrice());
                row.getCell(6).setCellValue(businessData1.getNewUsers());
            }

            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);
            outputStream.close();
            excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
