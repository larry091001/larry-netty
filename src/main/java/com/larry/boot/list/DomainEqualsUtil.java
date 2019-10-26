package com.larry.boot.list;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * 实体对比工具
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/10/25 13:52
 */
public class DomainEqualsUtil {
    /**
     * 日志操作类
     */
//    private static Logger logger = Logger.getLogger(DomainEquals.class);

    private static final ObjectMapper mapper=new ObjectMapper();

    /**
     * 比较两个BEAN或MAP对象的值是否相等
     * 如果是BEAN与MAP对象比较时MAP中的key值应与BEAN的属性值名称相同且字段数目要一致
     * @param source
     * @param target
     * @return
     */
    public static boolean domainEquals(Object source, Object target) {
        if (source == null || target == null) {
            return false;
        }
        boolean rv = true;
        if (source instanceof Map) {
            rv = mapOfSrc(source, target, rv);
        } else {
            rv = classOfSrc(source, target, rv);
        }
        System.out.println("THE EQUALS RESULT IS " + rv);
        return rv;
    }

    /**
     * 源目标为MAP类型时
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean mapOfSrc(Object source, Object target, boolean rv) {
        HashMap<String, String> map = new HashMap<String, String>();
        map = (HashMap) source;
        for (String key : map.keySet()) {
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                if(tarMap.get(key)==null){
                    rv = false;
                    break;
                }
                if (!map.get(key).equals(tarMap.get(key))) {
                    rv = false;
                    break;
                }
            } else {
                String tarValue = getClassValue(target, key) == null ? "" : getClassValue(target, key).toString();
                if (!tarValue.equals(map.get(key))) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 源目标为非MAP类型时
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean classOfSrc(Object source, Object target, boolean rv) {
        Class<?> srcClass = source.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey = field.getName();
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                if(tarMap.get(nameKey)==null){
                    rv = false;
                    break;
                }
                if (!tarMap.get(nameKey).equals(srcValue)) {
                    rv = false;
                    break;
                }
            } else {
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                String tarValue = getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey)
                        .toString();
                if (!srcValue.equals(tarValue)) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 根据字段名称取值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[] {});
                } catch (Exception e) {
                    System.out.println("反射取值出错：" + e.toString());
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID")
                        && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase()
                        .equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
            // logger.info("取方法出错！" + e.toString());
        }
        return null;
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个list分别存obj1,obj2此属性名的值
     * @param obj1 进行属性比较的对象1
     * @param obj2 进行属性比较的对象2
     * @param compareArr 选择要比较的属性数组
     * @return 属性差异比较结果map
     */
    public static Map<String, List<Object>> compareFields(Object obj1, Object obj2, String[] compareArr) {
        try {
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            List<String> ignoreList = null;
            if (compareArr != null && compareArr.length > 0) {
                // array转化为list
                ignoreList = Arrays.asList(compareArr);
            }
            // 只有两个对象都是同一类型的才有可比性
            if (obj1.getClass() == obj2.getClass()) {
                Class clazz = obj1.getClass();
                // 获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                        Object.class).getPropertyDescriptors();
                // 这里就是所有的属性了
                for (PropertyDescriptor pd : pds) {
                    // 属性名
                    String name = pd.getName();
                    // 如果当前属性选择进行比较，跳到下一次循环
                    if (ignoreList != null && ignoreList.contains(name)) {
                        // get方法
                        Method readMethod = pd.getReadMethod();
                        // 在obj1上调用get方法等同于获得obj1的属性值
                        Object objBefore = readMethod.invoke(obj1);
                        // 在obj2上调用get方法等同于获得obj2的属性值
                        Object objAfter = readMethod.invoke(obj2);
                        if (objBefore instanceof Timestamp) {
                            objBefore = new Date(((Timestamp) objBefore).getTime());
                        }
                        if (objAfter instanceof Timestamp) {
                            objAfter = new Date(((Timestamp) objAfter).getTime());
                        }
                        if (objBefore == null && objAfter == null) {
                            continue;
                        } else if (objBefore == null && objAfter != null) {
                            List<Object> list = new ArrayList<Object>();
                            list.add(objBefore);
                            list.add(objAfter);
                            map.put(name, list);
                            continue;
                        }
                        // 比较这两个值是否相等,不等则放入map
                        if (!objBefore.equals(objAfter)) {
                            List<Object> list = new ArrayList<Object>();
                            list.add(objBefore);
                            list.add(objAfter);
                            map.put(name, list);
                        }
                    }
                }
            }
            return map;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Compare two object and return modified fields
     * @param source source object
     * @param target target object
     * @return the modified fields and value after modify
     */
    public static Map<String,Object> getModifyContent(Object source, Object target) {
        Map<String,Object> modifies=new HashMap<>();
         /*
          process null problem, if all null means equal
          if only source is null means all modified
          if only target is null means nothing changed
         */
        if(null == source || null == target) {
            if(null==source&&null==target) return modifies;
            else if(null == target) return modifies;
            else {return mapper.convertValue(target, new TypeReference<Map<String,Object>>(){});}
        }
        // source and target must be same class type
        if(!Objects.equals(source.getClass().getName(), target.getClass().getName())){
            throw new ClassCastException("source and target are not same class type");
        }
        Map<String, Object> sourceMap= mapper.convertValue(source, new TypeReference<Map<String,Object>>(){});
        Map<String, Object> targetMap = mapper.convertValue(target, new TypeReference<Map<String,Object>>(){});
        sourceMap.forEach((k,v)->{
            Object targetValue=targetMap.get(k);
            if (!Objects.equals(v,targetValue)){modifies.put(k,targetValue);}
        });
        return modifies;
    }

    /**
     * Compare two object and return modified fields which contain in comparedProperties
     * @param source ource object
     * @param target target object
     * @param comparedProperties the fields need to be compare
     * @return the modified fields and value after modify
     */
    public static Map<String,Object> getModifyContent(Object source, Object target,Map<String,String> comparedProperties) {
        Map<String,Object> modifies=new HashMap<>();
        if(null == source || null == target) {
            if(null==source&&null==target) return modifies;
            else if(null == target) return modifies;
            else {return mapper.convertValue(target, new TypeReference<Map<String,Object>>(){});}
        }
        if(!Objects.equals(source.getClass().getName(), target.getClass().getName())){
            throw new ClassCastException("source and target are not same class type");
        }
        Map<String, Object> sourceMap= mapper.convertValue(source, new TypeReference<Map<String,Object>>(){});
        Map<String, Object> targetMap = mapper.convertValue(target, new TypeReference<Map<String,Object>>(){});
        sourceMap.forEach((k,v)->{
            if(comparedProperties!=null&&!comparedProperties.containsKey(k)){
                return;
            }
            Object targetValue=targetMap.get(k);
            if (!Objects.equals(v,targetValue)){modifies.put(k,targetValue);}
        });
        return modifies;
    }

    /**
     * Compare two object and return if equal
     * @param source source object
     * @param target target object
     * @return true-equal
     */
    public static boolean isEuqal(Object source, Object target) {
        if(null == source || null == target) {
            return false;
        }
        if(!Objects.equals(source.getClass().getName(), target.getClass().getName())){
            return false;
        }
        Map<String, Object> sourceMap= mapper.convertValue(source, new TypeReference<Map<String,Object>>(){});
        Map<String, Object> targetMap = mapper.convertValue(target, new TypeReference<Map<String,Object>>(){});
        return Objects.equals(sourceMap,targetMap);
    }

    /**
     * Compare two object and return if equal
     * @param source source object
     * @param target target object
     * @param comparedProperties only compare fields in this map
     * @return  rue-equal
     */
    public static boolean isEuqal(Object source, Object target,Map<String,String> comparedProperties) {
        if(null == source || null == target) {
            return null == source && null == target;
        }
        if(!Objects.equals(source.getClass().getName(), target.getClass().getName())){
            return false;
        }
        Map<String, Object> sourceMap= mapper.convertValue(source, new TypeReference<Map<String,Object>>(){});
        Map<String, Object> targetMap = mapper.convertValue(target, new TypeReference<Map<String,Object>>(){});
        for(String k:sourceMap.keySet()){
            if(comparedProperties!=null&&!comparedProperties.containsKey(k)){
                continue;
            }
            Object v=sourceMap.get(k);
            Object targetValue=targetMap.get(k);
            if(!Objects.equals(v,targetValue)){return false;}
        }
        return true;
    }
}
