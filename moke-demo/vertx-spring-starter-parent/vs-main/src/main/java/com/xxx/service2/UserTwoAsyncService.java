package com.xxx.service2;

import com.xxx.entity.User;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


/**
 * @author Xu Haidong
 * @date 2018/8/15
 */
@ProxyGen
public interface UserTwoAsyncService {

    void findUser(Long id, Handler<AsyncResult<User>> resultHandler);
}
