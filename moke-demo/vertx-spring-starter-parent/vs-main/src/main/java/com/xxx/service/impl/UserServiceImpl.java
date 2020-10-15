package com.xxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.entity.User;
import com.xxx.mapper.UserMapper;
import com.xxx.model.ReplyObj;
import com.xxx.service.UserService;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * User 表数据服务层接口实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public JsonObject findUser(JsonObject param) {
        LOGGER.debug("进入服务");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (param.containsKey("name")) {
            queryWrapper.lambda().like(User::getName, param.getString("name"));
        }
        if (param.containsKey("age")) {
            queryWrapper.lambda().eq(User::getAge, param.getString("age"));
        }
        List<User> list = list(queryWrapper);
        return new JsonObject(ReplyObj.build().setData(list).toString());
    }
}