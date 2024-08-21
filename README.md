# monday-log-server
Java写的HTTP日志服务器

## 构建

    mvn package

## 运行

    java -Dlogback.configurationFile=src/main/resources/logback.xml  -Dspring.config.location=src/main/resources/application.yml -jar target/monday-log-server-1.0.0.jar
