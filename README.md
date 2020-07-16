

* **统一认证功能**
  * 支持oauth2的四种模式登录
  * 支持用户名、密码加图形验证码登录
  * 支持手机号加密码登录
  * 支持openId登录
  * 支持第三方系统单点登录

* **分布式系统基础支撑**
  * 服务注册发现、路由与负载均衡
  * 服务降级与熔断
  * 服务限流(url/方法级别)
  * 统一配置中心
  * 统一日志中心
  * 统一分布式缓存操作类、cacheManager配置扩展
  * 分布式锁
  * 分布式任务调度器
  * 支持CI/CD持续集成(包括前端和后端)
  * 分布式高性能Id生成器
  * 分布式事务
* **系统监控功能**
  * 服务调用链监控
  * 应用拓扑图
  * 慢服务检测
  * 服务Metric监控
  * 应用监控(应用健康、JVM、内存、线程)
  * 错误日志查询
  * 慢查询SQL监控
  * 应用吞吐量监控(qps、rt)
  * 服务降级、熔断监控
  * 服务限流监控
  * 分库分表、读写分离
* **业务基础功能支撑**
  * 高性能方法级幂等性支持
  * RBAC权限管理，实现细粒度控制(方法、url级别)
  * 快速实现导入、导出功能
  * 数据库访问层自动实现crud操作
  * 代码生成器
  * 基于Hutool的各种便利开发工具
  * 网关聚合所有服务的Swagger接口文档
  * 统一跨域处理
  * 统一异常处理

&nbsp;

数据库为mysql8.0及其以上, mongo为4.2,hb暂时不考虑

## 4. 模块说明

```lua
mokecloud -- 父项目，公共依赖
│  ├─moke-business -- 业务模块一级工程
│  │  ├─user-center -- 用户中心[7000]
│  │  ├─file-center -- 文件中心[5000]
│  │  ├─member-center -- 会员中心[7001]
│  │  ├─goods-center -- 商品中心[7002]
│  │  ├─order-center -- 订单中心[7003]
│  │  ├─marking-center -- 营销中心[7004]
│  │─moke-commons -- 通用工具一级工程
│  │  ├─`mall`-auth-client-spring-boot-starter -- 封装spring security client端的通用操作逻辑
│  │  ├─moke-common-spring-boot-starter -- 封装通用操作逻辑
│  │  ├─moke-db-spring-boot-starter -- 封装数据库通用操作逻辑
│  │  ├─moke-log-spring-boot-starter -- 封装log通用操作逻辑
│  │  ├─moke-redis-spring-boot-starter -- 封装Redis通用操作逻辑
│  │  ├─moke-ribbon-spring-boot-starter -- 封装Ribbon和Feign的通用操作逻辑
│  │  ├─moke-sentinel-spring-boot-starter -- 封装Sentinel的通用操作逻辑
│  │  ├─moke-swagger2-spring-boot-starter -- 封装Swagger通用操作逻辑
│  ├─moke-gateway -- api网关一级工程
│  │  ├─zuul-gateway -- netflix-zuul[8080]
│  ├─moke-job -- 分布式任务调度一级工程
│  │  ├─job-admin -- 任务管理器[8081]
│  │  ├─job-core -- 任务调度核心代码
│  │  ├─job-executor-samples -- 任务执行者executor样例[8082]
│  ├─moke-monitor -- 监控一级工程
│  │  ├─sc-admin -- 应用监控[6500]
│  │  ├─log-center -- 日志中心[6200]
│  ├─moke-uaa -- spring-security认证中心[8000]
│  ├─moke-register -- 注册中心Nacos[8848]
│  ├─moke-transaction -- 事务一级工程
│  │  ├─txlcn-tm -- tx-lcn事务管理器[7970]
│  ├─moke-demo -- demo一级工程
│  │  ├─txlcn-demo -- txlcn的demo
│  │  ├─sharding-jdbc-demo -- sharding-jdbc的demo
```
有些技术是手痒练手而已,es的数据导入主要是用logstash-6.2.1来进行.
阿里云效
ES通常以集群方式工作，这样做不仅能够提高 ES的搜索能力还可以处理大数据搜索的能力，同时也增加了系统的 容错能力及高可用，ES可以实现PB级数据的搜索

前端也会进行对应的更新


ELK已搭建完毕