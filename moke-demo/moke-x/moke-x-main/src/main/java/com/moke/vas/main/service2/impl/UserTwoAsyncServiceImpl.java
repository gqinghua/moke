package com.moke.vas.main.service2.impl;

import com.moke.vas.core.anno.AsyncServiceHandler;
import com.moke.vas.core.model.BaseAsyncService;
import com.moke.vas.main.entity.User;
import com.moke.vas.main.service.UserService;
import com.moke.vas.main.service2.UserTwoAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@AsyncServiceHandler
@Component
public class UserTwoAsyncServiceImpl implements UserTwoAsyncService, BaseAsyncService {

    @Autowired
    private UserService userService;

    @Override
    public void findUser(Long id, Handler<AsyncResult<User>> resultHandler) {
        try {
            User user = userService.getById(id);
            Future.succeededFuture(user).setHandler(resultHandler);
        } catch (Exception e) {
            e.printStackTrace();
            resultHandler.handle(Future.failedFuture(e));
        }
    }
}
