# maven第三方jar包导入
---
有些jar包maven中没有需要打包
指令是：
mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
packaging是jar或war

参考网址：https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html
