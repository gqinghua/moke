package com.moke.gateway.feign;

import com.moke.common.constant.ServiceNameConstants;
import com.moke.common.model.SysPermission;
import com.moke.gateway.feign.fallback.MenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
	/**
	 * 角色菜单列表
	 * @param roleCodes
	 */
	@GetMapping(value = "/menus/{roleCodes}")
	List<SysPermission> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);
}
