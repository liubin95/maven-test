FROM openjdk:8
COPY ./target/maven-test-1.0-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-cp","maven-test-1.0-SNAPSHOT.jar","redis.MainRedis"]