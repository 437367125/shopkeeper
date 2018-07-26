## 后端源代码部署说明
用maven打包以后，将生成的war包上传到服务器，默认包名为shopkeeper-0.0.1-SNAPSHOT.war

用```java -jar shopkeeper-0.0.1-SNAPSHOT.war```命令可以启动服务，如果要让项目保持后台运行，则用```nohup 
 java -jar shopkeeper-0.0.1-SNAPSHOT.war &```命令来启动服务，默认端口号为9090，可以用```lsof -i:9090```查看这个进程的信息
