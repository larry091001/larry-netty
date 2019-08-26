package com.larry.boot.contains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/23 11:22
 */
public class ContainsTest {
    public static void main(String[] args) {
        String filterStr = "AMS.T_AMS_AC_IF,AMS.T_AMS_AC_IF1,AMS.T_AMS_AC_IF2";
        String table = "T_AMS_AC_IF";
        List<String> addList = new ArrayList<>();
        addList.add("T_AMS_AC_IF");
        addList.add("t_ams_ac_if");
        addList.add("T_AMS_AC_IF1");
        addList.add("T_AMS_AC_IF3");

        String[] filterTables= filterStr.toLowerCase().split(",");
        String[] filterTable = new String[filterTables.length];
        for (int i = 0; i < filterTables.length; i++) {
            String filterName = filterTables[i];
            if(filterName.indexOf(".") != -1){
                filterName = filterName.split("\\.")[1];
            }
            filterTable[i] = filterName;
        }
        System.out.println("filter table --- > \n" + filterTable[0]+"\n"+filterTable[1]);
        List<String> filterTabList =  Arrays.asList(filterTable);
        if (filterTabList.contains(table.toLowerCase())) {
            System.out.println("包含");
        }else {
            System.out.println("不包含");
        }

        filterTabList.forEach(filter -> {
            addList.remove(filter);
            addList.remove(filter.toUpperCase());
        });

        System.out.println(addList);

    }
}
