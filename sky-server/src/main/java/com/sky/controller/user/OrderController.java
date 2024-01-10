package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Client订单接口")
@Slf4j
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return com.sky.result.Result<com.sky.vo.OrderSubmitVO>
     * @author TZzzQAQ
     * @create 2024/1/10
     **/

    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单:{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 用户支付订单，没有微信官方授权，目前无法实现支付功能
     *
     * @param ordersPaymentDTO
     * @return com.sky.result.Result<com.sky.vo.OrderPaymentVO>
     * @author TZzzQAQ
     * @create 2024/1/10
     **/

    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
//        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
//        log.info("生成预支付交易单：{}", orderPaymentVO);
//        return Result.success(orderPaymentVO);
        return Result.success();
    }

    /**
     * 查询用户历史的所有订单
     *
     * @param page
     * @param pageSize
     * @param status
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author TZzzQAQ
     * @create 2024/1/10
     **/

    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> getAllHistoryOrders(Integer page, Integer pageSize, Integer status) {
        log.info("历史订单查询：{}+{}+{}", page, pageSize, status);
        PageResult pageResult = orderService.getAllHistoryOrders(page, pageSize, status);
        return Result.success(pageResult);
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> getOrderDetailById(@PathVariable Long id) {
        log.info("查询订单详情：{}", id);
        return Result.success(orderService.getOrderDetailById(id));
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result<String> cancelOrder(@PathVariable Long id) {
        log.info("订单{}申请取消", id);
        orderService.cancelOrder(id);
        return Result.success();
    }

    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result<String> orderAgain(@PathVariable Long id) {
        orderService.orderAgain(id);
        return Result.success();
    }

    @GetMapping("/reminder/{id}")
    @ApiOperation("用户催单")
    public Result<String> reminderOrders(@PathVariable Long id) {
        orderService.reminderOrders(id);
        return Result.success();
    }
}
