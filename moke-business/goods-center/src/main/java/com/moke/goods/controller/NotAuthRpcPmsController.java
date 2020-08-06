package com.moke.goods.controller;


import com.moke.common.entity.pms.*;
import com.moke.common.redis.template.RedisRepository;
import com.moke.common.redis.template.RedisUtil;
import com.moke.goods.mapper.PmsProductCategoryMapper;
import com.moke.goods.mapper.PmsProductMapper;
import com.moke.goods.mapper.PmsSkuStockMapper;
import com.moke.goods.service.*;
import com.moke.goods.vo.PromotionProduct;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:
 * @Date:
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "NotAuthRpcPmsController", description = "商品关系管理")
public class NotAuthRpcPmsController {

    @Autowired
    private IPmsProductConsultService pmsProductConsultService;
    @Resource
    private IPmsProductService pmsProductService;
    @Resource
    private IPmsProductAttributeCategoryService productAttributeCategoryService;
    @Resource
    private IPmsProductCategoryService productCategoryService;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IPmsBrandService IPmsBrandService;
    @Resource
    private RedisRepository redisRepository;
    @Resource
    private  PmsProductCategoryMapper categoryMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private IPmsFavoriteService favoriteService;
    @Resource
    private PmsSkuStockMapper skuStockMapper;


    @GetMapping(value = "/notAuth/rpc/PmsProduct/id", params = "id")
    PmsProduct selectById(Long id){
        return  productMapper.selectById(id);
    }

    @GetMapping(value = "/notAuth/rpc/PmsSkuStock/id", params = "id")
    PmsSkuStock selectSkuById(Long id){
        return skuStockMapper.selectById(id);
    }

    @GetMapping(value = "/notAuth/rpc/getPromotionProductList")
    List<PromotionProduct> getPromotionProductList(List<Long> productIdList){
        return  null;
    }

    @PostMapping(value = "/notAuth/rpc/updateSkuById")
    void updateSkuById(PmsSkuStock skuStock){
        skuStockMapper.updateById(skuStock);
    }


}
