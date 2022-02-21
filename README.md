# maven-test

## 介绍
> 各种相关不相关
1. rabbitMq
2. 响应式编程
3. redis集群模式
```shell script
docker-compose up -d;
# 注意修改ip
redis-cli --cluster create 172.19.0.3:7001 172.19.0.4:7002 172.19.0.2:7003
```
# mermaid test
```mermaid
stateDiagram-v2
[*] --> Still
Still --> [*]
Still --> Moving
Moving --> Still
Moving --> Crash
Crash --> [*]
```
```mermaid
 pie
 "Java" : 13733.025
 "SQL" : 9739.225
 "Scratch" : 2791.7
 "XML" : 2333.897
 "YAML" : 2298.638
```
