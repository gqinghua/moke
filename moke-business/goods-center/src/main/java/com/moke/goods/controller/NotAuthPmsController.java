package com.moke.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.IgnoreAuth;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.pms.*;
import com.moke.common.entity.sms.SmsGroup;
import com.moke.common.entity.ums.SysSchool;
import com.moke.common.feign.MarkingFeignClinent;
import com.moke.common.feign.MemberFeignClient;
import com.moke.common.feign.UserService;
import com.moke.common.model.SysStore;
import com.moke.common.redis.constant.RedisToolsConstant;
import com.moke.common.redis.template.RedisRepository;
import com.moke.common.redis.template.RedisUtil;
import com.moke.common.utils.CommonResult;
import com.moke.common.utils.ValidatorUtils;
import com.moke.common.vo.GoodsDetailResult;
import com.moke.common.vo.HomeContentResult;
import com.moke.common.vo.ProductTypeVo;
import com.moke.goods.mapper.*;
import com.moke.goods.service.*;
import com.moke.goods.vo.ConsultTypeCount;
import com.moke.goods.vo.PromotionProduct;
import com.moke.sentinel.config.ConstansValue;
import com.moke.util.DateUtils;
import com.moke.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther:
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "NotAuthPmsController", description = "商品关系管理")
@RequestMapping("/notAuth")
public class NotAuthPmsController {
    @Resource
    private PmsGiftsMapper pmsGiftsMapper;
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
    private IPmsGiftsService giftsService;
    @Resource
    private IPmsGiftsCategoryService giftsCategoryService;
    @Resource
    private  PmsProductCategoryMapper categoryMapper;
    @Resource
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private IPmsFavoriteService favoriteService;
    @Autowired
    private MemberFeignClient memberFeignClient;
    @Autowired
    private MarkingFeignClinent markingFeignClinent;
    @Resource
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private UserService userService;
    @IgnoreAuth
    @ApiOperation("首页内容页信息展示")
    @SysLog(MODULE = "home", REMARK = "首页内容页信息展示")
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public Result content() {
        // List<UmsMember> log =  memberService.list(new QueryWrapper<UmsMember>().between("create_time","2018-03-03 00:00:00","2018-09-03 23:59:59"));
        HomeContentResult contentResult = pmsProductService.content();
        return Result.SUCCESS(contentResult);
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品详情信息")
    @IgnoreAuth
    @GetMapping(value = "/goods/detail")
    @ApiOperation(value = "查询商品详情信息")
    public Result queryProductDetail(@RequestParam(value = "id", required = false) Long id,
                                     @RequestParam(value = "memberId", required = false) Long memberId) {
        GoodsDetailResult param = null;
        try {
            param = (GoodsDetailResult) redisRepository.get(String.format(RedisToolsConstant.GOODSDETAIL, id));
            if(ValidatorUtils.empty(param)){
                param = pmsProductService.getGoodsRedisById(id);
            }
        }catch (Exception e){
            param = pmsProductService.getGoodsRedisById(id);
        }
        Map<String, Object> map = new HashMap<>();

        if (ValidatorUtils.notEmpty(memberId)) {
            PmsFavorite query = new PmsFavorite();
            query.setObjId(id);
            query.setMemberId(memberId);
            query.setType(1);
            PmsFavorite findCollection = favoriteService.getOne(new QueryWrapper<>(query));
            if(findCollection!=null){
                map.put("favorite", true);
            }else{
                map.put("favorite", false);
            }
        }

        map.put("goods", param);
        return Result.SUCCESS(map);
    }
    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有品牌表列表")
    @ApiOperation("根据条件查询所有品牌表列表")
    @GetMapping(value = "/brand/list")
    public Result getPmsBrandByPage(PmsBrand entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(IPmsBrandService.page(new Page<PmsBrand>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有品牌表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }
    @IgnoreAuth
    @ApiOperation("获取某个商品的评价")
    @RequestMapping(value = "/consult/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "goodsId", required = false) Long goodsId,
                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        PmsProductConsult productConsult = new PmsProductConsult();
        productConsult.setGoodsId(goodsId);
        List<PmsProductConsult> list =  pmsProductConsultService.list(new QueryWrapper<>(productConsult));

        int goods = 0;
        int general = 0;
        int bad = 0;
        ConsultTypeCount count = new ConsultTypeCount();
        for (PmsProductConsult consult : list) {
            if (consult.getStars()==1){
                bad++;
            }
            if (consult.getStars()==2){
                general++;
            }
            if (consult.getStars()==3){
                goods++;
            }
        }
        count.setAll(goods + general + bad);
        count.setBad(bad);
        count.setGeneral(general);
        count.setGoods(goods);
        if (count.getAll()>0){
            count.setPersent(new BigDecimal(goods).divide(new BigDecimal(count.getAll())).multiply(new BigDecimal(100)));
        }else {
            count.setPersent(new BigDecimal(200));
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("list", list);
        objectMap.put("count", count);
        return new CommonResult().success(objectMap);
    }
    @SysLog(MODULE = "pms", REMARK = "查询商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询商品列表")
    @GetMapping(value = "/goods/list")
    public Object goodsList(PmsProduct product,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        product.setPublishStatus(1);
        product.setVerifyStatus(1);
        return new CommonResult().success(pmsProductService.page(new Page<PmsProduct>(pageNum, pageSize), new QueryWrapper<>(product)));
    }


    @SysLog(MODULE = "pms", REMARK = "查询商品分类列表")
    @IgnoreAuth
    @ApiOperation(value = "查询商品分类列表")
    @GetMapping(value = "/productCategory/list")
    public Object productCategoryList(PmsProductCategory productCategory,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        return new CommonResult().success(productCategoryService.page(new Page<PmsProductCategory>(pageNum, pageSize), new QueryWrapper<>(productCategory)));
    }
    @SysLog(MODULE = "pms", REMARK = "查询商品分类列表")
    @IgnoreAuth
    @ApiOperation(value = "查询商品分类列表")
    @GetMapping(value = "/categoryAndGoodsList/list")
    public Object categoryAndGoodsList(PmsProductCategory productCategory) {
        List<PmsProductAttributeCategory> productAttributeCategoryList = productAttributeCategoryService.list(new QueryWrapper<>());
        for (PmsProductAttributeCategory gt : productAttributeCategoryList) {
            PmsProduct productQueryParam = new PmsProduct();
            productQueryParam.setProductAttributeCategoryId(gt.getId());
            productQueryParam.setPublishStatus(1);
            productQueryParam.setVerifyStatus(1);
            gt.setGoodsList(pmsProductService.list(new QueryWrapper<>(productQueryParam).select(ConstansValue.sampleGoodsList)));
        }
        return new CommonResult().success(productAttributeCategoryList);
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询首页推荐品牌")
    @GetMapping(value = "/recommendBrand/list")
    public Object getRecommendBrandList(
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        return new CommonResult().success(pmsProductService.getRecommendBrandList(1,1));
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询首页新品")
    @GetMapping(value = "/newProductList/list")
    public Object getNewProductList(
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        return new CommonResult().success(pmsProductService.getRecommendBrandList(1,1));
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询首页推荐商品")
    @GetMapping(value = "/hotProductList/list")
    public Object getHotProductList(
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        return new CommonResult().success(pmsProductService.getHotProductList(1,1));
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询商品优惠")
    @GetMapping(value = "/getPromotionProductList")
    public List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids){
        return productMapper.getPromotionProductList(ids);
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询首页推荐商品")
    @GetMapping(value = "/initGoodsRedis")
    public Object initGoodsRedis() {

        return pmsProductService.initGoodsRedis();

    }
    @SysLog(MODULE = "pms", REMARK = "查询商品类型下的商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询商品类型下的商品列表")
    @GetMapping(value = "/typeGoodsList")
    public Object typeGoodsList(PmsProductCategory productCategory) {
        PmsProduct productQueryParam = new PmsProduct();

        productQueryParam.setPublishStatus(1);
        productQueryParam.setVerifyStatus(1);
        List<PmsProduct> list = pmsProductService.list(new QueryWrapper<>(productQueryParam));

        List<ProductTypeVo> relList = new ArrayList<>();
        for (PmsProduct l : list){
            ProductTypeVo vo = new ProductTypeVo();
            vo.setGoodsId(l.getId());
            vo.setId(l.getId());
            vo.setPic(l.getPic());
            vo.setName(l.getName());
            vo.setPrice(l.getPrice());
            vo.setPid(l.getProductCategoryId());
            relList.add(vo);
        }
        List<PmsProductCategory> categories = categoryMapper.selectList(new QueryWrapper<>());
        for (PmsProductCategory v : categories){
            if (v.getParentId()==0){
                ProductTypeVo vo = new ProductTypeVo();
                vo.setName(v.getName());
                vo.setId(v.getId());
                relList.add(vo);
            }else{
                ProductTypeVo vo = new ProductTypeVo();
                vo.setName(v.getName());
                vo.setId(v.getId());
                vo.setPid(v.getParentId());
                relList.add(vo);
            }
        }

        return new CommonResult().success(relList);
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品类型下的商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询商品类型下的商品列表")
    @GetMapping(value = "/typeList")
    public Object typeList(PmsProductCategory productCategory) {
        List<ProductTypeVo> relList = new ArrayList<>();
        List<PmsProductCategory> categories = categoryMapper.selectList(new QueryWrapper<>());
        for (PmsProductCategory v : categories){
            if (v.getParentId()==0){
                ProductTypeVo vo = new ProductTypeVo();
                vo.setName(v.getName());
                vo.setId(v.getId());
                relList.add(vo);
            }else{
                ProductTypeVo vo = new ProductTypeVo();
                vo.setName(v.getName());
                vo.setId(v.getId());
                vo.setPid(v.getParentId());
                relList.add(vo);
            }
        }

        return new CommonResult().success(relList);
    }



    @IgnoreAuth
    @ApiOperation(value = "查询商铺列表")
    @GetMapping(value = "/store/list")
    @SysLog(MODULE = "ums", REMARK = "查询学校列表")
    public Object storeList(SysStore entity,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        return new CommonResult().success(userService.selectStoreList( new QueryWrapper<SysStore>(entity)));
    }
    @ApiOperation("获取商铺详情")
    @RequestMapping(value = "/storeDetail", method = RequestMethod.GET)
    @ResponseBody
    public Object storeDetail(@RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "memberId", required = false) Long memberId) {
        SysStore store = userService.selectStoreById(id);
        List<PmsProductAttributeCategory> list = productAttributeCategoryMapper.selectList(new QueryWrapper<PmsProductAttributeCategory>().eq("store_id",id));
        for (PmsProductAttributeCategory gt : list) {
            PmsProduct productQueryParam = new PmsProduct();
            productQueryParam.setProductAttributeCategoryId(gt.getId());
            productQueryParam.setPublishStatus(1);
            productQueryParam.setVerifyStatus(1);
            IPage<PmsProduct> goodsList = pmsProductService.page(new Page<PmsProduct>(0, 8), new QueryWrapper<>(productQueryParam).select(ConstansValue.sampleGoodsList));
            if (goodsList!=null&& goodsList.getRecords()!=null && goodsList.getRecords().size()>0){
                gt.setGoodsList(goodsList.getRecords());
            }else{
                gt.setGoodsList(new ArrayList<>());
            }
        }
        store.setList(list);
        store.setGoodsCount(pmsProductService.count(new QueryWrapper<PmsProduct>().eq("store_id",id)));
        //记录浏览量到redis,然后定时更新到数据库
        String key= RedisToolsConstant.STORE_VIEWCOUNT_CODE+id;
        //找到redis中该篇文章的点赞数，如果不存在则向redis中添加一条
        Map<Object,Object> viewCountItem=redisUtil.hGetAll(RedisToolsConstant.STORE_VIEWCOUNT_KEY);
        Integer viewCount=0;
        if(!viewCountItem.isEmpty()){
            if(viewCountItem.containsKey(key)){
                viewCount=Integer.parseInt(viewCountItem.get(key).toString())+1;
                redisUtil.hPut(RedisToolsConstant.STORE_VIEWCOUNT_KEY,key,viewCount+"");
            }else {
                viewCount=1;
                redisUtil.hPut(RedisToolsConstant.STORE_VIEWCOUNT_KEY,key,1+"");
            }
        }else{
            redisUtil.hPut(RedisToolsConstant.STORE_VIEWCOUNT_KEY,key,1+"");
        }
        Map<String, Object> map = new HashMap<>();
        if (memberId != null) {
            PmsFavorite query = new PmsFavorite();
            query.setObjId(id);
            query.setMemberId(memberId);
            query.setType(3);
            PmsFavorite findCollection = favoriteService.getOne(new QueryWrapper<>(query));
            if(findCollection!=null){
                map.put("favorite", true);
            }else{
                map.put("favorite", false);
            }
        }
        store.setHit(viewCount);
        map.put("store", store);
        return new CommonResult().success(map);
    }
    @IgnoreAuth
    @ApiOperation(value = "查询学校列表")
    @GetMapping(value = "/school/list")
    @SysLog(MODULE = "ums", REMARK = "查询学校列表")
    public Object schoolList(SysSchool entity,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        return new CommonResult().success(memberFeignClient.pageSchool(entity,pageSize,pageNum));
    }
    @ApiOperation("获取学校详情")
    @RequestMapping(value = "/schoolDetail", method = RequestMethod.GET)
    @ResponseBody
    public Object schoolDetail(@RequestParam(value = "id", required = false) Long id) {
        SysSchool school = memberFeignClient.getSchoolById(id);
        List<PmsProduct> list = productMapper.selectList(new QueryWrapper<PmsProduct>().eq("school_id", id).select(ConstansValue.sampleGoodsList));
        school.setGoodsList(list);
        school.setGoodsCount(list.size());
        return new CommonResult().success(school);
    }


    @SysLog(MODULE = "pms", REMARK = "查询团购商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询团购商品列表")
    @GetMapping(value = "/groupHotGoods/list")
    public Object groupHotGoods(PmsProduct product,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<SmsGroup> groupList =  markingFeignClinent.listGroup();
        List<SmsGroup> result = new ArrayList<>();
        for (SmsGroup group :groupList){
            Long nowT = System.currentTimeMillis();
            Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(group.getEndTime(), group.getHours()), "yyyy-MM-dd HH:mm:ss");
            if (nowT > group.getStartTime().getTime() && nowT < endTime.getTime()) {
                group.setGoods(pmsProductService.getById(group.getGoodsId()));
                result.add(group);
            }
        }
        return new CommonResult().success(result);
    }

    @SysLog(MODULE = "pms", REMARK = "查询团购商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询团购商品列表")
    @GetMapping(value = "/groupGoods/list")
    public Object groupGoodsList(PmsProduct product,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<SmsGroup> groupList =  markingFeignClinent.listGroup();
        if (groupList!=null && groupList.size()>0){
            List<Long> ids = groupList.stream()
                    .map(SmsGroup::getGoodsId)
                    .collect(Collectors.toList());
            product.setPublishStatus(1);
            product.setVerifyStatus(1);
            product.setMemberId(null);
            IPage<PmsProduct> list  = pmsProductService.page(new Page<PmsProduct>(pageNum, pageSize), new QueryWrapper<>(product).in("id",ids));
            return new CommonResult().success(list);
        }
        return null;
    }

    @SysLog(MODULE = "pms", REMARK = "查询商品详情信息")
    @IgnoreAuth
    @GetMapping(value = "/goodsGroup/detail")
    @ApiOperation(value = "查询商品详情信息")
    public Object groupGoodsDetail(@RequestParam(value = "id", required = false) Long id,
                                   @RequestParam(value = "memberId", required = false) Long memberId,
                                   @RequestParam(value = "groupId", required = false) Long groupId) {
        //记录浏览量到redis,然后定时更新到数据库
        String key=RedisToolsConstant.GOODS_VIEWCOUNT_CODE+id;
        //找到redis中该篇文章的点赞数，如果不存在则向redis中添加一条
        Map<Object,Object> viewCountItem=redisUtil.hGetAll(RedisToolsConstant.GOODS_VIEWCOUNT_KEY);
        Integer viewCount=0;
        if(!viewCountItem.isEmpty()){
            if(viewCountItem.containsKey(key)){
                viewCount=Integer.parseInt(viewCountItem.get(key).toString())+1;
                redisUtil.hPut(RedisToolsConstant.GOODS_VIEWCOUNT_KEY,key,viewCount+"");
            }else {
                viewCount=1;
                redisUtil.hPut(RedisToolsConstant.GOODS_VIEWCOUNT_KEY,key,1+"");
            }
        }else{
            viewCount=1;
            redisUtil.hPut(RedisToolsConstant.GOODS_VIEWCOUNT_KEY,key,1+"");
        }

        GoodsDetailResult param = null;
        try {
            param = (GoodsDetailResult) redisRepository.get(String.format(RedisToolsConstant.GOODSDETAIL, id));
            if(ValidatorUtils.empty(param)){
                param = pmsProductService.getGoodsRedisById(id);
            }
        }catch (Exception e){
            param = pmsProductService.getGoodsRedisById(id);
        }
        SmsGroup group = markingFeignClinent.getGroupById(groupId);
        Map<String, Object> map = new HashMap<>();
        if (memberId != null) {
            PmsProduct p = param.getGoods();
            p.setHit(viewCount);
            PmsFavorite query = new PmsFavorite();
            query.setObjId(p.getId());
            query.setMemberId(memberId);
            query.setType(1);
            PmsFavorite findCollection = favoriteService.getOne(new QueryWrapper<>(query));
            if(findCollection!=null){
                map.put("favorite", true);
            }else{
                map.put("favorite", false);
            }
        }
        if (group!=null){
            map.put("memberGroupList",markingFeignClinent.selectGroupMemberList(id));
            map.put("group", group);
        }


        map.put("goods", param);
        return new CommonResult().success(map);
    }


    @SysLog(MODULE = "pms", REMARK = "查询团购商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询礼物商品列表")
    @GetMapping(value = "/gift/list")
    public Object giftList(PmsGifts product,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        IPage<PmsGifts> list  = giftsService.page(new Page<PmsGifts>(pageNum, pageSize), new QueryWrapper<>(product));
        return new CommonResult().success(list);

    }

    @SysLog(MODULE = "pms", REMARK = "查询商品详情信息")
    @IgnoreAuth
    @GetMapping(value = "/gift/detail")
    @ApiOperation(value = "查询礼物商品详情信息")
    public Object giftDetail(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "memberId", required = false) Long memberId) {
        PmsGifts  goods = giftsService.getById(id);
        Map<String, Object> map = new HashMap<>();
        if (memberId != null) {
            PmsFavorite query = new PmsFavorite();
            query.setObjId(goods.getId());
            query.setMemberId(memberId);
            query.setType(4);
            PmsFavorite findCollection = favoriteService.getOne(new QueryWrapper<>(query));
            if(findCollection!=null){
                map.put("favorite", true);
            }else{
                map.put("favorite", false);
            }
        }
        map.put("goods", goods);
        return new CommonResult().success(map);
    }
    @SysLog(MODULE = "pms", REMARK = "查询商品类型下的商品列表")
    @IgnoreAuth
    @ApiOperation(value = "查询积分商品类型")
    @GetMapping(value = "/typeGiftList")
    public Object typeGiftList( PmsGiftsCategory productCategory) {
        List<PmsGiftsCategory> categories = giftsCategoryService.list(new QueryWrapper<>(productCategory));
        return new CommonResult().success(categories);
    }

    @ApiOperation(value = "查询积分商品类型")
    @PostMapping(value = "/updateGoodsById")
    public void updateGoodsById(@RequestBody PmsProduct goods) {
        productMapper.updateById(goods);
    }

    @ApiOperation(value = "查询积分商品类型")
    @PostMapping(value = "/updateSkuById")
    public void updateSkuById(@RequestBody PmsSkuStock goods) {
        skuStockMapper.updateById(goods);
    }

    @ApiOperation(value = "查询积分商品类型")
    @GetMapping(value = "/getGiftById")
    public PmsGifts getGiftById(@RequestParam("goodsId") Long goodsId) {
        return giftsService.getById(goodsId);
    }

    @GetMapping("/goods/{id}")
    public PmsProduct findUserById(@RequestParam("id") Long id) {
        return pmsProductService.getById(id);
    }

    @ApiOperation(value = "查询积分商品类型")
    @PostMapping(value = "/updateGiftById")
    public void updateGiftById(@RequestBody PmsGifts goods) {
        pmsGiftsMapper.updateById(goods);
    }

    @GetMapping(value = "/notAuth/listGoodsByIds")
    public List<PmsProduct> listGoodsByIds(@RequestParam("ids") List<Long> ids) {
        return productMapper.selectBatchIds(ids);
    }

    @PostMapping(value = "/notAuth/rpc/saveProductConsult")
    public void saveProductConsult(@RequestBody PmsProductConsult productConsult) {
        pmsProductConsultService.save(productConsult);
    }
}
