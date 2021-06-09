# MQTT-demo


主要分两部分（均使用 `MQTT3` 协议）：

1. `publisher`/`subscriber`模块： 一个简单的基于 `hivemq` 的发布者/订阅者客户端。
2. `my-client`模块： 基于Socket自己手动序列化/反序列化`MQTT Packet`实现的发布者/订阅者客户端。

## 项目打包

项目根目录下执行:
```shell
mvn package
```

生成的 jar 包位于：
* `ublisher/target/Publisher-xxx-shaded.jar` 
* `subscriber/target/Subscriber-xxx-shaded.jar`
* `my-client/target/my-client-publisher-1.0-SNAPSHOT.jar`
* `my-client/target/my-client-subscriber-1.0-SNAPSHOT.jar`

## 项目运行

**基于hivemq的publisher/subscriber**
```shell
java -jar Publisher-xxx-shaded.jar [server_host] [server_port] [topic]
java -jar Subscriber-xxx-shaded.jar [server_host] [server_port] [topic]
```

**自己实现的简单的publisher/subscriber**
```shell
java -jar my-client-publisher-1.0-SNAPSHOT.jar [server_host] [server_port] [topic]
java -jar my-client-subscriber-1.0-SNAPSHOT.jar [server_host] [server_port] [topic]
```

## 操作方式

**Publisher**

* 在 `>>> ` 后键入消息即可。
* 输入 `exit` 即可退出。

**Subscriber**

* 会打印收到的消息
* `Ctrl + C` 即可退出。

## 特别的

* 消息均设置了 `retain = True`。