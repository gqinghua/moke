//
//package com.moke.pay.controller;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.jpay.weixin.api.WxPayApiConfigKit;
//import com.moke.common.model.Result;
//import com.moke.pay.config.PayConfigParmaInitRunner;
//import com.moke.pay.entity.PayGoodsOrder;
//import com.moke.pay.service.PayGoodsOrderService;
//import com.moke.pay.utils.PayConstants;
//import io.swagger.annotations.Api;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//
///**
// * 商品
// *
// * @author
// * @date 2019-05-28 23:58:27
// */
//@Controller
//@AllArgsConstructor
//@RequestMapping("/goods")
//@Api(value = "goods", tags = "商品订单管理")
//public class PayGoodsOrderController {
//
//	private final PayGoodsOrderService payGoodsOrderService;
//
//
//	/**
//	 * 商品订单
//	 *
//	 * @param goods        商品
//	 * @param modelAndView
//	 * @return R
//	 */
//	@GetMapping("/buy")
//	public ModelAndView buy(PayGoodsOrder goods, HttpServletRequest request, ModelAndView modelAndView) {
//		String ua = request.getHeader(HttpHeaders.USER_AGENT);
//		if (ua.contains(PayConstants.MICRO_MESSENGER)) {
//			String appId = WxPayApiConfigKit.getWxPayApiConfig().getAppId();
//			modelAndView.setViewName("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId +
//					"&redirect_uri=http%3a%2f%2fadmin.pig4cloud.com%2fpay%2fgoods%2fwx%3famount%3d" + goods.getAmount() +
//					"&response_type=code&scope=snsapi_base&state=" + appId);
//			return modelAndView;
//		}
//
//		modelAndView.setViewName("pay");
//		modelAndView.addAllObjects(payGoodsOrderService.buy(goods));
//		return modelAndView;
//	}
//
//	/**
//	 * oauth
//	 *
//	 * @param goods        商品信息
//	 * @param state        appid
//	 * @param code         回调code
//	 * @param modelAndView
//	 * @return
//	 * @throws
//	 */
//
//	@SneakyThrows
//	@GetMapping("/wx")
//	public ModelAndView wx(PayGoodsOrder goods, String state, String code, ModelAndView modelAndView) {
//		WxMpService wxMpService = PayConfigParmaInitRunner.mpServiceMap.get(state);
//		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
//		goods.setUserId(wxMpOAuth2AccessToken.getOpenId());
//		goods.setAmount(goods.getAmount());
//		modelAndView.setViewName("pay");
//		modelAndView.addAllObjects(payGoodsOrderService.buy(goods));
//		return modelAndView;
//	}
//
//	/**
//	 * 分页查询
//	 *
//	 * @param page          分页对象
//	 * @param payGoodsOrder 商品订单表
//	 * @return
//	 */
//	@ResponseBody
//	@GetMapping("/page")
//	public Result getPayGoodsOrderPage(Page page, PayGoodsOrder payGoodsOrder) {
//		return Result.succeed(payGoodsOrderService.page(page, Wrappers.query(payGoodsOrder)));
//	}
//
//
//	/**
//	 * 通过id查询商品订单表
//	 *
//	 * @param goodsOrderId id
//	 * @return R
//	 */
//	@ResponseBody
//	@GetMapping("/{goodsOrderId}")
//	public Result getById(@PathVariable("goodsOrderId") Integer goodsOrderId) {
//		return Result.succeed(payGoodsOrderService.getById(goodsOrderId));
//	}
//
//	/**
//	 * 新增商品订单表
//	 *
//	 * @param payGoodsOrder 商品订单表
//	 * @return R
//	 */
//	@PostMapping
//	@ResponseBody
//	@PreAuthorize("@pms.hasPermission('generator_paygoodsorder_add')")
//	public Result save(@RequestBody PayGoodsOrder payGoodsOrder) {
//		return Result.succeed(payGoodsOrderService.save(payGoodsOrder));
//	}
//
//	/**
//	 * 修改商品订单表
//	 *
//	 * @param payGoodsOrder 商品订单表
//	 * @return R
//	 */
//	@PutMapping
//	@ResponseBody
//	@PreAuthorize("@pms.hasPermission('generator_paygoodsorder_edit')")
//	public Result updateById(@RequestBody PayGoodsOrder payGoodsOrder) {
//		return Result.succeed(payGoodsOrderService.updateById(payGoodsOrder));
//	}
//
//	/**
//	 * 通过id删除商品订单表
//	 *
//	 * @param goodsOrderId id
//	 * @return R
//	 */
//	@ResponseBody
//	@DeleteMapping("/{goodsOrderId}")
//	@PreAuthorize("@pms.hasPermission('generator_paygoodsorder_del')")
//	public Result removeById(@PathVariable Integer goodsOrderId) {
//		return Result.succeed(payGoodsOrderService.removeById(goodsOrderId));
//	}
//
//}
