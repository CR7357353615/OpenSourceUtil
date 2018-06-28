# 重启tomcat 监听端口
---
```
#!/bin/sh

tomcat_path='/root/tomcat/apache-tomcat-9.0.7' #tomcat 完整路劲 或 最后级路劲文件家都可以

#获取 tomcat_path 所知tomcat 的进程ID


TomcatID=$(ps -ef |grep tomcat |grep -w $tomcat_path|grep -v 'grep'|awk '{print $2}')


echo $TomcatID $tomcat_path

if [ -n "$TomcatID" ];

       then
                echo "$TomcatID tomcat is running ................."
                kill -9 "$TomcatID"
        else
                echo "tomcat not start ============================="
fi
```
