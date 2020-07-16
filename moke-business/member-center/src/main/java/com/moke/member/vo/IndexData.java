package com.moke.member.vo;


import com.moke.common.vo.TArticleDO;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18 0018.
 */
@Data
public class IndexData {
    private List<TArticleDO> module_list;

    private int cat_goods_cols;
    private List<TArticleDO> block_list;



}
