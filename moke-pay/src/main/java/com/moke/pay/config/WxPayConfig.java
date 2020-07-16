package com.moke.pay.config;

import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class WxPayConfig {
	/**
	 * 服务端回调地址
	 */
	private String notifyUrl;

	/**
	 * 订单过期时间
	 */
	private String expireTime;
}