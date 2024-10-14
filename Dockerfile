FROM maven:3.8-jdk-17-alpine AS build
ENV MY_HOME=/app

RUN mkdir -p $MY_HOME
WORKDIR $MY_HOME

RUN sed -i 's#mirrorId#nexus-aliyun#g' /usr/share/maven/conf/settings.xml
RUN sed -i 's#repositoryId#*#g' /usr/share/maven/conf/settings.xml
RUN sed -i 's#http://my.repository.com/repo/path#https://nexus.zyosoft.cn/repository/gradle-group#g' /usr/share/maven/conf/settings.xml

COPY . $MY_HOME
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-alpine
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories
RUN apk --no-cache update && apk --no-cache add curl ttf-dejavu fontconfig net-tools busybox-extras && apk --no-cache upgrade
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
RUN mkdir /app/config -p
EXPOSE 80
COPY --from=build /app/target/sanFrancisco-foodTruck.jar /app/

WORKDIR /app
CMD ["java", "-Dspring.config.additional-location=config/application.yaml", "-jar", "/app/sanFrancisco-foodTruck.jar"]
