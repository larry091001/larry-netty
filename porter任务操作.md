1.porter 前端重做
2.MGR对页面请求接口暴露，提供页面（添加新的接口，尽量独立的）
3.数据权限
4.OCR只支持单点


1.分析链路
2.同步任务的表查询对应的topic
3.根据对应topic配置几个任务
4.配置新的任务先获取offset值
  zookeeper
> cd /home/app/zookeeper-3.4.11/bin
> ./zkCli.sh
[zk: localhost:2181(CONNECTED) 8] get /suixingpay/datas/task/293/position/DC_AMS     查看offset
[zk: localhost:2181(CONNECTED) 8] get /suixingpay/datas/task/293/position/EWM_SHZX   查看offset

查看是否有积压
http://10.1.1.245:9000/node3/inspect/node/info.json     238 EWM_SHZX 和 293 DC_AMS

5.任务启动（尽量配置新任务在原来的机器上操作，以方便停止原先老的任务）


=====================porter操作步骤==============================
1.分析链路
2.同步任务的表查询对应的topic
3.根据对应topic配置几个任务
4.配置新的任务先获取offset值

前置：分析数据重要性
 0、查看offset位置
 
 1、查看kafka的topic是否有积压
 http://10.1.1.245:9000/node2/inspect/node/info
 1.1、http://10.1.1.245:9000/node5/inspect/node/jstack
 
 根据topic和任务id查看 积压程度
 2、查看node节点是否有资源
 登录系统：集群管理-同步节点管理，查看node对应的ip，
 free -m 查看是否有剩余内存，top
 3、改动配置：
 任务号，nodeId，consumer.source.group =10293，topics名称，consumer.includes，consumer.offset，
 mapper[0].schema=UAP,DCM_OWNER（源端：用户名UAP变更为目标端 ：用户名DCM_OWNER）
 4、任务名称 必填：源端 ---> 目标端，例如：新T9到AMS_名字
 5、启动，	 查看zookeeper中offset 
  get /suixingpay/datas/task/10293/position/EWM_SHZX
 5.1表重要：2个任务同时跑
 5.2表不重要：重置offset到最新
 5.3重置offset命令
 get /suixingpay/datas/task/20293/position/t9_out3 
 set /suixingpay/datas/task/20293/position/t9_out3 '{"offset":2788838,"partition":0,"positionKey":"t9_out3_0","topic":"t9_out3"}'
 6、监控管理-任务监控，是否正常
  
 154机器
 创建kafka：
 /data/kafka_2.12-2.2.0/bin/kafka-topics.sh  --create --topic tss_out --partitions 1  --replication-factor 2  --zookeeper $zookeeper

taskId=10293
nodeId=205

consumer.consumerName=KafkaFetch
consumer.converter=oggJson
consumer.source.sourceType=KAFKA
consumer.source.servers=10.1.1.246:9200,10.1.1.247:9200 
consumer.source.topics=EWM_SHZX
consumer.source.autoCommit=false
consumer.source.group=10293
consumer.source.oncePollSize=300
consumer.source.pollTimeOut=200
consumer.includes=dcm_owner.T_PTS_ZPOS_CONFIG

loader.loaderName=JdbcBatch
loader.source.sourceName=76DCDB
loader.insertOnUpdateError=false
consumer.offset=1343115467

positionCheckInterval=1800
alarmPositionCount=10000
mapper[0].auto=false
mapper[0].forceMatched=true

##kafka-----------------》oracle
任务二：
taskId=20293
nodeId=205
consumer.consumerName=KafkaFetch
consumer.converter=oggJson
consumer.source.sourceType=KAFKA
consumer.source.servers=10.3.120.154:9092,10.3.120.155:9092,10.3.120.156:9092
consumer.source.topics=t9_out3
consumer.source.autoCommit=false
consumer.source.group=20293
consumer.source.oncePollSize=300
consumer.source.pollTimeOut=100
consumer.includes=UAP.T_UAP_ORG,RCS.T_MEC_FREEZ_TRADING_INQUIRY
consumer.offset=2788838-2791608

loader.loaderName=JdbcBatch
loader.source.sourceName=75DCDB
loader.insertOnUpdateError=false

mapper[0].schema=UAP,DCM_OWNER
mapper[0].forceMatched=true
mapper[1].schema=RCS,DCM_OWNER
mapper[1].forceMatched=true


positionCheckInterval=3600
alarmPositionCount=200000


##kafka-------消费------》mysql
taskId=20293
nodeId=205
consumer.consumerName=KafkaFetch
consumer.converter=oggJson
consumer.source.sourceType=KAFKA
consumer.source.servers=10.3.120.154:9092,10.3.120.155:9092,10.3.120.156:9092
consumer.source.topics=t9_out3
consumer.source.autoCommit=false
consumer.source.group=20293
consumer.source.oncePollSize=300
consumer.source.pollTimeOut=100
consumer.includes=UAP.T_UAP_ORG,RCS.T_MEC_FREEZ_TRADING_INQUIRY
consumer.offset=2788838

loader.loaderName=JdbcBatch
loader.source.sourceType=JDBC
loader.source.dbType=MYSQL
loader.source.url=jdbc:mysql://10.3.100.204:3306/amsdb?useUnicode=true&characterEncoding=utf8
loader.source.userName=portal_target
loader.source.password=bfUyhblhEfysyXGv
loader.source.maxWait=60000
loader.source.minPoolSize=5
loader.source.maxPoolSize=5
loader.source.initialPoolSize=5
loader.source.connectionErrorRetryAttempts=3
loader.insertOnUpdateError=false

mapper[0].auto=false
mapper[0].forceMatched=true
mapper[0].schema=UAP,amsdb
mapper[1].auto=false
mapper[1].forceMatched=true
mapper[1].schema=RCS,amsdb

positionCheckInterval=3600
alarmPositionCount=200000


##mysql -------消费-----》kafka
taskId=10294
nodeId=205

consumer.consumerName=CanalFetch
consumer.converter=canalRow
consumer.includes=smssdb.t_smt_execute_result,smssdb.t_smt_execute_log,smssdb.t_smt_original_data
consumer.source.sourceType=CANAL
consumer.source.slaveId=10294
consumer.source.filter=smssdb\\.(t_smt_execute_result|t_smt_execute_log|t_smt_original_data)
consumer.source.database=smssdb
consumer.source.password=nJOpH4jOzSTVhAZv
consumer.source.address=10.3.100.19:3306
consumer.source.username=porter_source
#要找DBA要
consumer.offset=mysql-bin.000064 

loader.loaderName=KAFKA_SYNC
loader.source.sourceType=KAFKA_PRODUCE
loader.source.servers=10.3.120.154:9092,10.3.120.155:9092,10.3.120.156:9092
loader.source.topic=smssdb_out
#不知道什么情况
loader.source.oggJson=true

positionCheckInterval=1800
alarmPositionCount=500000



##第一步：创建topic
/data/kafka_2.12-2.2.0/bin/kafka-topics.sh  --create --topic smssdb_out --partitions 1  --replication-factor 2  --zookeeper $zookeeper

##配置好任务，然后启动一下，停止，然后设置
 set /suixingpay/datas/task/10294/position/smssdb '{"batchId":160,"logfileName":"mysql-bin.000501","offset":4}'
然后 get /suixingpay/datas/task/10294/position/smssdb
查看是否修改成功
然后启动任务。

##查看topic消费记录
/data/kafka_2.12-2.2.0/bin/kafka-console-consumer.sh --bootstrap-server $broker --from-beginning --topic smssdb_out


##kafka-----消费----->kafka 且是多表且需要做1拆3任务，才有loader.source.partitionKey
taskId=251
nodeId=205
consumer.consumerName=KafkaFetch
consumer.converter=oggJson
consumer.source.sourceType=KAFKA
consumer.source.servers=10.1.5.29:9200,10.1.5.30:9200,10.1.5.31:9200
consumer.source.topics=DC_AMS
consumer.source.autoCommit=false
consumer.source.group=DC_AMS_PTS_TRANDATA_SPREAD_GROUP
consumer.source.oncePollSize=1000
consumer.source.pollTimeOut=1000
consumer.source.firstConsumeFrom=latest ## 注意从最后消费（不需要配置是配置offest=0）
consumer.includes=DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND

loader.loaderName=KAFKA_SYNC
loader.source.sourceType=KAFKA_PRODUCE
loader.source.servers=10.1.5.29:9200,10.1.5.30:9200,10.1.5.31:9200 
loader.source.topic=DC_AMS_PTS_TRANDATA_SPREAD
loader.source.transaction=false
loader.source.partitionKey.DCM_OWNER.T_PTS_TRANDATA=UUID
loader.source.partitionKey.DCM_OWNER.T_PTS_TRANDATA_EXTEND=UUID

positionCheckInterval=-1


################ 操作表：T_PTS_TRANDATA、T_PTS_TRANDATA_EXTEND 链路做合理改造 ################
##1.链路分析
    1、数据是从10.3.120.51和10.3.120.1 来的数据 数据中心和二维码的数据交易数据
    
##2.创建新的topic（如果是ogg吐到kafka的，需要DBA配合创建）
    1、创建topic：
    ogg吐入数据kafka：/data/kafka_2.12-2.2.0/bin/kafka-topics.sh --create --topic pts_trandata_or_extend --partitions 1 --replication-factor 2 --zookeeper $zookeeper
    porter任务消费kafka：/data/kafka_2.12-2.2.0/bin/kafka-topics.sh --create --topic dcm_owner_pts_trandata_or_extend --partitions 3 --replication-factor 2 --zookeeper $zookeeper

###3.查找对应的关联porter任务
    242---》同步dcm_owner.t_pts_trandata （oracle：10.1.11.143）节点：201
    286---》同步DCM_OWNER.T_PTS_TRANDATA，mapper[0].table=T_PTS_TRANDATA,T_PTS_TRANDATA （mysql：10.3.100.204）节点：205
    299-->同步DCM_OWNER.T_PTS_TRANDATA_EXTEND（mysql：10.3.100.204）节点：205
 
    251---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （kafka：29,30,31）节点：205
    10252---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （mysql：10.3.100.213）节点：205
    10253---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （mysql：10.3.100.213）节点：205
    10254---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （mysql：10.3.100.213）节点：205
 
    256--》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （kafka：29,30,31）节点：103
    257---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （oracle：75DCDB--75）节点：103
    258---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （oracle：75DCDB--75）节点：103
    259---》同步DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND （oracle：75DCDB--75）节点：103
    

###4.查看offset位置
    get /suixingpay/datas/task/242/position/DC_AMS  -------> "offset":22132755978
    get /suixingpay/datas/task/286/position/DC_AMS  -------> "offset":22132741188
    get /suixingpay/datas/task/299/position/DC_AMS  -------> "offset":22132727920

    get /suixingpay/datas/task/251/position/DC_AMS  -------> "offset":22132767616
    get /suixingpay/datas/task/10252/position/DC_AMS_PTS_TRANDATA_SPREAD  -------> "offset":1585841310
    get /suixingpay/datas/task/10253/position/DC_AMS_PTS_TRANDATA_SPREAD  -------> "offset":1586270422
    get /suixingpay/datas/task/10254/position/DC_AMS_PTS_TRANDATA_SPREAD  -------> "offset":1586043065

    get /suixingpay/datas/task/257/position/EWM_SHZX_PTS_TRANDATA_SPREAD  -------> "offset":311482866
    get /suixingpay/datas/task/258/position/EWM_SHZX_PTS_TRANDATA_SPREAD  -------> "offset":311532663
    get /suixingpay/datas/task/259/position/EWM_SHZX_PTS_TRANDATA_SPREAD  -------> "offset":311574543

###5.配置任务
    5.1、配置任务注意点：
        1、根据 源和目标 选择好对应的配置信息
        2、配置任务id尽量有一定标志性：比如.
            taskId=15251 --->从原先的251的任务改造的，
            如果是新的任务，就写好规则3.0版本是1开头，4.0版本是2开头
        3、配置nodeId节点
            3.1.先看配置node节点对应机器内存是否足够， 命令：free -m 同时也top一下查看CPU
        4、配置servers和topics及group
            consumer.source.servers=10.3.120.154:9092,10.3.120.155:9092,10.3.120.156:9092
            consumer.source.topics=dcm_owner_pts_trandata_or_extend
            consumer.source.group=15252
        5、配置partition，如果1拆3
            consumer.source.partition=0
            consumer.source.partition=1
            consumer.source.partition=2
        6、配置includes
            consumer.includes=DCM_OWNER.T_PTS_TRANDATA,DCM_OWNER.T_PTS_TRANDATA_EXTEND
        7、配置offset，且注意 consumer.source.firstConsumeFrom=latest
            consumer.offset=1585935995
        8、配置schema
            mapper[0].schema=DCM_OWNER,amsdb（源端：用户名DCM_OWNER变更为目标端 ：用户名amsdb）     
 
###6.启动任务，等待3到4秒，立马停止，查看zookeeper中offset
    查看新topic的offset位置命令：get /suixingpay/datas/task/10251/position/pts_trandata_or_extend

###7.进行对zookeeper中offset 和 配置中的offset比较：
    1、offset位置相差不是大，可忽略
    2、offset位置相差很大
      2.1、不重要的表，重置offset到最新，重置命令： 
        set /suixingpay/datas/task/15252/position/dcm_owner_pts_trandata_or_extend '{"offset":设置的offset,"partition":0,"positionKey":"dcm_owner_pts_trandata_or_extend_0","topic":"dcm_owner_pts_trandata_or_extend"}'
        set /suixingpay/datas/task/15253/position/dcm_owner_pts_trandata_or_extend '{"offset":设置的offset,"partition":1,"positionKey":"dcm_owner_pts_trandata_or_extend_1","topic":"dcm_owner_pts_trandata_or_extend"}'
        set /suixingpay/datas/task/15254/position/dcm_owner_pts_trandata_or_extend '{"offset":设置的offset,"partition":2,"positionKey":"dcm_owner_pts_trandata_or_extend_2","topic":"dcm_owner_pts_trandata_or_extend"}'
      2.2、重要的表，先分析，然后设置offset位置，或设置offset位置任务追补    

###8.重要的表，查看kafka的topic是否有积压和jstack
    查看命令：
    1、http://10.1.1.245:9000/node2/inspect/node/info      
    2、http://10.1.1.245:9000/node5/inspect/node/jstack
    
###9.启动任务

###10.查看监控管理-任务监控，同时打开任务对应的节点机器查看日志
    1、监控无显示信息：
       1.1、就查看日志信息是否有异常，根据节点登录对应的堡垒机ip：
        tail -1000f porter-boot-3.0.2/logs/data-node.log
       1.2、查看topic消费记录，命令如下:
        /data/kafka_2.12-2.2.0/bin/kafka-console-consumer.sh --bootstrap-server $broker --from-beginning --topic dcm_owner_pts_trandata_or_extend
    2、监控正常就往下操作
        
###11.原来任务是否停止，以下判断来操作：
    1、如果topic有积压，原来的任务不能停止，等到无积压才能停止原来的任务
    2、如果topic无积压，可停止原来的任务

################### 机器信息和地址信息 ######################
###1.zookeeper命令
 ###1.1.节点查看：ls /suixingpay/datas/node
    [201, 103, 202, 204, 205] 节点数
    节点对应的ip以下：
        103---》ip：10.1.45.65     ---》 http://10.1.1.245:9000/node2/inspect/node/info
        201---》ip：10.1.45.64     ---》 http://10.1.1.245:9000/node1/inspect/node/info
        202---》ip：10.1.45.95     ---》 http://10.1.1.245:9000/node3/inspect/node/info
        204---》ip：10.1.45.138    ---》 http://10.1.1.245:9000/node4/inspect/node/info
        205---》ip：10.1.42.221    ---》 http://10.1.1.245:9000/node5/inspect/node/info
    
###2.porter管理后台生成访问地址：
    http://10.1.1.245:9000/login.html 3.0版本
    http://10.3.1.176:8080/login.html 4.0版本
    
###3.porter查看topic ui地址：
    http://10.1.1.245:9000/ogg/ui   
    
###4.数据治理管理地址：
    http://huge.suixingpay.com
    
###5.查看topic积压与jstack地址：
     1、http://10.1.1.245:9000/node2/inspect/node/info      
     2、http://10.1.1.245:9000/node5/inspect/node/jstack
     
###6.kafka集群和zookeeper集群
    6.1、new kafka 集群
     10.3.120.154:9092,10.3.120.155:9092,10.3.120.156:9092
     10.3.120.160:9092,10.3.120.161:9092,10.3.120.162:9092
     10.3.120.167:9092,10.3.120.168:9092,10.3.120.169:9092
     
    6.2、old kafka 集群
     10.1.1.246:9200,10.1.1.247:9200
     10.1.5.29:9200,10.1.5.30:9200,10.1.5.31:9200  
     
    6.3、new zookeeper集群
     10.3.120.172:2181,10.3.120.173:2181,10.3.120.174:2181
     
    6.4、old zookeeper集群
     10.1.1.75:2181,10.1.1.239:2181,10.1.1.245:2181 
     
     
 ###7.kafka 相关操作
    /data/kafka_2.12-2.2.0/bin/kafka-console-consumer.sh --bootstrap-server $broker --from-beginning --topic pts_trandata_or_extend 	查看topic数据
    /data/kafka_2.12-2.2.0/bin/kafka-topics.sh --create --topic pts_trandata_or_extend --partitions 1 --replication-factor 2 --zookeeper $zookeeper 	创建topic
    /data/kafka_2.12-2.2.0/bin/kafka-topics.sh --list --zookeeper $zookeeper  	查看topic
    /data/kafka_2.12-2.2.0/bin/kafka-topics.sh --delete --topic dcm_owner_pts_trandata_or_extend --zookeeper $zookeeper 	删除topic
    /data/kafka_2.12-2.2.0/bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list 10.3.120.154:9092,10.3.120.155:9092,10.3.120.156:9092 -topic pts_trandata_or_extend --time -1 	查看总数
    /data/kafka_2.12-2.2.0/bin/kafka-console-consumer.sh --bootstrap-server $broker --from-beginning --topic dcm_owner_pts_trandata_or_extend | grep 'T_PTS_TRANDATA_EXTEND'  查看表的kafka消费
    //查询offset时间点
    ./kafka-console-consumer.sh --bootstrap-server 10.1.5.29:9200  --topic DC_AMS  --offset 23114320238 --partition 0

    
###8.针对（T_PTS_TRANDATA、T_PTS_TRANDATA_EXTEND）任务改造，操作任务列表为：
    任务：15242，旧任务id：242   ,节点：201,启动状态:（停止）,验证状态：未验证
    任务：15257，旧任务id：257   ,节点：103,启动状态:（停止）,验证状态：未验证
    任务：15286，旧任务id：286   ,节点：205,启动状态:（停止）,验证状态：未验证
    任务：15299，旧任务id：299   ,节点：205,启动状态:（停止）,验证状态：未验证
    任务：15251，旧任务id：251   ,节点：205,启动状态:（停止）,验证状态：无须验证
    任务：15252，旧任务id：10252 ,节点：205,启动状态:（停止）,验证状态：未验证
    任务：15253，旧任务id：10253 ,节点：205,启动状态:（停止）,验证状态：未验证
    任务：15254，旧任务id：10254 ,节点：205,启动状态:（停止）,验证状态：未验证

###9.配置任务启动异常：
    场景为：kafka---》kafka，通知同步两张表
    consumer.includes=PTS.T_PTS_TRANDATA,PTS.T_PTS_TRANDATA_EXTEND
    loader.source.partitionKey.PTS.T_PTS_TRANDATA=UUID
    loader.source.partitionKey.PTS.T_PTS_TRANDATA_EXTEND=UUID
    
2019-09-23 15:18:44.571 ERROR 60505 --- [suixingpay-572-TaskWork-[taskId:15251]-[consumer:pts_trandata_or_extend]-LoadJob-0] c.v.m.p.common.client.AbstractClient     : fail to send message to kafka,times:0

java.lang.InterruptedException: null
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1302)
	at java.util.concurrent.CountDownLatch.await(CountDownLatch.java:231)
	at org.apache.kafka.clients.producer.internals.ProduceRequestResult.await(ProduceRequestResult.java:76)
	at org.apache.kafka.clients.producer.internals.FutureRecordMetadata.get(FutureRecordMetadata.java:61)
	at org.apache.kafka.clients.producer.internals.FutureRecordMetadata.get(FutureRecordMetadata.java:29)
	at cn.vbill.middleware.porter.common.client.impl.KafkaProduceClient.nativeSendTo(KafkaProduceClient.java:223)
	at cn.vbill.middleware.porter.common.client.impl.KafkaProduceClient.sendTo(KafkaProduceClient.java:178)
	at cn.vbill.middleware.porter.common.client.impl.KafkaProduceClient.send(KafkaProduceClient.java:129)
	at cn.vbill.middleware.porter.plugin.loader.kafka.KafkaLoader.storeData(KafkaLoader.java:79)
	at cn.vbill.middleware.porter.plugin.loader.kafka.KafkaLoader.load(KafkaLoader.java:63)
	at cn.vbill.middleware.porter.task.load.LoadJob.loopLogic(LoadJob.java:144)
	at cn.vbill.middleware.porter.core.task.AbstractStageJob$LoopService.run(AbstractStageJob.java:141)
	at java.lang.Thread.run(Thread.java:745)

2019-09-23 15:18:45.072 ERROR 60505 --- [suixingpay-572-TaskWork-[taskId:15251]-[consumer:pts_trandata_or_extend]-LoadJob-0] c.v.middleware.porter.task.load.LoadJob  : Load ETLRow error!

java.lang.NullPointerException: null

    解决方式：先注释掉一个表，同时也注释掉对应partitionKey，然后启动，任务正常。
             然后在把注释放开，partitionKey注释也放开，在重新启动任务