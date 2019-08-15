# springboot-docker
springboot集成maven-docker插件，自动构建镜像
# pom 配置maven-docker插件
`<docker.image.prefix>tz</docker.image.prefix>`
### plugin
`
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
`
### 打包名称
`        <finalName>tz-docker-demo</finalName>`
# Dockerfile文件
`FROM frolvlad/alpine-oraclejdk8:slim
 VOLUME /tmp
 ADD tz-docker-demo.jar app.jar
 EXPOSE 8761
 ENTRYPOINT ["java","-jar","/app.jar"]`
 # 构建
 `mvn clean package docker:build`