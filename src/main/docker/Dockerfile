FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD tz-docker-demo.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
