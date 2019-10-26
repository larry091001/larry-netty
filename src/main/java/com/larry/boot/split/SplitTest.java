package com.larry.boot.split;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/9/10 18:05
 */
public class SplitTest {
    public static void main(String[] args) {
        Map<String, String> tableMapping = new HashMap<>();
        String[] tables =  "dcm_owner.T_PTS_TRANDATA,dcm_owner.T_PTS_TRANDATA_EXTEND".split(",");
        for (Integer index = 0; index < tables.length; index++) {
            String tableName = tables[index].substring(tables[index].indexOf(".") + 1);
            System.out.println(tableName);
            tableMapping.put(tableName,tableName);
        }
        System.out.println(tableMapping);
    }
}
