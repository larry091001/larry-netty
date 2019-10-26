package com.larry.boot.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/29 10:04
 */
public class ListTest {
    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();
        Node parent1 = new Node("z1", "1");
        Node parent2 = new Node("z2", "2");
        Node parent3 = new Node("z3", "3");
        nodeList.add(parent1);
        nodeList.add(parent2);
        nodeList.add(parent3);

        List<Node> childList = new ArrayList<>();
        Node child1 = new Node("z1", "1");
        Node child2 = new Node("z2", "2-1");
        Node child3 = new Node("z3", "3-1");
        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
//        childList.forEach(node ->{
//            System.out.println("1111111111");
//        });
        int removeCount = 0;
        for(Node childNode : childList){
            for(Node parentNode : nodeList){
                if(parentNode.getName().equals(childNode.name)){
                    System.out.println(nodeList.remove(parentNode));
                    break;
                }
            }
//            if(nodeList.remove(node)){
//                System.out.println("异常：" +node.getName()+"=="+node.getCode());
//            }
        }

        List<String> list = new ArrayList<String>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.add("4444");
        List<String> addList = new ArrayList<String>();
        for(String str : list){
            if (!str.equals("1111")){
                addList.add(str);
            }
        }
    }

}
class Node{
    String name;
    String code;

    public Node(){}

    public Node(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}