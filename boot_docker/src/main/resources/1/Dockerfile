# 依赖的基础镜像
FROM openjdk:8-jdk-alpine

# 镜像的维护者和邮箱地址
MAINTAINER hweiyu<hwy_dod@163.com>

# 容器构建过程中，执行命令并创建新的镜像层
# 注意：多个 RUN 命令时，最好通过 && 进行合并操作，要不然，在 docker 构建时，每个 RUN 会生成一层，造成容器臃肿变大
RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
   echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories && \
   apk update && \
   apk add --no-cache gcc libc-dev make openssl-dev pcre-dev zlib-dev linux-headers curl && \

# 挂载数据卷
VOLUME /data/upload_files

# 切换到工作目录，同cd命令：cd /usr/local/myapp
WORKDIR /usr/local/myapp

# 将宿主目录中的文件拷贝到镜像中
# ADD 不仅能复制宿主主机中的文件，还能是URL指向的文件。而且如果指向的文件是tar压缩包，在复制到容器后，会进行自动解压
# ADD ./spring-boot-demo.jar app.jar
# COPY 仅能复制宿主主机中的文件
COPY ./spring-boot-demo.jar app.jar

# 定义系统环境变量
ENV profile prod
ENV timezone Asia/Shanghai

# 容器启动时，需要运行的命令，shell格式：
# ENTRYPOINT java -jar -Dspring.profiles.active=$profile -Duser.timezone=$timezone app.jar
CMD java -jar -Dspring.profiles.active=$profile -Duser.timezone=$timezone  app.jar

# 对外暴露的端口号
EXPOSE 80 443
