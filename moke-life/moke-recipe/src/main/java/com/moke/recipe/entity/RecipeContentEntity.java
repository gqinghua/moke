package com.moke.recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 食谱表详情
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-03 22:32:44
 */
@Data
@TableName("recipe_content")
public class RecipeContentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id",type = IdType.ID_WORKER_STR)
	private String id;
	/**
	 * 食谱表分类id
	 */
	private String recipeId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 表图片
	 */
	private String pic;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 发布者id
	 */
	private String userid;

	/**
	 * 是否是热门
	 */
	private String hot;
	/**
	 * 排序
	 */
	private String sort;
}
