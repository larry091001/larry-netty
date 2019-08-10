package com.larry.boot.thrift.impl;

import com.larry.boot.thrift.generated.DataException;
import com.larry.boot.thrift.generated.Person;
import com.larry.boot.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/10 23:05
 * @version V1.0
 */
public class PersonServiceImpl implements PersonService.Iface{
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("get client param: " + username);
        Person person = new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("get client param: ");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried() ? "已婚" : "未婚");
    }
}
