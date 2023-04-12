# 基础镜像，当前新镜像是基于哪个镜像的，指定一个已经存在的镜像作为模板，第一条必须是from
FROM java:8
# 镜像维护者的姓名和邮箱地址
MAINTAINER  njw<njw@163.com>
# VOLUME 指定临时文件目录为/tmp，在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp(比如日志文件的挂载)
VOLUME /home/ruoyi/logs
# 将jar包添加到容器中并更名为zzyy_docker.jar  将宿主机目录下的文件添加进镜像且它会自动处理URL和解压tar压缩包
ADD docker_test-1.0-SNAPSHOT.jar zzyy_docker.jar
# 容器构建过程中，执行命令并创建新的镜像层
# 注意：多个 RUN 命令时，最好通过 && 进行合并操作，要不然，在 docker 构建时，每个 RUN 会生成一层，造成容器臃肿变大
# 运行jar包,shell中 bash -c 执行命令或者可执行文件
RUN bash -c 'touch /zzyy_docker.jar'
# 指定一个容器启动时要运行的命令！和 CMD 一样
ENTRYPOINT ["java","-jar","/zzyy_docker.jar"]
#当前容器对外暴露出的端口
EXPOSE 6001

 #FROM 		# 基础镜像，当前新镜像是基于哪个镜像的，后续的指令运行于此基础镜像所提供的运行环境；
 #基准镜像可以是任何可用镜像文件，默认情况下，docker build会在docker主机上查找指定的镜像文件，在其不存在时，则会自动从Docker的公共库pull镜像下来。
 #如果找不到指定的镜像文件，docker build会返回一个错误信息；语法格式:FROM <镜像名>:<标签>或 FROM <repository>@<digest>

 #MAINTAINER 	# 镜像维护者的姓名混合邮箱地址,dockerfile并不限制MAINTAINER指令可在出现的位置，但推荐将其放置于FROM指令之后


 #RUN 		# 容器构建时需要运行的命令!语法格式:RUN <command>或RUN ["<executable>", "<param1>", "<param2>"]
 #EXPOSE 		# 当前容器对外保留出的端口
 #WORKDIR 	# 指定在创建容器后，终端默认登录的进来工作目录，一个落脚点,类似于cd命令：cd /usr/local/myapp
 #ENV 		# 用来在构建镜像过程中设置环境变量！语法格式：ENV <key> <value>或 ENV <key>=<value> 示例:ENV DOC_ROOT=/data/web/html/
 #ADD 		# 将宿主机目录下的文件拷贝进镜像且 ADD命令会自动处理 URL 和解压 tar 压缩包!语法格式:ADD <src>  <dest>或ADD["<src>","<dest>"]
 #COPY 		# 类似 ADD，拷贝文件和目录到镜像中！语法格式:COPY<src源>  <dest目标>或 COPY["<src>","<dest>"]
 #VOLUME 		# 容器数据卷，用于数据保存和持久化工作!语法格式:VOLUME <mountpoint>或 VOLUME["<mountpoint>"]   (注意没有<>符号)
 #CMD 		# 指定一个容器启动时要运行的命令，dockerFile 中可以有多个 CMD 指令，但只有最后一个生效！
 #ENTRYPOINT 	# 指定一个容器启动时要运行的命令！和 CMD 一样
 #ONBUILD 	# 当构建一个被继承的 DockerFile 时运行命令，父镜像在被子镜像继承后，父镜像的 ONBUILD 被触发