package com.moke.moke.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 话题表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-08 00:07:02
 */
@Data
@TableName("life_theme")
public class LifeThemeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 话题主题
	 */
	private String theme;
	/**
	 * 是否启用
	 */
	private Integer enabled;
	/**
	 * 是否是热点
	 */
	private Integer up;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
