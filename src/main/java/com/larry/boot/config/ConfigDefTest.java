package com.larry.boot.config;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/10/16 14:32
 */
public class ConfigDefTest{

    private static ConnectorConfig config;

    public static void main(String[] args) {
        Map<String, String> props = new HashMap<String, String>();
        props.put("topic","test_topic_1");
        props.put("db.name","oracle_test");
        props.put("db.url","192.168.1.16");
        props.put("db.user","oracle");
        props.put("db.user.password","oracle");
        start(props);
    }

    public static void start(Map<String, String> props){
        config = new ConnectorConfig(props);
        System.out.println(config.getTopic() + "==" + config.getDbName() + "==" + config.getDbUrl() + "===" + config.getDbUser() + "==" + config.getDbUserPassword());
    }


}

class ConnectorConfig extends AbstractConfig {
    public static final String TOPIC = "topic";
    public static final String DB_NAME = "db.name";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_USER_PASSWORD = "db.user.password";

    public static ConfigDef configDef() {
        return new ConfigDef().define(TOPIC, ConfigDef.Type.STRING, Importance.HIGH, "Topic")
                .define(DB_NAME, ConfigDef.Type.STRING, Importance.HIGH, "Db Name")
                .define(DB_URL, ConfigDef.Type.STRING, Importance.HIGH, "Db Url")
                .define(DB_USER, ConfigDef.Type.STRING, Importance.HIGH, "Db User")
                .define(DB_USER_PASSWORD, ConfigDef.Type.STRING, Importance.HIGH, "Db User Password");
    }

    public ConnectorConfig(Map<String, String> props) {
        this(configDef(), props);
    }

    public ConnectorConfig(ConfigDef config, Map<String, String> props) {
        super(config, props);
    }

    public String getTopic() {
        return this.getString(TOPIC);
    }

    public String getDbName() {
        return this.getString(DB_NAME);
    }

    public String getDbUrl() {
        return this.getString(DB_URL);
    }

    public String getDbUser() {
        return this.getString(DB_USER);
    }

    public String getDbUserPassword() {
        return this.getString(DB_USER_PASSWORD);
    }
}
