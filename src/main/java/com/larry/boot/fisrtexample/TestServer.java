package com.larry.boot.fisrtexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/5 14:16
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        //线程组
        //bossGroup 客户端接收连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //连接处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            ChannelFuture future = serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }

    }
}

class Persron {

    /**
     * alarmPositionCount : 200000
     * consumer : {"consumerName":"KafkaFetch","converter":"oggJson","emptyFetchNoticeSpan":3600,"emptyFetchThreshold":3600,"includes":"OMS.t_base_factory,OMS.t_base_model,OMS.t_gm_card,OMS.t_base_goods,OMS.T_GM_DEVICE_POS,OMS.T_GM_DEVICE,OMS.T_GM_DEVICE_OPER_LOG,OMS.t_test_glt01","offset":"235036664","source":{"servers":"10.1.5.29:9200,10.1.5.30:9200,10.1.5.31:9200","sourceType":"KAFKA","topics":"agency_t9","pollTimeOut":"200","oncePollSize":"300","autoCommit":"false","group":"10264"}}
     * loader : {"insertOnUpdateError":false,"loaderName":"JdbcBatch","source":{"connectionErrorRetryAttempts":"3","password":"D99B192258D3CBE6","minPoolSize":"10","sourceType":"JDBC","maxWait":"10000","dbType":"ORACLE","userName":"zkw","maxPoolSize":"20","initialPoolSize":"10","url":"jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = no)(FAILOVER = yes)(ADDRESS = (PROTOCOL = TCP)(HOST = 10.3.120.53)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = posdb))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.3.120.54)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = posdb)))"}}
     * localTask : false
     * mapper : [{"forceMatched":true,"ignoreTargetCase":true,"schema":["OMS","OMS"]}]
     * nodeId : 103
     * positionCheckInterval : 1800
     * receiver : []
     * status : WORKING
     * taskId : 10264
     */

    private int alarmPositionCount;
    private ConsumerBean consumer;
    private LoaderBean loader;
    private boolean localTask;
    private String nodeId;
    private int positionCheckInterval;
    private String status;
    private String taskId;
    private List<MapperBean> mapper;
    private List<?> receiver;

    public int getAlarmPositionCount() {
        return alarmPositionCount;
    }

    public void setAlarmPositionCount(int alarmPositionCount) {
        this.alarmPositionCount = alarmPositionCount;
    }

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }

    public LoaderBean getLoader() {
        return loader;
    }

    public void setLoader(LoaderBean loader) {
        this.loader = loader;
    }

    public boolean isLocalTask() {
        return localTask;
    }

    public void setLocalTask(boolean localTask) {
        this.localTask = localTask;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getPositionCheckInterval() {
        return positionCheckInterval;
    }

    public void setPositionCheckInterval(int positionCheckInterval) {
        this.positionCheckInterval = positionCheckInterval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<MapperBean> getMapper() {
        return mapper;
    }

    public void setMapper(List<MapperBean> mapper) {
        this.mapper = mapper;
    }

    public List<?> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<?> receiver) {
        this.receiver = receiver;
    }

    public static class ConsumerBean {
        /**
         * consumerName : KafkaFetch
         * converter : oggJson
         * emptyFetchNoticeSpan : 3600
         * emptyFetchThreshold : 3600
         * includes : OMS.t_base_factory,OMS.t_base_model,OMS.t_gm_card,OMS.t_base_goods,OMS.T_GM_DEVICE_POS,OMS.T_GM_DEVICE,OMS.T_GM_DEVICE_OPER_LOG,OMS.t_test_glt01
         * offset : 235036664
         * source : {"servers":"10.1.5.29:9200,10.1.5.30:9200,10.1.5.31:9200","sourceType":"KAFKA","topics":"agency_t9","pollTimeOut":"200","oncePollSize":"300","autoCommit":"false","group":"10264"}
         */

        private String consumerName;
        private String converter;
        private int emptyFetchNoticeSpan;
        private int emptyFetchThreshold;
        private String includes;
        private String offset;
        private SourceBean source;

        public String getConsumerName() {
            return consumerName;
        }

        public void setConsumerName(String consumerName) {
            this.consumerName = consumerName;
        }

        public String getConverter() {
            return converter;
        }

        public void setConverter(String converter) {
            this.converter = converter;
        }

        public int getEmptyFetchNoticeSpan() {
            return emptyFetchNoticeSpan;
        }

        public void setEmptyFetchNoticeSpan(int emptyFetchNoticeSpan) {
            this.emptyFetchNoticeSpan = emptyFetchNoticeSpan;
        }

        public int getEmptyFetchThreshold() {
            return emptyFetchThreshold;
        }

        public void setEmptyFetchThreshold(int emptyFetchThreshold) {
            this.emptyFetchThreshold = emptyFetchThreshold;
        }

        public String getIncludes() {
            return includes;
        }

        public void setIncludes(String includes) {
            this.includes = includes;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

        public SourceBean getSource() {
            return source;
        }

        public void setSource(SourceBean source) {
            this.source = source;
        }

        public static class SourceBean {
            /**
             * servers : 10.1.5.29:9200,10.1.5.30:9200,10.1.5.31:9200
             * sourceType : KAFKA
             * topics : agency_t9
             * pollTimeOut : 200
             * oncePollSize : 300
             * autoCommit : false
             * group : 10264
             */

            private String servers;
            private String sourceType;
            private String topics;
            private String pollTimeOut;
            private String oncePollSize;
            private String autoCommit;
            private String group;

            public String getServers() {
                return servers;
            }

            public void setServers(String servers) {
                this.servers = servers;
            }

            public String getSourceType() {
                return sourceType;
            }

            public void setSourceType(String sourceType) {
                this.sourceType = sourceType;
            }

            public String getTopics() {
                return topics;
            }

            public void setTopics(String topics) {
                this.topics = topics;
            }

            public String getPollTimeOut() {
                return pollTimeOut;
            }

            public void setPollTimeOut(String pollTimeOut) {
                this.pollTimeOut = pollTimeOut;
            }

            public String getOncePollSize() {
                return oncePollSize;
            }

            public void setOncePollSize(String oncePollSize) {
                this.oncePollSize = oncePollSize;
            }

            public String getAutoCommit() {
                return autoCommit;
            }

            public void setAutoCommit(String autoCommit) {
                this.autoCommit = autoCommit;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }
        }
    }

    public static class LoaderBean {
        /**
         * insertOnUpdateError : false
         * loaderName : JdbcBatch
         * source : {"connectionErrorRetryAttempts":"3","password":"D99B192258D3CBE6","minPoolSize":"10","sourceType":"JDBC","maxWait":"10000","dbType":"ORACLE","userName":"zkw","maxPoolSize":"20","initialPoolSize":"10","url":"jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = no)(FAILOVER = yes)(ADDRESS = (PROTOCOL = TCP)(HOST = 10.3.120.53)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = posdb))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.3.120.54)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = posdb)))"}
         */

        private boolean insertOnUpdateError;
        private String loaderName;
        private SourceBeanX source;

        public boolean isInsertOnUpdateError() {
            return insertOnUpdateError;
        }

        public void setInsertOnUpdateError(boolean insertOnUpdateError) {
            this.insertOnUpdateError = insertOnUpdateError;
        }

        public String getLoaderName() {
            return loaderName;
        }

        public void setLoaderName(String loaderName) {
            this.loaderName = loaderName;
        }

        public SourceBeanX getSource() {
            return source;
        }

        public void setSource(SourceBeanX source) {
            this.source = source;
        }

        public static class SourceBeanX {
            /**
             * connectionErrorRetryAttempts : 3
             * password : D99B192258D3CBE6
             * minPoolSize : 10
             * sourceType : JDBC
             * maxWait : 10000
             * dbType : ORACLE
             * userName : zkw
             * maxPoolSize : 20
             * initialPoolSize : 10
             * url : jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = no)(FAILOVER = yes)(ADDRESS = (PROTOCOL = TCP)(HOST = 10.3.120.53)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = posdb))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.3.120.54)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = posdb)))
             */

            private String connectionErrorRetryAttempts;
            private String password;
            private String minPoolSize;
            private String sourceType;
            private String maxWait;
            private String dbType;
            private String userName;
            private String maxPoolSize;
            private String initialPoolSize;
            private String url;

            public String getConnectionErrorRetryAttempts() {
                return connectionErrorRetryAttempts;
            }

            public void setConnectionErrorRetryAttempts(String connectionErrorRetryAttempts) {
                this.connectionErrorRetryAttempts = connectionErrorRetryAttempts;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getMinPoolSize() {
                return minPoolSize;
            }

            public void setMinPoolSize(String minPoolSize) {
                this.minPoolSize = minPoolSize;
            }

            public String getSourceType() {
                return sourceType;
            }

            public void setSourceType(String sourceType) {
                this.sourceType = sourceType;
            }

            public String getMaxWait() {
                return maxWait;
            }

            public void setMaxWait(String maxWait) {
                this.maxWait = maxWait;
            }

            public String getDbType() {
                return dbType;
            }

            public void setDbType(String dbType) {
                this.dbType = dbType;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMaxPoolSize() {
                return maxPoolSize;
            }

            public void setMaxPoolSize(String maxPoolSize) {
                this.maxPoolSize = maxPoolSize;
            }

            public String getInitialPoolSize() {
                return initialPoolSize;
            }

            public void setInitialPoolSize(String initialPoolSize) {
                this.initialPoolSize = initialPoolSize;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class MapperBean {
        /**
         * forceMatched : true
         * ignoreTargetCase : true
         * schema : ["OMS","OMS"]
         */

        private boolean forceMatched;
        private boolean ignoreTargetCase;
        private List<String> schema;

        public boolean isForceMatched() {
            return forceMatched;
        }

        public void setForceMatched(boolean forceMatched) {
            this.forceMatched = forceMatched;
        }

        public boolean isIgnoreTargetCase() {
            return ignoreTargetCase;
        }

        public void setIgnoreTargetCase(boolean ignoreTargetCase) {
            this.ignoreTargetCase = ignoreTargetCase;
        }

        public List<String> getSchema() {
            return schema;
        }

        public void setSchema(List<String> schema) {
            this.schema = schema;
        }
    }
}
