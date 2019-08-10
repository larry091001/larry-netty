package com.larry.boot.thrift.test;

import com.larry.boot.thrift.generated.Person;
import com.larry.boot.thrift.generated.PersonService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/11 0:08
 * @version V1.0
 */
public class ThriftClientTest {
    public static void main(String[] args) {
        TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);
        try{
            transport.open();
            Person person = client.getPersonByUsername("张三");

            System.out.println("get person param: ");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried() ? "已婚" : "未婚");

            System.out.println("==========================");
            Person person2 = new Person();
            person2.setUsername("李四");
            person2.setAge(21);
            person2.setMarried(true);

            client.savePerson(person2);

        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage(), ex);
        }finally {
            transport.close();
        }
    }
}
