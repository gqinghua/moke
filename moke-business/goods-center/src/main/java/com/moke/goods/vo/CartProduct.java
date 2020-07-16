package com.moke.goods.vo;


import com.moke.common.entity.pms.PmsProduct;
import com.moke.common.entity.pms.PmsProductAttribute;
import com.moke.common.entity.pms.PmsSkuStock;

import java.util.List;

/**
 * 购物车中选择规格的商品信息
 * https://github.com/shenzhuan/mallplus on 2018/8/2.
 */
public class CartProduct extends PmsProduct {
    private List<PmsProductAttribute> productAttributeList;
    private List<PmsSkuStock> skuStockList;

    public List<PmsProductAttribute> getProductAttributeList() {
        return productAttributeList;
    }

    public void setProductAttributeList(List<PmsProductAttribute> productAttributeList) {
        this.productAttributeList = productAttributeList;
    }

    public List<PmsSkuStock> getSkuStockList() {
        return skuStockList;
    }

    public void setSkuStockList(List<PmsSkuStock> skuStockList) {
        this.skuStockList = skuStockList;
    }
}
