package com.xxx.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * @author Xu Haidong
 * @date 2018/8/2
 */
@DataObject(generateConverter = true)
public class User {

    @TableId("test_id")
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 这里故意演示注解可无
     */
    @TableLogic
    private Integer testType;

    /**
     * 测试插入填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Date testDate;

    private Long role;

    private String phone;

    public User() {
    }

    public User(JsonObject jsonObject) {
        UserConverter.fromJson(jsonObject, this);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getTestType() {
        return this.testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Long getRole() {
        return this.role;
    }

    public void setRole(Long role) {
        this.role = role;
    }


    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        UserConverter.toJson(this, json);
        return json;
    }
}
