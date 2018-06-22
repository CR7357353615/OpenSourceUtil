# Jenkins
---
## 下载
可以直接去[官网](https://jenkins.io/download/)下载，mac版直接安装，自动会配置好，位置在/Users/Shared/Jenkins。

安装好以后，默认启动端口是8080.可以创建一个新的用户。

## 全局配置
参考[文章](https://blog.csdn.net/xlgen157387/article/details/50353317)

## 配置构建
代码在git上的，可以在【源码管理处】选择git
填写git地址，访问方式可以是用户名密码或是ssh

## 发布到远程机器
参考[文章](http://blog.51cto.com/xiong51/2091739)

## 需要注意的地方
1.在配置全局maven时，maven库地址要正确，mac不要写~/ 要用/Users/xx

2.在下载依赖包时，要指向本地maven库，方法是在打包语句中加上 -Dmaven.repo.local=/Users/xx/.m2/repository

3.Jenkins会把依赖包下一份到/Users/Shared/Jenkins/.m2/ 中，所以遇到不能读写的情况，需要用 chmod 命令改变文件和文件夹权限 用-R命令，将改变文件夹下的所有文件夹和文件。

## 参数化构建
在配置中选择参数化构建过程，填写参数名，默认值。需要用到的时候，用$参数名代替。

## 传递依赖问题
如果项目pom中依赖的jarA包依赖另一个jarB包，如果不能依赖，那么需要把jarA包的<dependency>放入maven库的的对应包的pom中即可。

## 其他问题
shell脚本权限问题：参考[文章](https://blog.csdn.net/SkyeBeFreeman/article/details/78116113)
在Jenkins宿主服务器上运行如下命令 
$ sudo visudo
在文件的末尾加上一行 
jenkins ALL=(ALL) NOPASSWD: ALL
