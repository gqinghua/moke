package com.xxx.service;

import com.xxx.entity.User;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * @author Xu Haidong
 * @date 2018/8/15
 */
@ProxyGen
public interface UserAsyncService {

    void listUsers(User user, Handler<AsyncResult<List<User>>> resultHandler);
}
