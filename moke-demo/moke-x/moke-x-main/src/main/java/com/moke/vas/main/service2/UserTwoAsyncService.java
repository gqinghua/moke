package com.moke.vas.main.service2;

import com.moke.vas.main.entity.User;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


/**
 *
 * @date 2018/8/15
 */
@ProxyGen
public interface UserTwoAsyncService {

    void findUser(Long id, Handler<AsyncResult<User>> resultHandler);
}
