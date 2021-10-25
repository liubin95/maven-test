FROM openjdk:8
# settings timezone
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY ./target/maven-test-1.0-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-cp","maven-test-1.0-SNAPSHOT.jar","date.DateMain"]