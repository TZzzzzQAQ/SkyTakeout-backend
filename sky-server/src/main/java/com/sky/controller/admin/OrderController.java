package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@Api(tags = "订单管理接口")
@Slf4j
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> conditionallySearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单搜索{}", ordersPageQueryDTO);
        return Result.success(orderService.conditionallySearch(ordersPageQueryDTO));
    }

    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statisticsVOResult(){
        log.info("获取各个状态的订单数量统计");
        return Result.success(orderService.statisticsVOResult());
    }

    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.getOrderDetailById(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result<String> confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("接单：{}",ordersConfirmDTO);
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result<String> rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception{
        log.info("拒单");
        orderService.rejectOrder(ordersRejectionDTO);
        return  Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result<String> cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO){
        log.info("取消订单");
        orderService.cancelOrderByRestaurant(ordersCancelDTO);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result<String> deliveryOrder(@PathVariable Long id){
        log.info("派送订单：{}",id);
        orderService.deliveryOrder(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result<String> accomplishOrder(@PathVariable Long id){
        log.info("完成订单：{}",id);
        orderService.accomplishOrder(id);
        return Result.success();
    }
}
