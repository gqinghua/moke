/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.moke.pay.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpay.alipay.AliPayApi;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.moke.common.model.Result;
import com.moke.pay.entity.PayNotifyRecord;
import com.moke.pay.handler.PayNotifyCallbakHandler;
import com.moke.pay.service.PayNotifyRecordService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 异步通知记录
 *
 * @author
 * @date 2019-05-28 23:57:23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/notify")
@Api(value = "notify", tags = "notify管理")
public class PayNotifyRecordController {
	private final PayNotifyRecordService payNotifyRecordService;
	private final PayNotifyCallbakHandler alipayCallback;
	private final PayNotifyCallbakHandler weChatCallback;

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param payNotifyRecord 异步通知记录
	 * @return
	 */
	@GetMapping("/page")
	public Result getPayNotifyRecordPage(Page page, PayNotifyRecord payNotifyRecord) {
		return Result.succeed(payNotifyRecordService.page(page, Wrappers.query(payNotifyRecord)));
	}


	/**
	 * 通过id查询异步通知记录
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public Result getById(@PathVariable("id") Long id) {
		return Result.succeed(payNotifyRecordService.getById(id));
	}

	/**
	 * 新增异步通知记录
	 *
	 * @param payNotifyRecord 异步通知记录
	 * @return R
	 */
	@PostMapping
	@PreAuthorize("@pms.hasPermission('pay_paynotifyrecord_add')")
	public Result save(@RequestBody PayNotifyRecord payNotifyRecord) {
		return Result.succeed(payNotifyRecordService.save(payNotifyRecord));
	}

	/**
	 * 修改异步通知记录
	 *
	 * @param payNotifyRecord 异步通知记录
	 * @return R
	 */

	@PutMapping
	@PreAuthorize("@pms.hasPermission('pay_paynotifyrecord_edit')")
	public Result updateById(@RequestBody PayNotifyRecord payNotifyRecord) {
		return Result.succeed(payNotifyRecordService.updateById(payNotifyRecord));
	}

	/**
	 * 通过id删除异步通知记录
	 *
	 * @param id id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('pay_paynotifyrecord_del')")
	public Result removeById(@PathVariable Long id) {
		return Result.succeed(payNotifyRecordService.removeById(id));
	}

	/**
	 * 支付宝渠道异步回调
	 *
	 * @param request 渠道请求
	 * @return
	 */
	@SneakyThrows
	@PostMapping("/ali/callbak")
	public void aliCallbak(HttpServletRequest request, HttpServletResponse response) {
		// 解析回调信息
		Map<String, String> params = AliPayApi.toMap(request);
		alipayCallback.handle(params);
		response.getWriter().print(alipayCallback.handle(params));
	}

	/**
	 * 微信渠道支付回调
	 *
	 * @param request
	 * @return
	 */
	@SneakyThrows
	@ResponseBody
	@PostMapping("/wx/callbak")
	public String wxCallbak(HttpServletRequest request) {
		String xmlMsg = HttpKit.readData(request);
		log.info("微信订单回调信息:{}", xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		return weChatCallback.handle(params);
	}

}
