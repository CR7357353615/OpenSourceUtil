# springboot docker
---
## pom文件
<packaging>war</packaging> 改为 <packaging>jar</packaging>

```xml
<groupId>org.springframework</groupId>
    <artifactId>gs-spring-boot-docker</artifactId>
    <version>0.1.0</version>
    <packaging>jar</packaging>
    <name>Spring Boot Docker</name>
    <description>Getting started with Spring Boot and Docker</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.3.RELEASE</version>
        <relativePath />
    </parent>

    <properties>
        <docker.image.prefix>springio</docker.image.prefix>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!-- tag::plugin[] -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <repository>${docker.image.prefix}/${project.artifactId}</repository>
                    <buildArgs>
                        <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
                    </buildArgs>
                </configuration>
            </plugin>
            <!-- end::plugin[] -->

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>unpack</id>
                        <phase>package</phase>
                        <goals>
                            <goal>unpack</goal>
                        </goals>
                        <configuration>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>${project.groupId}</groupId>
                                    <artifactId>${project.artifactId}</artifactId>
                                    <version>${project.version}</version>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

## Dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

## package
mvn clean package -Dmaven.test.skip=true && java -jar target/xx.jar

## build
mvn install dockerfile:build

## 查看image
docker image ls

## run
docker run -p 8080:8080 -t springio/gs-spring-boot-docker

## 即可访问

## 查看当前docker进程
docker ps

## 停止
docker stop XX

