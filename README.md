# springboot-docker
springboot集成maven-docker插件，自动构建镜像
# pom 配置maven-docker插件
```<docker.image.prefix>tz</docker.image.prefix>
```
### plugin
```
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
```       
 <finalName>tz-docker-demo</finalName>
```
# Dockerfile文件
```FROM frolvlad/alpine-oraclejdk8:slim
 VOLUME /tmp
 ADD tz-docker-demo.jar app.jar
 EXPOSE 8081
 ENTRYPOINT ["java","-jar","/app.jar"]
```
 # 构建
 ```
 mvn clean package docker:build -Dmaven.test.skip=true
 ```
 #连接mysql容器
 ```
spring:
    datasource:
        # tz_mysql 是我的mysql容器名称
      url: jdbc:mysql://tz_mysql:3306/mystudy 
      driver-class-name:  com.mysql.cj.jdbc.Driver
      username: root
      password: root
```
 #运行容器
 ```docker run --rm -p 8081:8081 --name demo  --link tz_mysql:tz_mysql tz-docker-demo:0.0.1```
 --link 连接的容器名：别名（和上方配置mysql的地方一致）
 
 
###  链接Skywalking 监控
* Dockerfile
```
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD tz-docker-demo.jar app.jar
ADD agent agent
EXPOSE 8081
ENTRYPOINT ["java","-javaagent:/agent/skywalking-agent.jar","-DSW_AGENT_NAME=\"springboot-docker\"","-DSW_AGENT_NAMESPACE=\"demo\"","-jar","/app.jar"]
```
* src/main/docker/agent 这个文件夹也是重要的
* src/main/docker/agent 这个文件夹也是重要的
* src/main/docker/agent 这个文件夹也是重要的
* agent/config/agent.config skywalking配置文件在这
