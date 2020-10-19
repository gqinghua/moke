package com.moke.NacosConsumer.entity;

import java.io.Serializable;
import java.util.Date;


public class SysOperLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    private Long id;

    /** 操作模块 */
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private Integer businessType;

    /** 请求方法 */
    private String method;

    /** 错误消息 */
    private String errorMsg;

    private Integer status;

    /** 操作时间 */
    private Date operTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Override
    public String toString() {
        return "SysOperLog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", businessType=" + businessType +
                ", method='" + method + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", status=" + status +
                ", operTime=" + operTime +
                '}';
    }
}