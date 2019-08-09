package com.larry.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/9 14:40
 */
public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("zhangsan").setAge(21).setAddress("大兴区").build();

        //转换成字节数组
        byte[] studentByteArray = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(studentByteArray);

        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
