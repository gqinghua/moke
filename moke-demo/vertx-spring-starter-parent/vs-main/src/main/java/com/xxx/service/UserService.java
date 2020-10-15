package com.xxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.entity.User;
import io.vertx.core.json.JsonObject;


/**
 *
 * User 表数据服务层接口
 *
 */
public interface UserService extends IService<User> {

	JsonObject findUser(JsonObject param);
}