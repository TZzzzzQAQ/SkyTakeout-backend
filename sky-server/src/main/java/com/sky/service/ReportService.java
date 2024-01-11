package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverByStartAndEnd(LocalDate start, LocalDate end);

    UserReportVO getUserStaticsByStartAndEnd(LocalDate begin, LocalDate end);
}
