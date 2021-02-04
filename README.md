# springboot-docker

* springboot集成maven-docker插件，自动构建镜像
* 继承SkyWalking
* 集成Arthas

## Springboot+Docker
### pom 配置maven-docker插件
```text
<docker.image.prefix>tz</docker.image.prefix>
```
### plugin
```text
                <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.13</version>
                <configuration>
                     <!-- 镜像名称-->
                    <imageName>${docker.image.prefix}/docker-demo:latest</imageName>
                    <!--  dockerfile地址 -->
                    <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
```
### 打包名称
```text   
 <finalName>tz-docker-demo</finalName>
```
### Dockerfile文件
```FROM openjdk:8-jdk-alpine
 ADD tz-docker-demo.jar app.jar
 EXPOSE 8081
 ENTRYPOINT ["java","-jar","/app.jar"]
```
### 连接mysql容器 

 ```yml
# tz_mysql 为 我本地mysql 容器的名称
spring:
    datasource:
        # tz_mysql 是我的mysql容器名称
      url: jdbc:mysql://tz_mysql:3306/mystudy 
      driver-class-name:  com.mysql.cj.jdbc.Driver
      username: root
      password: root
 ```

### 构建

 ```text
 mvn clean package docker:build -Dmaven.test.skip=true
 ```

### 运行容器
 ```text
 docker run --rm -p 8081:8081 --name demo  --link tz_mysql:tz_mysql tz-docker-demo:0.0.1```
 --link 连接的容器名：别名（和上方配置mysql的地方一致）
 ```

### 浏览器访问

```text
http://localhost:8081/boot/
```

##  链接Skywalking 监控
###  Dockerfile
```text
FROM openjdk:8-jdk-alpine
ADD tz-docker-demo.jar app.jar
ADD agent agent
EXPOSE 8081
ENTRYPOINT ["java","-javaagent:/agent/skywalking-agent.jar","-DSW_AGENT_NAME=\"springboot-docker\"","-DSW_AGENT_NAMESPACE=\"demo\"","-jar","/app.jar"]
```
* src/main/docker/agent 这个文件夹也是重要的
* src/main/docker/agent 这个文件夹也是重要的
* src/main/docker/agent 这个文件夹也是重要的
* agent/config/agent.config skywalking配置文件在这


## arthas
 ### 配置dockerfile
- 需要注意的地方 
启动命令重定向了一个启动脚本，这是因为在docker启动程序中，java的程序pid=1无法被使用，也就无法使用jstack命令，arthas也无法使用
使用重定向之后就可以获取到程序运行的pid了，然后愉快的执行对应的命令

 ```text
FROM openjdk:8-jdk-alpine
#基础镜像基于openjdk，利用alpine 下面的
ENV JAVA_OPTS="-server -Xms512m -Xmx512m" LOGGING_LEVEL="INFO"
#编译时变量无法在运行时用，此处做一次转换
ENV TARGET_JAR="tz-docker-demo.jar"
#将编译好的工程jar包copy到镜像容器中
COPY ${TARGET_JAR} /usr/src/${TARGET_JAR}
ENV OPTS=${JAVA_OPTS}" -Dfile.encoding=UTF8    -Duser.timezone=GMT+08"
WORKDIR /usr/src
RUN echo "java -jar \${OPTS} \${TARGET_JAR} --logging.level.root=\${LOGGING_LEVEL}" > start.sh \
             && chmod 777 start.sh
CMD ./start.sh

 ```
### 执行构建

```text
mvn clean package docker:build -Dmaven.test.skip=true
```
### 运行容器

```text
docker run --rm -p 8081:8081 --name demo  --link tz_mysql:tz_mysql tz-docker-demo:0.0.1
```
### arthas启动
```text
docker exec -it  demo /bin/sh -c "wget https://arthas.aliyun.com/arthas-boot.jar && java -jar arthas-boot.jar"
```

