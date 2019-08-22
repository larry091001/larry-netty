package com.larry.boot.format;

import java.util.List;

/**
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/22 13:20
 * @version V1.0
 */
public class PorterJsonFormat {
    /**
     * version : 1.0
     * taskType : PORTER
     * taskId : 298
     * sourceDb : {"firstConsumeFrom":"latest","partition":"1","sourceType":"KAFKA","topics":"DC_AMS_T_SES_SET_ORD_INF_SPREAD","host":"10.1.1.246:9200,10.1.1.247:9200 ","autoCommit":"false"}
     * targetDb : {"connectionErrorRetryAttempts":"3","database":"amsdb","minPoolSize":"5","sourceType":"JDBC","maxWait":"60000","host":"10.3.100.204","dbType":"MYSQL","userName":"portal_target","maxPoolSize":"5","initialPoolSize":"5"}
     * mapper : {"schema":["DC_AMS_T_SES_SET_ORD_INF_SPREAD","amsdb"],"table":["t_ses_set_ord_inf","t_ses_set_ord_inf"]}
     * receiver : []
     * status : WORKING
     */

    private String version;
    private String taskType;
    private Long taskId;
    private SourceDbBean sourceDb;
    private TargetDbBean targetDb;
    private MapperBean mapper;
    private String status;
    private List<ReceiverBean> receiver;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public SourceDbBean getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(SourceDbBean sourceDb) {
        this.sourceDb = sourceDb;
    }

    public TargetDbBean getTargetDb() {
        return targetDb;
    }

    public void setTargetDb(TargetDbBean targetDb) {
        this.targetDb = targetDb;
    }

    public MapperBean getMapper() {
        return mapper;
    }

    public void setMapper(MapperBean mapper) {
        this.mapper = mapper;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReceiverBean> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<ReceiverBean> receiver) {
        this.receiver = receiver;
    }


    public static class SourceDbBean {
        /**
         * firstConsumeFrom : latest
         * partition : 1
         * sourceType : KAFKA
         * topics : DC_AMS_T_SES_SET_ORD_INF_SPREAD
         * host : 10.1.1.246:9200,10.1.1.247:9200
         * autoCommit : false
         */

        private String firstConsumeFrom;
        private String partition;
        private String sourceType;
        private String topics;
        private String host;
        private String autoCommit;

        public String getFirstConsumeFrom() {
            return firstConsumeFrom;
        }

        public void setFirstConsumeFrom(String firstConsumeFrom) {
            this.firstConsumeFrom = firstConsumeFrom;
        }

        public String getPartition() {
            return partition;
        }

        public void setPartition(String partition) {
            this.partition = partition;
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

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getAutoCommit() {
            return autoCommit;
        }

        public void setAutoCommit(String autoCommit) {
            this.autoCommit = autoCommit;
        }
    }

    public static class TargetDbBean {
        /**
         * connectionErrorRetryAttempts : 3
         * database : amsdb
         * minPoolSize : 5
         * sourceType : JDBC
         * maxWait : 60000
         * host : 10.3.100.204
         * dbType : MYSQL
         * userName : portal_target
         * maxPoolSize : 5
         * initialPoolSize : 5
         */

        private String connectionErrorRetryAttempts;
        private String database;
        private String minPoolSize;
        private String sourceType;
        private String maxWait;
        private String host;
        private String dbType;
        private String userName;
        private String maxPoolSize;
        private String initialPoolSize;

        public String getConnectionErrorRetryAttempts() {
            return connectionErrorRetryAttempts;
        }

        public void setConnectionErrorRetryAttempts(String connectionErrorRetryAttempts) {
            this.connectionErrorRetryAttempts = connectionErrorRetryAttempts;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
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

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
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
    }

    public static class MapperBean {
        private List<String> schema;
        private List<String> table;

        public List<String> getSchema() {
            return schema;
        }

        public void setSchema(List<String> schema) {
            this.schema = schema;
        }

        public List<String> getTable() {
            return table;
        }

        public void setTable(List<String> table) {
            this.table = table;
        }
    }

    public static class ReceiverBean {
        /**
         * realName : 超级管理员
         * phone : 13800138000
         * email : liu_lp@suixingpay.com
         */

        private String realName;
        private String phone;
        private String email;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
