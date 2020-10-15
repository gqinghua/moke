package com.xxx.service2.impl;

import com.xxx.anno.AsyncServiceHandler;
import com.xxx.entity.User;
import com.xxx.model.BaseAsyncService;
import com.xxx.service.UserService;
import com.xxx.service2.UserTwoAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Xu Haidong
 * @date 2018/8/15
 */
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
