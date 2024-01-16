package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.WorkSpaceMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WorkSpaceMapper workSpaceMapper;

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /**
         * 营业额：当日已完成订单的总金额
         * 有效订单：当日已完成订单的数量
         * 订单完成率：有效订单数 / 总订单数
         * 平均客单价：营业额 / 有效订单数
         * 新增用户：当日新增用户的数量
         */
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        Integer count = reportMapper.getOrders(map);
        Integer newUser = reportMapper.getAllUserStaticsByDate(map);

        map.put("status", Orders.COMPLETED);

        Integer completedCount = reportMapper.getOrders(map);

        Double turnover = reportMapper.getTurnoverByDate(map);
        Double unitPrice = 0.0;
        Double rate = 0.0;

        if (count != 0) {
            if (completedCount != 0) {
                rate = (double) completedCount / count;
                if (turnover != 0)
                    unitPrice = turnover / completedCount;
            }
        }
        if (turnover == null) {
            turnover = 0.0;
        }
        return BusinessDataVO.builder()
                .validOrderCount(completedCount)
                .orderCompletionRate(rate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .newUsers(newUser)
                .build();
    }

    @Override
    public OrderOverViewVO getOverviewOrders() {
        Map<String, Object> map = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        // 获取今天的开始时间（00:00:00）
        LocalDateTime begin = today.atStartOfDay();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", null);
        Integer allOrders = workSpaceMapper.getOrderStatus(map);

        map.put("status", Orders.COMPLETED);
        Integer completedOrders = workSpaceMapper.getOrderStatus(map);

        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = workSpaceMapper.getOrderStatus(map);

        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = workSpaceMapper.getOrderStatus(map);

        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = workSpaceMapper.getOrderStatus(map);
        return OrderOverViewVO.builder()
                .allOrders(allOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .deliveredOrders(deliveredOrders)
                .waitingOrders(waitingOrders)
                .build();
    }

    @Override
    public DishOverViewVO getOverviewDishes() {
        Map<String, Integer> map = new HashMap<>();
        map.put("status", StatusConstant.DISABLE);

        Integer discontinued = workSpaceMapper.getDishCount(map);
        map.put("status", StatusConstant.ENABLE);
        Integer sold = workSpaceMapper.getDishCount(map);
        return DishOverViewVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }

    @Override
    public SetmealOverViewVO getSetmealOverview() {
        Map<String, Integer> map = new HashMap<>();
        map.put("status", StatusConstant.DISABLE);

        Integer discontinued = workSpaceMapper.getSetMealCount(map);

        map.put("status", StatusConstant.ENABLE);
        Integer sold = workSpaceMapper.getSetMealCount(map);
        return SetmealOverViewVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }
}
