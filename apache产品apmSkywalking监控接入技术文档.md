# skywalking部署

## 1.部署skywalking（坑很多）

```
1: window下部署:步骤如下,直接启动
mysql数据库:注掉h2,把mysql的注解去掉
启动apm,bin下的startup.bat启动
访问127.0.0.1:8080

```

```
2:linux下部署,
在webapp下,修改webapp.yml端口号改为18080原本为8080,到bin目录下./startup.sh启动skywalking
启动命令如下
java -javaagent:/root/apache-skywalking-apm-bin/agent/skywalking-agent.jar
-Dskywalking.agent.service_name=nacos01 -jar nacos-service-provider-2.1.4.RELEASE.jar

edas:
我在edas里yingxiao应用里加入了自定义jvm参数,-javaagent:/root/apache-skywalking-apm-bin/agent/skywalking-agent.jar 需要重启一下


权限问题,需要在admin下进行操作
 chown admin:admin config -R
 /home/admin/app
```

```
把本地跑的项目注入到skywalking中
解压skywalking压缩包,找到agent下skywalking-agent 的jar包,在idea上启动参数加上
-Dskywalking.agent.service_name=nacos-provider02-test
-Dskywalking.collector.backend_service=123.57.128.134:11800
-javaagent:E:/software02/apache-skywalking-apm-6.5.0/apache-skywalking-apm-bin/agent/skywalking-agent.jar
第一行是skywalking显示的服务名称,
第二行是oap地址,不要改
第三行是skywalking的探针包就是skywalking-agent.jar地址.

```



## 2.项目加入skywalking的agent包

```
在agent的/skywalking-agent.jar是java探针埋点的jar包,很重要,需要在jar启动之前申明命令
如上面的命令
```

docker接入skywalking

```
docker run -e SW_COLLECTOR_SERVERS={你的ip}:11800 -e SW_SERVICE_NAME=docker-boot -p 10010:10010 docker-boot:v0.1
```



## 3.添加启动参数-javaagent指定agent包的位置



```
在开发环境中idea的vmoptions中加入agentjiar包位置
-javaagent:E:/software/apache-skywalking-apm-bin/agent/skywalking-agent.jar
```

```
├── agent                    #skywalking agent目录，也有人称为探针 
│    ├── activations                #不知道干啥用
│    ├── bootstrap-plugins          #Bootstrap 类插件
│    ├── config                     #配置目录其中包含一个agent.conf------需要配置
│    ├── logs                       #agent的日志
│    ├── optional-plugins           #可选插件
│    ├── plugins                    #（如想使用可选插件或bootstrap插件，把包放到该目录下）
│    ├── skywalking-agent.jar       #agent的jar          
├── bin                      #启动脚本，内含skywakling
│    ├── oapService.bat              #oap初始化启动脚本windows
│    ├── oapServiceInit.bat          #oap初始化脚本windows
│    ├── oapServiceInit.sh           #oap初始化脚本linux
│    ├── oapServiceNoInit.bat        #oap无需初始化启动脚本windows
│    ├── oapServiceNoInit.sh         #oap无需初始化启动脚本linux
│    ├── oapService.sh               #oap初始化启动脚本windows
│    ├── startup.bat                 #sw启动脚本windows
│    ├── startup.sh                  #sw启动脚本linux------直接启动这个即可(相当于oap+UI)
│    ├── webappService.bat           #UI启动脚本windows
│    └── webappService.sh            #UI启动脚本linux------想重启UI使用这个
├── config                   #配置文件目录
│    ├── alarm-settings-sample.yml       #告警配置示例
│    ├── alarm-settings.yml              #告警配置
│    ├── application.yml                 #oap服务配置------需要配置
│    ├── component-libraries.yml         #组件库配置,定义被监控应用中使用的所有组件库
│    ├── gateways.yml                    #网关配置
│    ├── log4j2.xml                      #日志配置
│    ├── official_analysis.oal           #数据分析指标配置
│    └── service-apdex-threshold.yml     #阀值配置
├── LICENSE
├── licenses 
├── NOTICE
├── oap-libs                 #oap依赖，不作展开
├── README.txt  
└── webapp                   #UI jar包
      ├── skywalking-webapp.jar            #UI jar包
      └── webapp.yml                       #UI配置文件------需要配置

```



# 搭建 SkyWalking 集群环境

 在生产环境下，我们一般推荐搭建 SkyWalking 集群环境。  也可以在生产环境下使用 SkyWalking 单机环境，毕竟 SkyWalking 挂了之后，不影响业务的正常运行。 

 搭建一个 SkyWalking **集群**环境，步骤如下： 

第一步，搭建一个 Elasticsearch 服务的**集群**。可忽略



第二步，搭建一个注册中心的**集群**。目前 SkyWalking 支持 Zookeeper、Kubernetes、Consul、Nacos 作为注册中心。可忽略

第三步，同时参考[《SkyWalking 文档 —— 集群管理》](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/master/setup/backend/backend-cluster.md)

oap搭建 zookeeper-3.4.10   服务端collector 

修改apache-skywalking-apm-incubating\config\application.yml

启动apache-skywalking-apm-incubating\bin\collectorService.bat

```
cluster:
  zookeeper
    hostPort: 10.10.20.198:2181,10.10.20.64:2181,10.10.20.63:2181
    sessionTimeout: 100000
naming:
  jetty:
    host: 10.10.20.198
remote:
  gRPC:
    host: 10.10.20.198
agent_gRPC:
  gRPC:
    host: 10.10.20.198
agent_jetty:
  jetty:
    host: 10.10.20.198
ui:
  jetty:
    host: 10.10.20.198
storage:
  elasticsearch:
    clusterName: CollectorDBCluster
    clusterNodes: 10.10.20.198:9300,10.10.20.64:9300,10.10.20.63:9300
```

2.5 服务端webui配置

修改apache-skywalking-apm-incubating\webapp\webapp.yml

启动apache-skywalking-apm-incubating\bin\webappservice.bat

```
server:
  port: 8080
collector:
  path: /graphql
  ribbon:
    ReadTimeout: 10000
    listOfServers: 127.0.0.1:10800
```

2.6 客户端collector配置

    单独下载skywalking-collector包到客户端
    修改D:\skywalking-collector\config\collector.conf
    启动D:\skywalking-collector\bin\collector-service.bat
```
cluster.current.hostname = 10.10.20.63 #当前服务器
cluster.seed_nodes=10.10.20.63:11800 #当前服务器
es.cluster.nodes=10.10.20.63:9300,10.10.20.198:9300,10.10.20.64:9300
http.hostname=10.10.20.198  #服务端webui所在服务器
```

，将 SkyWalking OAP 服务注册到注册中心上

第四步，启动一个 Spring Boot 应用，并配置 SkyWalking Agent。另外，在设置 SkyWaling Agent 的 `SW_AGENT_COLLECTOR_BACKEND_SERVICES` 地址时，需要设置多个 SkyWalking OAP 服务的地址数组。



第五步，搭建一个 SkyWalking UI 服务的**集群**，同时使用 Nginx 进行负载均衡。另外，在设置 SkyWalking UI 的 `collector.ribbon.listOfServers` 地址时，也需要设置多个 SkyWalking OAP 服务的地址数组。









-Dskywalking.agent.service_name=nacos-test
-Dskywalking.collector.backend_service=123.57.128.134:11800
-javaagent:E:/software02/apache-skywalking-apm-bin/agent/skywalking-agent.jar

## 系统学习及学习文档详细

## skywalking中有P50，P90，P95这种统计口径，就是百分位数的概念。

       释义：在一个样本数据集合中，通过某个样本值，可以得到小于这个样本值的数据占整体的百分之多少，这个样本值的值就是这个百分数对应的百分位数。
    
       举例：全公司参加考试，有百分之八十的人都低于60分，那么对于整个公司的考试成绩这个样本集合来说，第八十百分位数就是60；
    
       图例：如下图，表示7月22日，14:56分这个时间点探针反馈的统计结果来看，有50%的请求响应时间低于60ms，有75%的请求响应时间低于60ms，有90%的请求响应时间低于550ms，有95%的请求响应时间低于550ms，有99%的请求响应时间低于550ms
# 什么是 SkyWalking





历史版本,配置导入,配置导出,服务列表,服务发布,服务订阅,节点列表,心跳检查,节点状态,服务发现,命名

SkyWalking 是观察性分析平台和应用性能管理系统。

提供分布式追踪、服务网格遥测分析、度量聚合和可视化一体化解决方案.

支持Java, .Net Core, PHP, NodeJS, Golang, LUA语言探针

支持Envoy + Istio构建的Service Mesh

 ![https://skywalking.apache.org/assets/frame-v8.jpg](https://skywalking.apache.org/assets/frame-v8.jpg) 

## 技术点:java探针

使用java代理来实现java字节码注入
 使用JavaSsist可以对字节码进行修改
 使用ASM可以修改字节码

**使用Java代理和ASM字节码技术开发java探针工具可以修改字节码**

备注：javassist是一个库，实现ClassFileTransformer接口中的transform()方法。ClassFileTransformer 这个接口的目的就是在class被装载到JVM之前将class字节码转换掉，从而达到动态注入代码的目的。

备注：ASM是一个java字节码操纵框架，它能被用来动态生成类或者增强既有类的功能。ASM 可以直接产生二进制 class  文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。Java class 被存储在严格格式定义的  .class文件里，这些类文件拥有足够的元数据来解析类中的所有元素：类名称、方法、属性以及 Java  字节码（指令）。ASM从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生成新类。

 java探针工具原理图 :

 1：在JVM加载class二进制文件的时候，利用ASM动态的修改加载的class文件，在**监控的方法**前后添加计时器功能，用于计算监控方法耗时；
 2：将监控的相关方法 和 耗时及内部调用情况，按照顺序放入处理器；
 3：处理器利用栈先进后出的特点对方法调用先后顺序做处理，当一个请求处理结束后，将**耗时方法轨迹**和**入参map**输出到文件中；
 4：然后区分出耗时的业务，转化为xml格式进行解析和分析。 

Java探针工具功能点:

1、支持方法执行耗时范围抓取设置，根据耗时范围抓取系统运行时出现在设置耗时范围的代码运行轨迹。

2、支持抓取特定的代码配置，方便对配置的特定方法进行抓取，过滤出关系的代码执行耗时情况。

3、支持APP层入口方法过滤，配置入口运行前的方法进行监控，相当于监控特有的方法耗时，进行方法专题分析。

4、支持入口方法参数输出功能，方便跟踪耗时高的时候对应的入参数。

5、提供WEB页面展示接口耗时展示、代码调用关系图展示、方法耗时百分比展示、可疑方法凸显功能。



第一篇：

JavaAgent 是JDK 1.5 以后引入的，也可以叫做Java代理。

JavaAgent 是运行在 main方法之前的拦截器，它内定的方法名叫 premain ，也就是说先执行 premain 方法然后再执行 main 方法。

那么如何实现一个 JavaAgent 呢？很简单，只需要增加 premain 方法即可

java探针技术不依赖与任何框架

## 架构

- **探针** 基于不同的来源可能是不一样的, 但作用都是收集数据, 将数据格式化为 SkyWalking 适用的格式.
- **平台后端**, 支持数据聚合, 数据分析以及驱动数据流从探针到用户界面的流程。分析包括 Skywalking 原生追踪和性能指标以及第三方来源，包括 Istio 及 Envoy  telemetry , Zipkin 追踪格式化等。 你甚至可以使用  [Observability Analysis Language 对原生度量指标](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/concepts-and-designs/oal.md) 和 [用于扩展度量的计量系统](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/concepts-and-designs/meter.md) 自定义聚合分析。
- **存储** 通过开放的插件话的接口存放 SkyWalking 数据. 你可以选择一个既有的存储系统, 如  ElasticSearch, H2 或 MySQL 集群(Sharding-Sphere 管理),也可以选择自己实现一个存储系统. 当然,  我们非常欢迎你贡献新的存储系统实现。
- **UI** 一个基于接口高度定制化的Web系统，用户可以可视化查看和管理 SkyWalking 数据。

整个架构，分成上、下、左、右四部分：

> 考虑到让描述更简单，我们舍弃掉 Metric 指标相关，而着重在 Tracing 链路相关功能。

- 上部分 **Agent** ：负责从应用中，收集链路信息，发送给 SkyWalking OAP 服务器。目前支持 SkyWalking、Zikpin、Jaeger  等提供的 Tracing 数据信息。而我们目前采用的是，SkyWalking Agent 收集 SkyWalking Tracing  数据，传递给服务器。
- 下部分 **SkyWalking OAP** ：负责接收 Agent 发送的 Tracing 数据信息，然后进行分析(Analysis Core) ，存储到外部存储器( Storage )，最终提供查询( Query )功能。
- 右部分 **Storage** ：Tracing 数据存储。目前支持 ES、MySQL、Sharding Sphere、TiDB、H2 多种存储器。而我们目前采用的是 ES ，主要考虑是 SkyWalking 开发团队自己的生产环境采用 ES 为主。
- 左部分 **SkyWalking UI** ：负责提供控台，查看链路等等



## skywalking概念技术词语:

###  **服务(Service)** ：

表示对请求提供相同行为的一系列或一组工作负载。在使用 Agent 或 SDK 的时候，你可以定义服务的名字。如果不定义的话，SkyWalking 将会使用你在平台（例如说 Istio）上定义的名字。 

 这里，我们可以看到 Spring Boot 应用的**服务**为 `"demo-application"`，就是我们在环境变量 `SW_AGENT_NAME` 中所定义的。 

###  **服务实例(Service Instance)** ： 

 上述的一组工作负载中的每一个工作负载称为一个实例。就像 Kubernetes 中的 pods 一样, 服务实例未必就是操作系统上的一个进程。但当你在使用 Agent 的时候, 一个服务实例实际就是操作系统上的一个真实进程。

  这里，我们可以看到 Spring Boot 应用的**服务**为 `{agent_name}-pid:{pid}@{hostname}`，由 Agent 自动生成。关于它，我们在[「5.1 hostname」](https://skywalking.apache.org/zh/blog/2020-04-19-skywalking-quick-start.html#)小节中，有进一步的讲解，胖友可以瞅瞅。 

###  **端点(Endpoint)** ： 

对于特定服务所接收的请求路径, 如 HTTP 的 URI 路径和 gRPC 服务的类名 + 方法签名。

> 这里，我们可以看到 Spring Boot 应用的一个**端点**，为 API 接口 `/demo/echo`。

##  kyWalking 项目的核心设计目标

- **保持可观测性**. 不管目标系统如何部署, SkyWalking 总要提供一种方案或集成方式来保持对目标系统的观测, 基于此, SkyWalking 提供了数种运行时探针。
- **拓扑结构, 性能指标和追踪一体化**. 理解分布式系统的第一步是通过观察其拓扑结构图.  拓扑图可以将复杂的系统在一张简单的图里面进行可视化展现. 基于拓扑图，运维支撑系统相关人员需要更多关于服务/实例/端点/调用的性能指标.  链路追踪(trace)作为详细的日志, 对于此种性能指标来说很有意义, 如你想知道什么时候端点延时变得很长, 想了解最慢的链路并找出原因.  因此你可以看到, 这些需求都是从大局到细节的, 都缺一不可. SkyWalking 集成并提供了一系列特性来使得这些需求成为可能,  并且使之易于理解.
- **轻量级**. 有两个方面需要保持轻量级. (1) 探针, 我们通常依赖于网络传输框架, 如 gRPC.  在这种情况下, 探针就应该尽可能小, 防止依赖库冲突以及虚拟机的负载压力(例如 JVM 永久代内存占用压力). (2) 作为一个观测平台,  在你的整个项目环境中只是次要系统, 因此我们使用自己的轻量级框架来构建后端核心服务. 所以你不需要部署并维护大数据相关的平台,  SkyWalking 在技术栈方面应该足够简单。
- **可插拔**. SkyWalking 核心团队提供了许多默认实现, 但这肯定是不够的, 也不可能适用于每一种场景, 因此我们提供了大量的特性来支持可插拔功能。
- **可移植**.  SkyWalking 可以运行在多种环境下, 包括: (1) 使用传统的注册中心, 如 [Eureka](https://github.com/spring-cloud/spring-cloud-netflix) (2) 使用包含服务发现的RPC框架，如Spring Cloud, Apache Dubbo (3) 在现代基础设施中使用服务网 (4) 使用云服务 (5) 跨云部署

在所有这些情况下，SkyWalking 应该运行良好

- **可互操作**. 可观测性是一个庞大的领域, 即使有强大的社区, SkyWalking  不可能支持所有方方面面, 因此 SkyWalking 支持与其他运维支撑系统进行互操作, 主要是探针, 如 Zipkin, Jaeger,  OpenTracing 和 OpenCensus. SkyWalking 接收并理解他们的数据格式, 这对于终端用户来说是非常有用的,  因为不需要他们更换已有的库。

## 探针简介

在 SkyWalking 中, 探针表示集成到目标系统中的代理或 SDK 库, 它负责收集遥测数据, 包括链路追踪和性能指标。根据目标系统的技术栈, 探针可能有差异巨大的方式来达到以上功能. 但从根本上来说都是一样的, 即收集并格式化数据, 并发送到后端。

从高层次上来讲, SkyWalking 探针可分为以下三组：

- **基于语言的原生代理**. 这种类型的代理运行在目标服务的用户空间中, 就像用户代码的一部分一样. 如 SkyWalking Java 代理, 使用 `-javaagent` 命令行参数在运行期间对代码进行操作, `操作` 一词表示修改并注入用户代码. 另一种代理是使用目标库提供的钩子函数或拦截机制. 如你所见, 这些探针是基于特定的语言和库。
- **服务网格探针**. 服务网格探针从服务网格的 Sidecar 和控制面板收集数据. 在以前, 代理只用作整个集群的入口, 但是有了服务网格和 Sidecar 之后, 我们可以基于此进行观测了。
- **第三方打点类库**. SkyWalking 也能够接收其他流行的打点库产生的数据格式. SkyWalking 通过分析数据,将数据格式化成自身的链路和度量数据格式. 该功能最初只能接收 Zipkin 的 span 数据. 更多参考[其他追踪系统的接收器](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/setup/backend/backend-receivers.md)。

你不必同时使用 **基于语言的原生代理** 和 **服务网格探针** ，因为两者都收集指标数据，否则你的系统就要承受双倍负载, 且分析数量会翻倍.

有如下几种推荐的方式来使用探针:

1. 只使用 **基于语言的原生代理**.
2. 只使用 **第三方打点库**, 如 Zipkin 打点系统.
3. 只使用 **服务网格探针**.,比如redissson
4. 使用 **服务网格探针**, 配合 **语言原生代理** 或 **第三方打点库**, 来 **追踪状态** . (高级用法)

## 服务自动打点代理:

服务自动打点代理是基于语言的原生代理的一部分,这种代理需要依靠某些语言特定的特性, 通常是一种基于虚拟机的语言.

### 自动打点是什么意思?

许多用户都是在听到"他们说不需要改一行代码"之后才了解到这些代理的, SkyWalking 以前也将这种说法放在 README 文档中. 实际上这种说法是既对又错的. 对于最终用户来说是对的, 他们不需要修改代码(至少在绝大多数情况下). 但这种说法也是错的, 因为代码实际上还是被修改了, 只是被代理给修改了, 这种做法通常叫做"在运行时操作代码". 底层原理就是自动打点代理利用了虚拟机提供的用于修改代码的接口来动态加入打点的代码, 如通过 `javaagent premain` 来修改 Java 类.

此外, 我们说大部分自动打点代理是基于虚拟机的, 但实际上你也可以在编译期构建这样的工具.

### 有什么限制?

自动打点很好用, 你还可以在编译时进行自动打点而不需要依赖虚拟机特性, 那么这里有什么限制吗?

答案当然是有, 以下就是它们的限制:

- **进程内传播在大多数情况下成为可能**. 许多高级编程语言(如 Java, .NET)都是用于构建业务系统. 大部分业务逻辑代码对于每一个请求来说都运行在同一个线程内, 这使得传播是基于线程 ID 的, 以确保上下文是安全的.
- **仅仅对某些框架和库奏效**. 因为是代理来在运行时修改代码的, 这也意味着代理插件开发者事先就要知道 所要修改的代码是怎么样的. 因此, 在这种探针下通常会有一个已支持的列表清单. 如 [SkyWalking Java 代理支持列表](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/setup/service-agent/java-agent/Supported-list.md).
- **跨线程可能并非总是奏效**. 如上所述, 每个请求的代码大都运行在一个线程之内, 对于业务代码来说尤其如此. 但是在其他一些场景下, 它们也会在不同线程下工作, 比如指派任务到其他线程, 任务池, 以及批处理. 对于一些语言, 可能还提供了协程或类似的概念如 `Goroutine`, 使得开发者可以低开销地来执行异步操作, 在这些场景下, 自动打点可能会遇到一些问题.

所以说自动打点没有什么神秘的, 总而言之就是, 自动打点代理开发者写了一个激活程序, 使得打点的代码 自动运行, 仅此而已.

## 服务网格探针

服务网格探针使用了服务网格实现者中提供的可扩展机制，比如 Istio。

### 什么是服务网格

下面的解释来自Istio文档。

> 服务网格通常用于描述组成此类应用程序的微服务网络以及它们之间的交互。随着服务网格的大小和复杂性的增长，它会变得更难理解和管理。它需要包括发现、负载平衡、故障恢复、度量和监视以及更复杂的操作需求A/B测试、金丝雀发布、限流、访问控制和端到端身份验证。

### 探针从哪里采集数据

Istio 是一个非常典型的服务网格的设计和实现。它定义了 **控制平面** 和 **数据平面**，被广泛使用。下面是 Istio 的架构 :

[![Istio 架构](https://camo.githubusercontent.com/ba0adfcf26c1d8564b216b3c32d6db88dfb16a51/68747470733a2f2f697374696f2e696f2f646f63732f6f70732f6172636869746563747572652f617263682e737667)](https://camo.githubusercontent.com/ba0adfcf26c1d8564b216b3c32d6db88dfb16a51/68747470733a2f2f697374696f2e696f2f646f63732f6f70732f6172636869746563747572652f617263682e737667)

服务网格探针可以选择从 **控制平面** 和 **数据平面** 采集数据。在 Istio 中，指的是从 Mixer(Control Panel) 或者 Envoy sidecar(Data Panel) 中采集遥测数据。探针从客户端和服务器端收集每个请求的两个遥测实体，它们其实是相同的数据。

### 服务网格如何使后端工作

从探针中，您可以看到在这种探针中一定没有相关的跟踪，那么为什么 SkyWalking 平台仍然可以工作？

服务网格探针从每个请求收集遥测数据，因此它知道源、目标、端点、延迟和状态。通过这些，后端可以通过将这些调用合并为行来描述整个拓扑图，以及每个节点通过传入请求的度量。后端解析跟踪数据，请求相同的度量数据。因此，正确的表述是：

**服务网格度量就是跟踪解析器生成的度量。他们是相同的。**

## 观测分析平台

OAP 观测分析平台(Observability Analysis Platform)是一个从 SkyWalking 6.x 开始出现的新概念. OAP 取代了整个旧的 SkyWalking 后端. OAP 的能力如下所述.

### OAP 能力

OAP 从多种数据源接收数据, 这些数据分为两大类, **链路追踪** 和 **度量指标** .

- **链路追踪**. 包括 SkyWalking 原生数据格式, Zipkin V1 和 V2 数据格式, 以及 Jaeger 数据格式.
- **度量指标**. SkyWalking 集成了服务网格平台, 如 Istio, Envoy 和 Linkerd, 并在数据面板和控制面板进行观测。此外, SkyWalking 原生代理还可以运行在度量模式, 这极大提升了性能。

可以同时使用提供的任何集成解决方案，比如 SkyWalking 日志插件或工具包， SkyWalking 还提供了可视化集成来对追踪和日志进行绑定, 这是通过使用 trace id 和 span id 实现的.

通常所有的服务都是通过 gRPC 和 HTTP 协议实现, 使得未来集成那些尚未支持的生态系统更加容易.

### OAP 中的链路追踪

链路追踪在 OAP 中的有两种处理.

1. 在 SkyWalking 5.x 中传统的方式. 以 SkyWalking 的 segment 和 span 来格式化追踪数据，甚至包括 Zipkin 数据格式化。OAP 通过分析数据段获得度量指标, 并将度量数据推送到聚合流。
2. 考虑仅仅将追踪视为某种日志, 只提供存储和可视化能力.

同样的, SkyWalking 接收来自其他系统的追踪数据格式, 如 Zipkin, Jeager, OpenCensus. 这些格式也可以由以上两种方式进行处理.

### OAP 中的度量指标

OAP 中的度量指标是 6.x 版本中全新的功能. 通过连接的节点之间的度量数据, 构建分布式系统的观测数据, 且不需要追踪数据.

度量数据在 OAP 集群中以流的模式进行聚合. 参考[观测分析语言](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/concepts-and-designs/oal.md), 该文介绍了如何使用简单的脚本形式进行数据聚合和分析。

## 协议

有以下两种类型的协议.

- [**探针协议**](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/protocols/README.md#探针协议). 包括对探针如何发送收集到的度量数据、跟踪信息以及涉及到的每个实体格式的描述和定义。
- [**查询协议**](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/protocols/README.md#查询协议). 服务后端给 SkyWalking 自有的 UI 和任何第三方 UI 提供了查询的能力. 这些查询都是基于 GraphQL 的.

#### 探针协议

它们也是与探针的组相关的, 为了理解这一点, 请参考[概念和设计](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/concepts-and-designs/README.md)一文. 这些探针组是**基于原生语言代理协议**, **服务网格协议**以及其他第三方打点协议.

### 注册协议

包含服务, 服务实例, 网络地址以及端点元数据注册. 注册的目的是:

1. 对于服务, 网络地址和端点, 注册之后将会返回注册对象的一个唯一 ID, 通常是一个整数. 探针可以使用这个 ID 来替代字符串文本, 达到数据压缩的功能. 进一步讲, 有些协议只接收 ID.
2. 对于服务实例, 注册之后将会为每个新的实例返回一个新的唯一 ID. 每个服务实例必须包含服务 ID.

#### 基于语言的原生代理协议

有以下两种协议可以让基于语言的代理在分布式环境下工作.

1. **跨进程传播的头部协议**是一种有线数据格式, 代理和 SDK 通常使用 RPC 请求以及 HTTP/MQ/HTTP2 请求头来运载数据. 远程代理将在请求处理器中接收这些数据, 并将上下文绑定到该请求中.
2. **追踪数据协议**是一种离线数据格式, 代理和 SDK 使用这种数据格式来发送追踪数据和指标数据到 SkyWalking 或其他兼容的后端。

为了兼容性, 请求头有两种格式. 默认是使用 v2.

- [跨进程传播的请求头 v2](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/protocols/Skywalking-Cross-Process-Propagation-Headers-Protocol-v2.md) 是自 6.0.0-beta 版本开始, 针对在线上下文传播的一种新的协议. 它将在以后替代老的 **SW3** 协议, 目前来说它们二者都是支持的.
- [跨进程传播的请求头 v1](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/protocols/Skywalking-Cross-Process-Propagation-Headers-Protocol-v1.md) 是针对在线传播的协议. 遵循此协议的不同进程的追踪数据段可以被连接起来.

自 SkyWalking v6.0.0-beta 开始, SkyWalking 代理和后端都使用第二版的追踪数据协议(Trace Data Protocol v2), 后端仍然支持 v1 版本的协议.

- [SkyWalking 追踪数据协议 v2](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/protocols/Trace-Data-Protocol-v2.md) 定义了代理和后端之间通讯的方式和格式.
- [SkyWalking 追踪数据协议 v1](https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/protocols/Trace-Data-Protocol.md). 该协议用于旧的版本中. 目前仍然支持.

#### 服务网格探针协议

Sidecar 中的探针或代理可以使用该协议发送数据到后端. 该服务通过 gRPC 实现, 需要以下关键信息:

1. 在服务两侧都需要服务名或 ID
2. 在服务两侧都需要服务实例名称或 ID
3. 端点. HTTP 中的 URI, gRPC 中的方法完整签名.
4. 时延. 以毫秒为单位
5. HTTP 中的响应码
6. 状态. 成功或失败
7. 协议. HTTP, gRPC 等
8. 监测点. 在服务网格 sidecar 中, `client` 或 `server`。 在普通的 L7 代理中, 值是 `proxy`.

#### 第三方打点协议

SkyWalking 并不定义第三方打点协议. 它们只是协议和数据格式, SkyWalking 兼容这些协议和数据格式, 且可以接收它们. SkyWalking 一开始就支持 Zipkin v1, v2 数据格式. 后端遵循模块化原则, 所以要扩展新的接收器以支持新的协议和格式是非常容易的.

### 查询协议

查询协议遵循 GraphQL 语法, 提供了数据查询能力, 这都取决于你要分析的指标.

## 支持的中间件,以及框架,第三方依赖

- HTTP Server 

  - [Tomcat](https://github.com/apache/tomcat) 7
  - [Tomcat](https://github.com/apache/tomcat) 8
  - [Tomcat](https://github.com/apache/tomcat) 9
  - [Spring Boot](https://github.com/spring-projects/spring-boot) Web 4.x
  - Spring MVC 3.x, 4.x 5.x with servlet 3.x
  - [Nutz Web Framework](https://github.com/nutzam/nutz)  1.x
  - [Struts2 MVC](http://struts.apache.org/)  2.3.x -> 2.5.x
  - [Resin](http://www.caucho.com/resin-4.0/) 3 (可选¹)
  - [Resin](http://www.caucho.com/resin-4.0/) 4 (可选¹)
  - [Jetty Server](http://www.eclipse.org/jetty/) 9
  - [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) 5.x
  - [Undertow](http://undertow.io/)  2.0.0.Final -> 2.0.13.Final
  - [RESTEasy](https://resteasy.github.io/)  3.1.0.Final -> 3.7.0.Final
  - [Play Framework](https://www.playframework.com/) 2.6.x -> 2.8.x
  - [Light4J Microservices Framework](https://doc.networknt.com/) 1.6.x -> 2.x
  - [Netty SocketIO](https://github.com/mrniko/netty-socketio) 1.x

- HTTP Client 

  - [Feign](https://github.com/OpenFeign/feign) 9.x
  - [Netflix Spring Cloud Feign](https://github.com/spring-cloud/spring-cloud-netflix/tree/master/spring-cloud-starter-feign) 1.1.x, 1.2.x, 1.3.x
  - [Okhttp](https://github.com/square/okhttp) 3.x
  - [Apache httpcomponent HttpClient](http://hc.apache.org/) 4.2, 4.3
  - [Spring RestTemplete](https://github.com/spring-projects/spring-framework) 4.x
  - [Jetty Client](http://www.eclipse.org/jetty/) 9
  - [Apache httpcomponent AsyncClient](https://hc.apache.org/httpcomponents-asyncclient-dev/) 4.x

- HTTP Gateway 

  - [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) 2.1.x.RELEASE (可选²)

- JDBC 

  - Mysql Driver 5.x, 6.x, 8.x
  - Oracle Driver (可选¹)
  - H2 Driver 1.3.x -> 1.4.x
  - [Sharding-JDBC](https://github.com/shardingjdbc/sharding-jdbc) 1.5.x
  - [ShardingSphere](https://github.com/apache/incubator-shardingsphere) 3.0.0
  - [ShardingSphere](https://github.com/apache/incubator-shardingsphere) 3.0.0, 4.0.0-RC1
  - PostgreSQL Driver 8.x, 9.x, 42.x
  - Mariadb Driver 2.x, 1.8

- RPC框架 

  - [Dubbo](https://github.com/alibaba/dubbo) 2.5.4 -> 2.6.0
  - [Dubbox](https://github.com/dangdangdotcom/dubbox) 2.8.4
  - [Apache Dubbo](https://github.com/apache/dubbo) 2.7.0
  - [Motan](https://github.com/weibocom/motan) 0.2.x -> 1.1.0
  - [gRPC](https://github.com/grpc/grpc-java) 1.x
  - [Apache ServiceComb Java Chassis](https://github.com/apache/servicecomb-java-chassis) 0.1 -> 0.5,1.0.x
  - [SOFARPC](https://github.com/alipay/sofa-rpc) 5.4.0
  - [Armeria](https://github.com/line/armeria) 0.63.0 -> 0.98.0
  - [Apache Avro](http://avro.apache.org) 1.7.0 - 1.8.x
  - [Finagle](https://github.com/twitter/finagle) 6.25.0 -> 20.1.0

- MQ 

  - [RocketMQ](https://github.com/apache/rocketmq) 4.x
  - [Kafka](http://kafka.apache.org) 0.11.0.0 -> 1.0
  - [ActiveMQ](https://github.com/apache/activemq) 5.x
  - [RabbitMQ](https://www.rabbitmq.com/) 5.x
  - [Pulsar](http://pulsar.apache.org) 2.2.x -> 2.4.x

- NoSQL 

  - Redis 

    - [Jedis](https://github.com/xetorthio/jedis) 2.x
    - [Redisson](https://github.com/redisson/redisson) Easy Java Redis client 3.5.2+
    - [Lettuce](https://github.com/lettuce-io/lettuce-core) 5.x (可选²)

  - [MongoDB Java Driver](https://github.com/mongodb/mongo-java-driver) 2.13-2.14,3.3+

  - Memcached Client 

    - [Spymemcached](https://github.com/couchbase/spymemcached) 2.x
    - [Xmemcached](https://github.com/killme2008/xmemcached) 2.x

  - Elasticsearch

    - [transport-client](https://github.com/elastic/elasticsearch/tree/master/client/transport) 5.2.x-5.6.x
    - [transport-client](https://github.com/elastic/elasticsearch/tree/v6.7.1/client/transport) 6.7.1-6.8.4
    - [rest-high-level-client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/index.html) 6.7.1-6.8.4

  - SolrJ

     7.0.0-7.7.1 

    - [SolrJ](https://github.com/apache/lucene-solr/tree/master/solr/solrj) 7.x

  - Cassandra

     3.x 

    - [cassandra-java-driver](https://github.com/datastax/java-driver) 3.7.0-3.7.2

- 服务发现 

  - [Netflix Eureka](https://github.com/Netflix/eureka)

- 分布式协调 

  - [Zookeeper](https://github.com/apache/zookeeper) 3.4.x (可选²，并且3.4.4除外)

- Spring生态系统 

  - Spring Bean annotations(@Bean, @Service, @Component, @Repository) 3.x and 4.x (可选²)
  - Spring Core Async SuccessCallback/FailureCallback/ListenableFutureCallback 4.x

- [Hystrix: 分布式系统延时和故障容错](https://github.com/Netflix/Hystrix) 1.4.20 -> 1.5.12

- 调度器 

  - [Elastic Job](https://github.com/elasticjob/elastic-job) 2.x

- OpenTracing社区支持

- [Canal: 阿里巴巴的基于MySQL binlog的增量订阅与消费组件](https://github.com/alibaba/canal) 1.0.25 -> 1.1.2

- JSON 

  - [GSON](https://github.com/google/gson) 2.8.x (可选²)

- Vert.x 生态 

  - Vert.x Eventbus 3.2+
  - Vert.x Web 3.x

- 线程调度框架 

  - [Spring @Async](https://github.com/spring-projects/spring-framework) 4.x and 5.x

- 缓存 

  - [Ehcache](https://www.ehcache.org/) 2.x

- Kotlin 

  - [Coroutine](https://kotlinlang.org/docs/reference/coroutines-overview.html) 1.0.1 -> 1.3.x (Optional²)

由于许可的限制或不兼容，这些插件发布在第三方仓库中。可以到[SkyAPM java插件扩展仓库](https://github.com/SkyAPM/java-plugin-extensions)获得这些插件。

²根据我们的经验，这些插件会对性能有影响，或者，必须在某些情况下才使用，所以只发布在`/optional-plugins`文件夹下，想要使用的话，复制到`/plugins`文件夹下。

Java Agent是插件化、可插拔的。Skywalking的插件分为三种：

- 引导插件：在agent的 `bootstrap-plugins` 目录下
- 内置插件：在agent的 `plugins` 目录下
- 可选插件：在agent的 `optional-plugins` 目录下

插件说明地址:  https://github.com/SkyAPM/document-cn-translation-of-skywalking/blob/master/docs/zh/8.0.0/setup/service-agent/java-agent/README.md#optional-plugins



插件,可选

其中，-javaagent参数指定skywalking-agent.jar的全路径；skywalking.agent.service_name指定微服务实例名称，用于展示在SkyWalking的web监控界面

3）如果需要跟踪微服务接口内部的调用关系，并采集性能数据，则按需要在各方法上增加@trace注解

引入apm-toolkit-trace包，用于AOP方式采集方法trace信息

         <dependency>
            <groupId>org.apache.skywalking</groupId>
            <artifactId>apm-toolkit-trace</artifactId>
            <version>${skywalking.version}</version>
         </dependency>
 要跟踪的方法上增加@trace注解 

 <dependency>
             <groupId>org.apache.skywalking</groupId>
             <artifactId>apm-toolkit-logback-1.x</artifactId>
             <version>${skywalking.version}</version>
         </dependency>）

日志中增加TraceId，方便问题跟踪

TraceId是SkyWalking框架生成的唯一键，可用于在SkyWalking的web监控界面按此ID快速定位各种接口的调用关系

## **Skywalking 服务告警:

```
 先来看一下告警的规则配置。在alarm-settings.xml中可以配置告警规则，告警规则支持自定义。

```

##    ![img](https://img2018.cnblogs.com/blog/285763/201911/285763-20191121174318199-1915223117.png) 



 一份告警配置由以下几部分组成：

service_resp_time_rule：告警规则名称 ***_rule （规则名称可以自定义但是必须以’_rule’结尾
indicator-name：指标数据名称： 定义参见http://t.cn/EGhfbmd
op: 操作符： > , < , = 【当然你可以自己扩展开发其他的操作符】
threshold：目标值：指标数据的目标数据 如sample中的1000就是服务响应时间，配合上操作符就是大于1000ms的服务响应
period: 告警检查周期：多久检查一次当前的指标数据是否符合告警规则
counts: 达到告警阈值的次数
silence-period：忽略相同告警信息的周期
message：告警信息
webhooks：服务告警通知服务地址

Skywalking通过HttpClient的方式远程调用在配置项webhooks中定义的告警通知服务地址。 

# 阿里Edas监控:

首先,

这是edas才有的监控,支持docker,k8s,ecs集群.

日志管理.可以在线查看日志信息.

应用总览:

应用监控.:概览分析,请求量平均分析时间,实时实例,gc,慢sql分析,支持时间段查看提供ui画面,系统信息

应用详情:概览,jvm监控,主机监控,sql分析,异常分析,接口快照

接口调用:概览,sql分析,异常分析,链路上游,链路下游,接口快照

高级监控:需要开通.

# javaagent对软件的影响:

JavaAgent 能给我们带来什么？

1. 自己实现一个 JavaAgent xxxxxx
2. 基于 JavaAgent 的 spring-loaded 实现 jar 包的热更新，也就是在不重启服务器的情况下，使我们某个更新的 jar 被重新加载。

java底层Instrumentation应用,在jvm运行的时候会第一次触发此拦截,执行方法,同包下面还有其他的同类,意义相同,很多的第三方库都是封装的以上的同包下的代码.影响程度看apa

 JDK5中只能通过命令行参数在启动JVM时指定javaagent参数来设置代理类，而JDK6中已经不仅限于在启动JVM时通过配置参数来设置代理类，JDK6中通过 Java Tool API 中的 attach 方式，我们也可以很方便地在运行过程中动态地设置加载代理类，以达到  instrumentation 的目的。 
Instrumentation 的最大作用，就是类定义动态改变和操作。 

 简单的一个例子，计算某个方法执行需要的时间，不修改源代码的方式，使用Instrumentation 代理来实现这个功能，给力的说，这种方式相当于在JVM级别做了AOP支持，这样我们可以在不修改应用程序的基础上就做到了AOP，是不是显得略吊。 



​               





 JDK5中只能通过命令行参数在启动JVM时指定javaagent参数来设置代理类，而JDK6中已经不仅限于在启动JVM时通过配置参数来设置代理类，JDK6中通过 Java Tool API 中的 attach 方式 ,是不是并不用            











# Skywalking集群搭建具体步骤 :

第一,     先搭建单机skywalking环境,ui和oap都已经启动成功.

es持久化,忽略,.注册中心选用nacos.

## /config/application.yml：

```
#####该配置代表单机模式，需要注释掉
#standalone 
#  standalone:   
  # Please check your ZooKeeper is 3.5+, However, it is also compatible with ZooKeeper 3.4.x. Replace the ZooKeeper 3.5+
  # library the oap-libs folder with your ZooKeeper 3.4.x library.
#  zookeeper:
#    nameSpace: ${SW_NAMESPACE:""}
#    hostPort: ${SW_CLUSTER_ZK_HOST_PORT:localhost:2181}
#    #Retry Policy
#    baseSleepTimeMs: ${SW_CLUSTER_ZK_SLEEP_TIME:1000} # initial amount of time to wait between retries
#    maxRetries: ${SW_CLUSTER_ZK_MAX_RETRIES:3} # max number of times to retry
#    # Enable ACL
#    enableACL: ${SW_ZK_ENABLE_ACL:false} # disable ACL in default
#    schema: ${SW_ZK_SCHEMA:digest} # only support digest schema
#    expression: ${SW_ZK_EXPRESSION:skywalking:skywalking}
#  kubernetes:
#    watchTimeoutSeconds: ${SW_CLUSTER_K8S_WATCH_TIMEOUT:60}
#    namespace: ${SW_CLUSTER_K8S_NAMESPACE:default}
#    labelSelector: ${SW_CLUSTER_K8S_LABEL:app=collector,release=skywalking}
#    uidEnvName: ${SW_CLUSTER_K8S_UID:SKYWALKING_COLLECTOR_UID}
#  consul:
#    serviceName: ${SW_SERVICE_NAME:"SkyWalking_OAP_Cluster"}
#     Consul cluster nodes, example: 10.0.0.1:8500,10.0.0.2:8500,10.0.0.3:8500
#    hostPort: ${SW_CLUSTER_CONSUL_HOST_PORT:localhost:8500}
#####打开nacos集群设置
  nacos:
#####名字可以自己起
    serviceName: ${SW_SERVICE_NAME:"SkyWalking_OAP_Cluster"}
#####此处也可以设置域名加端口，如：test.nacos.com:80
    hostPort: ${SW_CLUSTER_NACOS_HOST_PORT:192.168.1.1:8843,192.168.1.2:8843}
  # Nacos Configuration namespace
#####此处又命名空间就写真实命名空间，没有就写public
    namespace: 'public'
```

 **core OAP的核心配置，配置项都有英文解释，这里不详述** 

```
core:
  default:
    # Mixed: Receive agent data, Level 1 aggregate, Level 2 aggregate
    # Receiver: Receive agent data, Level 1 aggregate
    # Aggregator: Level 2 aggregate
    role: ${SW_CORE_ROLE:Mixed} # Mixed/Receiver/Aggregator
    restHost: ${SW_CORE_REST_HOST:0.0.0.0}
    restPort: ${SW_CORE_REST_PORT:12800}
    restContextPath: ${SW_CORE_REST_CONTEXT_PATH:/}
    #####只需修改这个，其他默认即可
    gRPCHost: ${SW_CORE_GRPC_HOST:192.168.1.1，192.168.1.2}
    gRPCPort: ${SW_CORE_GRPC_PORT:11800}
    downsampling:
      - Hour
      - Day
      - Month
    # Set a timeout on metrics data. After the timeout has expired, the metrics data will automatically be deleted.
    enableDataKeeperExecutor: ${SW_CORE_ENABLE_DATA_KEEPER_EXECUTOR:true} # Turn it off then automatically metrics data delete will be close.
    dataKeeperExecutePeriod: ${SW_CORE_DATA_KEEPER_EXECUTE_PERIOD:5} # How often the data keeper executor runs periodically, unit is minute
    recordDataTTL: ${SW_CORE_RECORD_DATA_TTL:90} # Unit is minute
    minuteMetricsDataTTL: ${SW_CORE_MINUTE_METRIC_DATA_TTL:90} # Unit is minute
    hourMetricsDataTTL: ${SW_CORE_HOUR_METRIC_DATA_TTL:36} # Unit is hour
    dayMetricsDataTTL: ${SW_CORE_DAY_METRIC_DATA_TTL:45} # Unit is day
    monthMetricsDataTTL: ${SW_CORE_MONTH_METRIC_DATA_TTL:18} # Unit is month
    # Cache metric data for 1 minute to reduce database queries, and if the OAP cluster changes within that minute,
    # the metrics may not be accurate within that minute.
    enableDatabaseSession: ${SW_CORE_ENABLE_DATABASE_SESSION:true}
```

 **configuration 配置中心** 

```
configuration:
#####使用配置中心的话就把这个注释掉
#  none:
#  apollo:
#    apolloMeta: http://106.12.25.204:8080
#    apolloCluster: default
#    # apolloEnv: # defaults to null
#    appId: skywalking
#    period: 5
  nacos:
    # Nacos Server Host
    #####没有域名的话，也可以ip+port
    serverAddr: test.nacos.com:80
    # Nacos Server Port
    port: 80
    # Nacos Configuration Group
    group: 'skywalking'
    # Nacos Configuration namespace
    namespace: '01e2ec68-7d15-4ae3-9422-0418f657876d'
    # Unit seconds, sync period. Default fetch every 60 seconds.
    period : 10
    # the name of current cluster, set the name if you want to upstream system known.
    clusterName: "default"
#  zookeeper:
#    period : 60 # Unit seconds, sync period. Default fetch every 60 seconds.
#    nameSpace: /default
#    hostPort: localhost:2181
#    #Retry Policy
#    baseSleepTimeMs: 1000 # initial amount of time to wait between retries
#    maxRetries: 3 # max number of times to retry
#  etcd:
#    period : 60 # Unit seconds, sync period. Default fetch every 60 seconds.
#    group :  'skywalking'
#    serverAddr: localhost:2379
#    clusterName: "default"
#  consul:
#    # Consul host and ports, separated by comma, e.g. 1.2.3.4:8500,2.3.4.5:8500
#    hostAndPorts: ${consul.address}
#    # Sync period in seconds. Defaults to 60 seconds.
#    period: 1s
```

## /agent/config/agent.conf:

```
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 
# The agent namespace
# agent.namespace=${SW_AGENT_NAMESPACE:default-namespace}
 
# The service name in UI
#####名字可以自己起，建议不同服务之间名字区分开，好区分
agent.service_name=${SW_AGENT_NAME:my-application118}
 
# The number of sampled traces per 3 seconds
# Negative number means sample traces as many as possible, most likely 100%
# agent.sample_n_per_3_secs=${SW_AGENT_SAMPLE:-1}
 
# Authentication active is based on backend setting, see application.yml for more details.
# agent.authentication = ${SW_AGENT_AUTHENTICATION:xxxx}
 
# The max amount of spans in a single segment.
# Through this config item, skywalking keep your application memory cost estimated.
# agent.span_limit_per_segment=${SW_AGENT_SPAN_LIMIT:300}
 
# Ignore the segments if their operation names end with these suffix.
# agent.ignore_suffix=${SW_AGENT_IGNORE_SUFFIX:.jpg,.jpeg,.js,.css,.png,.bmp,.gif,.ico,.mp3,.mp4,.html,.svg}
 
# If true, skywalking agent will save all instrumented classes files in `/debugging` folder.
# Skywalking team may ask for these files in order to resolve compatible problem.
# agent.is_open_debugging_class = ${SW_AGENT_OPEN_DEBUG:true}
 
# The operationName max length
# agent.operation_name_threshold=${SW_AGENT_OPERATION_NAME_THRESHOLD:500}
 
# Backend service addresses.
#####配置gRPC的地址
collector.backend_service=${SW_AGENT_COLLECTOR_BACKEND_SERVICES:192.168.1.1:11800,192.168.1.2:11800}
 
# Logging file_name
logging.file_name=${SW_LOGGING_FILE_NAME:skywalking-api.log}
 
# Logging level
logging.level=${SW_LOGGING_LEVEL:DEBUG}
 
# Logging dir
# logging.dir=${SW_LOGGING_DIR:""}
 
# Logging max_file_size, default: 300 * 1024 * 1024 = 314572800
# logging.max_file_size=${SW_LOGGING_MAX_FILE_SIZE:314572800}
 
# The max history log files. When rollover happened, if log files exceed this number,
# then the oldest file will be delete. Negative or zero means off, by default.
# logging.max_history_files=${SW_LOGGING_MAX_HISTORY_FILES:-1}
 
# mysql plugin configuration
#####这一项可以选择开启，在链路追踪时，可以看到sql的参数，我的好像没起作用不知道为啥
plugin.mysql.trace_sql_parameters=${SW_MYSQL_TRACE_SQL_PARAMETERS:true}
```

## /webapp/webapp.yml

```
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 
server:
#####默认是8080端口，可以改，支持修改端口，但是不支持增加项目路径
  port: 8080
 
collector:
  path: /graphql
  ribbon:
    ReadTimeout: 10000
    # Point to all backend's restHost:restPort, split by ,
    listOfServers: 192.168.1.1:12800,192.168.1.2:12800
```

 sh startup.sh 

**三、访问UI**

192.168.1.1:8080或者192.168.1.2:8080 两个地址都能访问到UI，小伙伴们可以做个nginx反向代理，给配置个域名



阿里ip

123.57.128.134

mokeip:

101.133.210.57

moke2ip:

101.132.165.77

## 个人实践实操记录:

环境skywalking6.5版本,jdk8.0.2台1核512g阿里云服务器.nacos服务器一台.

先把tar包上传到/home下,注意权限问题.解压.

想修改webapp下的ui端口,默认时8080,统一修改成18080..两台都要修改

你要做集群就要用到注册中心,6.5支持nacos,但是8.0以后不支持.我们用的时nacos,所以支持的不是很好,但是也能用.

然后修改config下的applicatom.yml参数.

具体如下:

在搭建过程中多次尝试并查看日志解决问题.官网并没有集群搭建具体步骤.

cluster:下的standalone注释掉.打开nacos注释,注册中心可选,官网推荐zookeeper.

standalone:意思时单机运行.集群要把这个注释掉,默认单机运行.

core下面的参数不要动,host就是要0.0.0.0,之前看其他的说要改,其实并不用改,改的话会报zull错误.ip端口错误.

storage下的时存储机制,官网推荐用es,我们用的默认的,只能有一个存在,es和h2,mysql看情况设置.

configuration下也是要进行集群设置,打开nacos注释,官网推荐zookeeper,  

关键点: 

serverAddr: 123.57.128.134:8850

port: 8850

下面的不要动了

然后修改webapp下的webapp.yml

collector设置listOfServers,如果是集群的话需要设置多个collectorip地址,配置的数据是

在core下的grpchost和resthost

示例如下

 listOfServers: 123.57.128.134:12800,101.132.165.77:12800

启动skywalking,注意启动参数的设置.以上设置,两台服务相同,都要进行设置

修改/agent/config/agent.conf:

#####配置gRPC的地址
collector.backend_service=${SW_AGENT_COLLECTOR_BACKEND_SERVICES:192.168.1.1:11800,192.168.1.2:11800}

配置grpc的ip和端口.也可以不加.在启动是也可以加,不过还是推荐改一下.

在edas里部署探针jar包是需要权限问题的,需要权限.

这个等会再写





问题:skywalking集群的通信问题?

集群是通过nacos做服务发现还是不通过,问题是什么?







