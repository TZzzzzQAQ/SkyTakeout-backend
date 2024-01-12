package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    Double getTurnoverByDate(Map<String, Object> start);

    Integer getAllUserStaticsByDate(Map<String, Object> map);

    Integer getOrders(Map<String, Object> dbmap);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
