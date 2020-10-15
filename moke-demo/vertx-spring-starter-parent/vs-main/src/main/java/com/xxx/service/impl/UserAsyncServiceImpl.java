package com.xxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.anno.AsyncServiceHandler;
import com.xxx.entity.User;
import com.xxx.model.BaseAsyncService;
import com.xxx.service.UserAsyncService;
import com.xxx.service.UserService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Xu Haidong
 * @date 2018/8/15
 */
@Component
@AsyncServiceHandler
public class UserAsyncServiceImpl implements UserAsyncService, BaseAsyncService {

    @Autowired
    private UserService userService;

    @Override
    public void listUsers(User user, Handler<AsyncResult<List<User>>> resultHandler) {
        try {
            List<User> userList = userService.list(new QueryWrapper<>(user));
            Future.succeededFuture(userList).setHandler(resultHandler);
        } catch (Exception e) {
            resultHandler.handle(Future.failedFuture(e));
        }
    }
}
