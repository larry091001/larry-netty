package com.larry.boot.format;

import java.util.List;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/22 13:33
 */
public class OggJsonFormat {

    /**
     * taskType : OGG
     * receiver : [{"realName":"超级管理员","phone":"13800138000","email":"liu_lp@suixingpay.com"}]
     * repName : rep_hx4
     * targetDb : {"address":"10.1.11.75","userName":"dcdb.dcm_owner"}
     * mapper : {"schema":["posdb.mss","dcdb.dcm_owner"],"table":["t_mss_mec_auth_if","t_mss_mec_auth_if"]}
     * taskId : 194
     * extName : ext_dc1
     * sourceDb : {"database":"posdb.mss","address":"10.3.120.51"}
     */

    private String taskType;
    private String repName;
    private TargetDbBean targetDb;
    private MapperBean mapper;
    private Long taskId;
    private String extName;
    private SourceDbBean sourceDb;
    private List<ReceiverBean> receiver;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public SourceDbBean getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(SourceDbBean sourceDb) {
        this.sourceDb = sourceDb;
    }

    public List<ReceiverBean> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<ReceiverBean> receiver) {
        this.receiver = receiver;
    }

    public static class TargetDbBean {
        /**
         * address : 10.1.11.75
         * userName : dcdb.dcm_owner
         */

        private String address;
        private String userName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

    public static class SourceDbBean {
        /**
         * database : posdb.mss
         * address : 10.3.120.51
         */

        private String database;
        private String address;

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
