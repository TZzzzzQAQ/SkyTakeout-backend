package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ReportMapper {

    Double getTurnoverByDate(Map<String, Object> start);
}
