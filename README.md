###   just for fun

## Spider

  - 针对不同网站 提供聚集多个网站和单个网站的接口 以供别人调用
  - 任务队列使用redis/kafka/rabbitmq任选
  
  
  
## Store

  -  存取数据到MySQL中，然后同步到elasticsearch，也可以拉binlog
     同步到es中
 
     
## Monitor
  - 除去SpringBoot自身的监控，植入zipkin这种无侵入性的agent


## Rpc
  -  我们采用[gd-rpc](https://github.com/goudai/gd-rpc) 来实现rpc接口调用

## Task
  - spring task/light-task-scheduler/quartz/elastic-job任选   
     
## Show

  -  todo