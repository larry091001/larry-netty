package com.larry.boot.split;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/10/22 13:54
 */
public class StrSplitTest {
    public static void main(String[] args) {
        String str = "a.b";
        System.out.println(str.split("\\.")[0]);

        String str1 = "a1";
        System.out.println(str.split("\\.")[0]);
    }
}
