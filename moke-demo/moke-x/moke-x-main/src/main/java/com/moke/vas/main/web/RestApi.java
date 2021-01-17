package com.moke.vas.main.web;

import com.moke.vas.main.service.UserAsyncService;
import com.moke.vas.main.service2.UserTwoAsyncService;
import com.moke.vas.core.anno.RouteHandler;
import com.moke.vas.core.anno.RouteMapping;
import com.moke.vas.core.anno.RouteMethod;
import com.moke.vas.core.model.ReplyObj;
import com.moke.vas.core.utils.AsyncServiceUtil;
import com.moke.vas.core.utils.HttpUtil;
import com.moke.vas.core.utils.ParamUtil;
import com.moke.vas.core.vertx.VertxUtil;
import com.moke.vas.main.entity.User;
import io.vertx.core.Handler;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Set;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * this is controller
 */
@RouteHandler("restapp")
public class RestApi {

    /**
     * this can be used to consumer, if it is possible
     * however, this is from event bus aspect, i think
     * Address can be defined by rpc rules for invoking, not package names
     *
     *     vertx.eventBus().send("database-service-address", message, options, res2 -> {
     */
    private UserAsyncService userAsyncService  = AsyncServiceUtil.getAsyncServiceInstance(UserAsyncService.class);

    private UserTwoAsyncService userTwoAsyncService = AsyncServiceUtil.getAsyncServiceInstance(UserTwoAsyncService.class);

    /**
     * 演示过滤器
     *
     * @return
     */
    @RouteMapping(value = "/*", method = RouteMethod.ROUTE, order = 2)
    public Handler<RoutingContext> appFilter() {
        return ctx -> {
            System.err.println("i am app filter2！");
            ctx.next();
        };
    }

    @RouteMapping(value = "/*", method = RouteMethod.ROUTE, order = 3)
    public Handler<RoutingContext> appFilter2() {
        return ctx -> {
            System.err.println("i am app filter3！");
            ctx.next();
        };
    }

    /**
     * 演示路径参数
     *
     * @return
     */
    @RouteMapping(value = "/test/:id", method = RouteMethod.GET)
    public Handler<RoutingContext> myTest() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequestParams(ctx);
            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ReplyObj.build().setMsg("Hello，欢迎使用测试地址.....").setData(param.encode()));
        };
    }

    /**
     * 演示服务调用
     *
     * @return
     */
    @RouteMapping(value = "/listUsers", method = RouteMethod.GET)
    public Handler<RoutingContext> listUsers() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequestParams(ctx);
            if (param.containsKey("age")) {
                param.put("age", Integer.valueOf(param.getString("age")));
            }
            User user = new User(param);
            userAsyncService.listUsers(user, ar -> {
                if (ar.succeeded()) {
                    List<User> userList = ar.result();
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ReplyObj.build().setData(userList));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                            ReplyObj.build().setData(ar.cause().getMessage()).setCode(HTTP_INTERNAL_ERROR));
                }
            });
        };
    }

    @RouteMapping(value = "/findUserById", method = RouteMethod.GET)
    public Handler<RoutingContext> findUserById() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequestParams(ctx);
            if (!param.containsKey("id")) {
                HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                        ReplyObj.build().setMsg("缺少id参数").setCode(HTTP_INTERNAL_ERROR));
                return;
            }
            userTwoAsyncService.findUser(Long.valueOf(param.getString("id")), ar -> {
                if (ar.succeeded()) {
                    User user = ar.result();
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ReplyObj.build().setData(user));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                            ReplyObj.build().setData(ar.cause().getMessage()).setCode(HTTP_INTERNAL_ERROR));
                }
            });
        };
    }

    /**
     * 演示文件上传
     *
     * @return
     */
    @RouteMapping(value = "/upload", method = RouteMethod.POST)
    public Handler<RoutingContext> upload() {
        return ctx -> {
            Set<FileUpload> uploads = ctx.fileUploads();
            FileSystem fs = VertxUtil.getVertxInstance().fileSystem();
            uploads.forEach(fileUpload -> {
                String path = "D:/vertxupload/" + fileUpload.fileName();
                fs.copy(fileUpload.uploadedFileName(), path, ar -> {
                    if (ar.succeeded()) {
                        fs.deleteBlocking(fileUpload.uploadedFileName());
                    }
                });
            });
            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ReplyObj.build().setData("OK"));
        };
    }

}
