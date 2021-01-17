package com.moke.vas.main.service;

import com.moke.vas.main.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.vertx.core.json.JsonObject;


/**
 *
 * User 表数据服务层接口
 *
 */
public interface UserService extends IService<User> {

	JsonObject findUser(JsonObject param);
}