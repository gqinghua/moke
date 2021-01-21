package com.moke.gateway.service.impl;

import com.moke.gateway.service.SafeRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 安全规则业务实现类
 *
 * @author pangu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SafeRuleServiceImpl implements SafeRuleService {
	@Override
	public Mono<Void> filterBlackList(ServerWebExchange exchange) {
		return null;
	}

//	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//	@Autowired
//	private IRuleCacheService ruleCacheService;
//
//	@Override
//	public Mono<Void> filterBlackList(ServerWebExchange exchange) {
//		Stopwatch stopwatch = Stopwatch.createStarted();
//		ServerHttpRequest request = exchange.getRequest();
//		ServerHttpResponse response = exchange.getResponse();
//		try {
//			URI originUri = getOriginRequestUri(exchange);
//			String requestIp = RequestHolder.getServerHttpRequestIpAddress(request);
//			String requestMethod = request.getMethodValue();
//			AtomicBoolean forbid = new AtomicBoolean(false);
//			// 从缓存中获取黑名单信息
//			Set<Object> blackLists = ruleCacheService.getBlackList(requestIp);
//			blackLists.addAll(ruleCacheService.getBlackList());
//			// 检查是否在黑名单中
//			checkBlackLists(forbid, blackLists, originUri, requestMethod);
//			log.debug("黑名单检查完成 - {}", stopwatch.stop());
//			if (forbid.get()) {
//				log.info("属于黑名单地址 - {}", originUri.getPath());
//				return ResponseUtil.webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE,
//						HttpStatus.NOT_ACCEPTABLE, Result.data(HttpStatus.NOT_ACCEPTABLE.value(), "", "已列入黑名单，访问受限"));
//			}
//		} catch (Exception e) {
//			log.error("黑名单检查异常: {} - {}", e.getMessage(), stopwatch.stop());
//		}
//		return null;
//	}
//
//	/**
//	 * 获取网关请求URI
//	 *
//	 * @param exchange ServerWebExchange
//	 * @return URI
//	 */
//	private URI getOriginRequestUri(ServerWebExchange exchange) {
//		return exchange.getRequest().getURI();
//	}
//
//	/**
//	 * 检查是否满足黑名单的条件
//	 *
//	 * @param forbid        是否黑名单判断
//	 * @param blackLists    黑名列表
//	 * @param uri           资源
//	 * @param requestMethod 请求方法
//	 */
//	private void checkBlackLists(AtomicBoolean forbid, Set<Object> blackLists, URI uri, String requestMethod) {
//		for (Object bl : blackLists) {
//			BlackList blackList = JSONObject.parseObject(bl.toString(), BlackList.class);
//			if (antPathMatcher.match(blackList.getRequestUri(), uri.getPath()) && RuleConstant.BLACKLIST_OPEN.equals(blackList.getStatus())) {
//				if (RuleConstant.ALL.equalsIgnoreCase(blackList.getRequestMethod())
//						|| StringUtils.equalsIgnoreCase(requestMethod, blackList.getRequestMethod())) {
//					if (StringUtil.isNotBlank(blackList.getStartTime()) && StringUtil.isNotBlank(blackList.getEndTime())) {
//						if (DateUtil.between(DateUtil.parseLocalTime(blackList.getStartTime(), DateUtil.DATETIME_FORMATTER),
//								DateUtil.parseLocalTime(blackList.getEndTime(), DateUtil.DATETIME_FORMATTER))) {
//							forbid.set(Boolean.TRUE);
//						}
//					} else {
//						forbid.set(Boolean.TRUE);
//					}
//				}
//			}
//			if (forbid.get()) {
//				break;
//			}
//		}
//	}
}
