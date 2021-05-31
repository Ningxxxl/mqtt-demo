# MQTT-demo

一个简单的基于 `hivemq` 的发布者/订阅者demo。使用 `MQTT3` 协议。

## 项目打包

项目根目录下执行:
```shell
mvn package
```

生成的 jar 包位于：
* `Publisher/target/Publisher-xxx-shaded.jar` 
* `Subscriber/target/Subscriber-xxx-shaded.jar`

## 项目运行

**Publisher**
```shell
java -jar Publisher-xxx-shaded.jar [server_host] [server_port] [topic]
```

**Subscriber**
```shell
java -jar Subscriber-xxx-shaded.jar[server_host] [server_port] [topic]
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