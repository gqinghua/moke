package com.moke.recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 食谱主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-30 00:02:16
 */
@Data
@TableName("recipe")
public class RecipeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.ID_WORKER_STR)
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 
	 */
	private Date create_date;
	/**
	 * 
	 */
	private Date update_date;

}
