package com.moke.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity - 基类
 * @return
 * @author tzq
 * @date 2020.03.27
 */

public abstract class BaseEntity<ID extends Serializable> implements Serializable {

//    /**
//     * ID
//     */
//
//    private ID id;
//
//    /**
//     * 创建日期
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    private LocalDateTime createDate;
//
//    /**
//     * 修改日期
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    private LocalDateTime modifyDate;
//
//    /**
//     * 获取ID
//     *
//     * @return ID
//     */
//    public ID getId() {
//        return id;
//    }
//
//    /**
//     * 设置ID
//     *
//     * @param id ID
//     */
//    public void setId(ID id) {
//        this.id = id;
//    }
//
//    /**
//     * 获取创建日期
//     *
//     * @return 创建日期
//     */
//    public LocalDateTime getCreateDate() {
//        return createDate;
//    }
//
//    /**
//     * 设置创建日期
//     *
//     * @param createDate 创建日期
//     */
//    public void setCreateDate(LocalDateTime createDate) {
//        this.createDate = createDate;
//    }
//
//    /**
//     * 获取修改日期
//     *
//     * @return 修改日期
//     */
//    public LocalDateTime getModifyDate() {
//        return modifyDate;
//    }
//
//    /**
//     * 设置修改日期
//     *
//     * @param modifyDate 修改日期
//     */
//    public void setModifyDate(LocalDateTime modifyDate) {
//        this.modifyDate = modifyDate;
//    }
//
//    /**
//     * 重写toString方法
//     *
//     * @return 字符串
//     */
//    @Override
//    public String toString() {
//        return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
//    }
//
//    /**
//     * 重写equals方法
//     *
//     * @param obj 对象
//     * @return 是否相等
//     */
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (this == obj) {
//            return true;
//        }
//        if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
//            return false;
//        }
//        BaseEntity<?> other = (BaseEntity<?>) obj;
//        return getId() != null && getId().equals(other.getId());
//    }
//
//    /**
//     * 重写hashCode方法
//     *
//     * @return HashCode
//     */
//    @Override
//    public int hashCode() {
//        int hashCode = 17;
//        hashCode += getId() != null ? getId().hashCode() * 31 : 0;
//        return hashCode;
//    }

}
