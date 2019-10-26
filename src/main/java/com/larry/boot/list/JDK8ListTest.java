package com.larry.boot.list;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/10/24 13:51
 */
public class JDK8ListTest {
    public static void main(String[] args) {
        List<DatasourceTable> oldTableList = new ArrayList<>();
        String tableSchema = "A.A1";
        DatasourceTable table1 = new DatasourceTable();
        table1.setSchema("A");
        table1.setTableName("A1");
        table1.setName("a");

        DatasourceTable table2 = new DatasourceTable();
        table2.setSchema("A");
        table2.setTableName("A2");
        table2.setName("b");

        DatasourceTable table3 = new DatasourceTable();
        table3.setSchema("B");
        table3.setTableName("B1");
        table3.setName("a");

        oldTableList.add(table1);
        oldTableList.add(table2);
        oldTableList.add(table3);

        List<DatasourceTable> newTableList = new ArrayList<>();

        DatasourceTable table4 = new DatasourceTable();
        table4.setSchema("C");
        table4.setTableName("C1");
        table4.setName("a");

        DatasourceTable table5 = new DatasourceTable();
        table5.setSchema("A");
        table5.setTableName("A2");
        table5.setName("a");

        DatasourceTable table6 = new DatasourceTable();
        table6.setSchema("A");
        table6.setTableName("A1");
        table6.setName("a");

        newTableList.add(table4);
        newTableList.add(table5);
        newTableList.add(table6);

        List<String> dtNameList = oldTableList.stream()
                .map(DatasourceTable :: getSchema)
                .collect(toList());

        System.out.println(dtNameList);

        //找出相同的
        List<DatasourceTable> collect = oldTableList.stream()
                .filter(o -> tableSchema.equalsIgnoreCase(o.getSchema()+"."+o.getTableName()))
                .collect(toList());
        System.out.println(collect.get(0).getSchema()+"=="+collect.get(0).getTableName());

        //找出不相同的
        List<DatasourceTable> updateColumnList = newTableList.stream()
                .filter(item -> !oldTableList.contains(item))
                .collect(toList());
        updateColumnList.stream().forEach(data ->{
            System.out.println("更新的列信息：" + data.getSchema()+"=="+data.getTableName());
        });

        newTableList.stream().forEach(data -> {
            System.out.println("更新的列信息1：" + oldTableList.contains(data));
        });

        System.out.println(DomainEqualsUtil.domainEquals(table1, table6));
        System.out.println(table6.hashCode());
    }

//    public StringBuilder compareContract(CreateContractSignInfoRequest sign, CreateContractSignInfoRequest existSign) {
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            Field[] fields = getAllFields(sign);
//            for (int j = 0; j < fields.length; j++) {
//                fields[j].setAccessible(true);
//                // 字段值
//                if (!fields[j].get(sign).equals(fields[j].get(existSign))) {
//                    stringBuilder.append(existSign.getUserName());
//                    stringBuilder.append(fields[j].getName() + "、");
//                }
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return stringBuilder;
//    }

    /**
     * 获取所有属性，包括父类
     *
     * @param object
     * @return
     */
//    public Field[] getAllFields(Object object) {
//        Class clazz = object.getClass();
//        List<Field> fieldList = new ArrayList<>();
//        while (clazz != null) {
//            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
//            clazz = clazz.getSuperclass();
//        }
//        Field[] fields = new Field[fieldList.size()];
//        fieldList.toArray(fields);
//        return fields;
//    }

}

class DatasourceTable{
    String schema;
    String name;
    String tableName;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
