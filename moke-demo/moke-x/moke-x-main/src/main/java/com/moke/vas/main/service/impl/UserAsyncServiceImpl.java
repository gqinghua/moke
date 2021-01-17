package com.moke.vas.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.moke.vas.core.anno.AsyncServiceHandler;
import com.moke.vas.core.model.BaseAsyncService;
import com.moke.vas.main.entity.User;
import com.moke.vas.main.service.UserAsyncService;
import com.moke.vas.main.service.UserService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
