package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverByStartAndEnd(LocalDate start, LocalDate end);

    UserReportVO getUserStaticsByStartAndEnd(LocalDate begin, LocalDate end);

    OrderReportVO getOrderStaticsByStartAndEnd(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10ByStartAndEnd(LocalDate begin, LocalDate end);
}
