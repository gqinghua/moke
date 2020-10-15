package com.xxx.handlerfactory;

import com.xxx.anno.RouteHandler;
import com.xxx.anno.RouteMapping;
import com.xxx.anno.RouteMethod;
import com.xxx.utils.ReflectionUtil;
import com.xxx.vertx.RouterUtil;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vertx.core.http.HttpHeaders.*;

/**
 * Router 对象创建
 */
public class RouterHandlerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouterHandlerFactory.class);

    // 需要扫描注册的Router路径
    private static volatile Reflections reflections;

    // 默认api前缀
    private static final String GATEWAY_PREFIX = "/";

    private volatile String gatewayPrefix = GATEWAY_PREFIX;


    public RouterHandlerFactory(String routerScanAddress) {
        Objects.requireNonNull(routerScanAddress, "The router package address scan is empty.");
        reflections = ReflectionUtil.getReflections(routerScanAddress);
    }

    public RouterHandlerFactory(List<String> routerScanAddresses) {
        Objects.requireNonNull(routerScanAddresses, "The router package address scan is empty.");
        reflections = ReflectionUtil.getReflections(routerScanAddresses);
    }

    public RouterHandlerFactory(String routerScanAddress, String gatewayPrefix) {
        Objects.requireNonNull(routerScanAddress, "The router package address scan is empty.");
        reflections = ReflectionUtil.getReflections(routerScanAddress);
        this.gatewayPrefix = gatewayPrefix;
    }

    /**
     * 开始扫描并注册handler
     */
    public Router createRouter() {
        Router router = RouterUtil.getRouter();
        router.route().handler(ctx -> {
            LOGGER.debug("The HTTP service request address information ===>path:{}, uri:{}, method:{}",
                    ctx.request().path(), ctx.request().absoluteURI(), ctx.request().method());
            ctx.response().headers().add(CONTENT_TYPE, "application/json; charset=utf-8");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_HEADERS,
                    "X-PINGOTHER, Origin,Content-Type, Accept, X-Requested-With, Dev, Authorization, Version, Token");
            ctx.response().headers().add(ACCESS_CONTROL_MAX_AGE, "1728000");
            ctx.next();
        });
        Set<HttpMethod> method = new HashSet<HttpMethod>() {{
            add(HttpMethod.GET);
            add(HttpMethod.POST);
            add(HttpMethod.OPTIONS);
            add(HttpMethod.PUT);
            add(HttpMethod.DELETE);
            add(HttpMethod.HEAD);
        }};
        /* 添加跨域的方法 **/
        router.route().handler(CorsHandler.create("*").allowedMethods(method));
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());

        try {
            Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(RouteHandler.class);
            Comparator<Class<?>> comparator = (c1, c2) -> {
                RouteHandler routeHandler1 = c1.getAnnotation(RouteHandler.class);
                RouteHandler routeHandler2 = c2.getAnnotation(RouteHandler.class);
                return Integer.compare(routeHandler2.order(), routeHandler1.order());
            };
            List<Class<?>> sortedHandlers = handlers.stream().sorted(comparator).collect(Collectors.toList());
            for (Class<?> handler : sortedHandlers) {
                try {
                    registerNewHandler(router, handler);
                } catch (Exception e) {
                    LOGGER.error("Error register {}", handler);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Manually Register Handler Fail，Error details：" + e.getMessage());
        }
        return router;
    }

    private void registerNewHandler(Router router, Class<?> handler) throws Exception {
        String root = gatewayPrefix;
        if (!root.startsWith("/")) {
            root = "/" + root;
        }
        if (!root.endsWith("/")) {
            root = root + "/";
        }
        if (handler.isAnnotationPresent(RouteHandler.class)) {
            RouteHandler routeHandler = handler.getAnnotation(RouteHandler.class);
            root = root + routeHandler.value();
        }
        Object instance = handler.newInstance();
        Method[] methods = handler.getMethods();
        Comparator<Method> comparator = (m1, m2) -> {
            RouteMapping mapping1 = m1.getAnnotation(RouteMapping.class);
            RouteMapping mapping2 = m2.getAnnotation(RouteMapping.class);
            return Integer.compare(mapping2.order(), mapping1.order());
        };

        List<Method> methodList = Stream.of(methods).filter(
                method -> method.isAnnotationPresent(RouteMapping.class)
        ).sorted(comparator).collect(Collectors.toList());
        for (Method method : methodList) {
            if (method.isAnnotationPresent(RouteMapping.class)) {
                RouteMapping mapping = method.getAnnotation(RouteMapping.class);
                RouteMethod routeMethod = mapping.method();
                String routeUrl;
                if (mapping.value().startsWith("/:")) {
                    routeUrl = (method.getName() + mapping.value());
                } else {
                    routeUrl = (mapping.value().endsWith(method.getName()) ?
                            mapping.value() : (mapping.isCover() ? mapping.value() : mapping.value() + method.getName()));
                    if (routeUrl.startsWith("/")) {
                        routeUrl = routeUrl.substring(1);
                    }
                }
                String url;
                if (!root.endsWith("/")) {
                    url = root.concat("/" + routeUrl);
                } else {
                    url = root.concat(routeUrl);
                }
                Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) method.invoke(instance);
                LOGGER.debug("Register New Handler -> {}:{}", routeMethod, url);
                switch (routeMethod) {
                    case POST:
                        router.post(url).handler(methodHandler);
                        break;
                    case PUT:
                        router.put(url).handler(methodHandler);
                        break;
                    case DELETE:
                        router.delete(url).handler(methodHandler);
                        break;
                    case ROUTE:
                        router.route(url).handler(methodHandler); // get、post、delete、put
                        break;
                    case GET: // fall through
                    default:
                        router.get(url).handler(methodHandler);
                        break;
                }
            }
        }
    }
}
