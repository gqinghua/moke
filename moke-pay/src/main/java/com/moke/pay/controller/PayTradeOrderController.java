
package com.moke.pay.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.model.Result;
import com.moke.pay.entity.PayTradeOrder;
import com.moke.pay.service.PayTradeOrderService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 支付
 *
 * @author
 * @date 2019-05-28 23:58:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/order" )
@Api(value = "order", tags = "订单")
public class PayTradeOrderController {

    private final PayTradeOrderService payTradeOrderService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param payTradeOrder 支付
     * @return
     */
    @GetMapping("/page" )
    public Result getPayTradeOrderPage(Page page, PayTradeOrder payTradeOrder) {
        return Result.succeed(payTradeOrderService.page(page, Wrappers.query(payTradeOrder)));
    }


    /**
     * 通过id查询支付
     * @param orderId id
     * @return R
     */
    @GetMapping("/{orderId}" )
    public Result getById(@PathVariable("orderId" ) String orderId) {
        return Result.succeed(payTradeOrderService.getById(orderId));
    }

    /**
     * 新增支付
     * @param payTradeOrder 支付
     * @return R
     */

    @PostMapping
    @PreAuthorize("@pms.hasPermission('pay_paytradeorder_add')" )
    public Result save(@RequestBody PayTradeOrder payTradeOrder) {
        return Result.succeed(payTradeOrderService.save(payTradeOrder));
    }

    /**
     * 修改支付
     * @param payTradeOrder 支付
     * @return R
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('pay_paytradeorder_edit')" )
    public Result updateById(@RequestBody PayTradeOrder payTradeOrder) {
        return Result.succeed(payTradeOrderService.updateById(payTradeOrder));
    }

    /**
     * 通过id删除支付
     * @param orderId id
     * @return R
     */
    @DeleteMapping("/{orderId}" )
    @PreAuthorize("@pms.hasPermission('pay_paytradeorder_del')" )
    public Result removeById(@PathVariable String orderId) {
        return Result.succeed(payTradeOrderService.removeById(orderId));
    }

}
