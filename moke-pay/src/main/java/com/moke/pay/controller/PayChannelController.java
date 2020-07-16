

package com.moke.pay.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.model.Result;
import com.moke.pay.config.PayConfigParmaInitRunner;
import com.moke.pay.entity.PayChannel;
import com.moke.pay.service.PayChannelService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 渠道
 *
 * @author
 * @date 2019-05-28 23:57:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/paychannel")
@Api(value = "paychannel", tags = "paychannel管理")
public class PayChannelController {
	private final PayConfigParmaInitRunner parmaInitRunner;
	private final PayChannelService payChannelService;

	/**
	 * 分页查询
	 *
	 * @param page       分页对象
	 * @param payChannel 渠道
	 * @return
	 */
	@GetMapping("/page")
	public Result getPayChannelPage(Page page, PayChannel payChannel) {
		return Result.succeed(payChannelService.page(page, Wrappers.query(payChannel)));
	}


	/**
	 * 通过id查询渠道
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public Result getById(@PathVariable("id") Integer id) {
		return Result.succeed(payChannelService.getById(id));
	}

	/**
	 * 新增渠道
	 *
	 * @param payChannel 渠道
	 * @return R
	 */
	@PostMapping
	@PreAuthorize("@pms.hasPermission('pay_paychannel_add')")
	public Result save(@RequestBody PayChannel payChannel) {
		payChannelService.saveChannel(payChannel);
		parmaInitRunner.initPayConfig();
		return Result.succeed("新增成功");
	}

	/**
	 * 修改渠道
	 *
	 * @param payChannel 渠道
	 * @return R
	 */

	@PutMapping
	@PreAuthorize("@pms.hasPermission('pay_paychannel_edit')")
	public  Result updateById(@RequestBody PayChannel payChannel) {
		payChannelService.updateById(payChannel);
		parmaInitRunner.initPayConfig();
		return Result.succeed("修改成功");
	}

	/**
	 * 通过id删除渠道
	 *
	 * @param id id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('pay_paychannel_del')")
	public Result removeById(@PathVariable Integer id) {
		payChannelService.removeById(id);
		parmaInitRunner.initPayConfig();
		return Result.succeed("通过id删除渠道成功");
	}

}
