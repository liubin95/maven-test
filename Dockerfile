FROM openjdk:8

# settings timezone
ENV TZ Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./java-native/target/java-native-1.0.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-cp","java-native-1.0.jar","DateMain"]