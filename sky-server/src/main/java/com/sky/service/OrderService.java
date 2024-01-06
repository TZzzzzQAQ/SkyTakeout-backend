package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult getAllHistoryOrders(Integer page, Integer pageSize, Integer status);

    OrderVO getOrderDetailById(Long id);

    void cancelOrder(Long id);

    void orderAgain(Long id);

    PageResult conditionallySearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statisticsVOResult();

    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    void cancelOrderByRestaurant(OrdersCancelDTO ordersCancelDTO);

    void deliveryOrder(Long id);

    void accomplishOrder(Long id);
}
