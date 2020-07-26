#镜像出处
FROM java:8
#维护者信息
MAINTAINER zzy
#镜像操作指令
#在容器的根目录中创建spring-cloud文件夹
RUN mkdir -p /spring-cloud
#将本地项目jar包拷贝到Docker容器中的位置
COPY ./springcloud-eureka.jar /spring-cloud/springcloud-eureka.jar
#将本地的可执行文件拷贝到Docker容器中的根目录下
COPY ./app-entrypoint.sh /
#给app-entrypoint.sh赋予可执行权限
RUN chmod +x /app-entrypoint.sh
#对外暴漏的端口号
EXPOSE 6868
#开机启动
ENTRYPOINT ["/app-entrypoint.sh"]