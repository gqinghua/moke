# vert.x+SpringBoot+MybatisPlus

项目旨在便于现有spring项目与vert.x整合，既能体验vert.x的高效又兼顾spring的泛用性，降低vert.x的学习和使用成本

项目使用了vert.x-web框架，在web层与传统spring的服务层之间加入了异步服务层。异步服务层使用vert.x-service-proxy服务代理框架实现，简化了EventBus的使用。

博客地址https://blog.csdn.net/u013615903/article/details/81776820