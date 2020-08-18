package top.glma.plus.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanToMap {

    public static <T> Map<String,Object> convert(T bean){
        Method[] methods=bean.getClass().getMethods();
        Map<String,Object> keyValues=new HashMap<>();
        try {
            for(Method method: methods){

                String methodName=method.getName();
                //反射获取属性与属性值的方法很多，以下是其一；也可以直接获得属性，不过获取的时候需要用过设置属性私有可见
                if (methodName.contains("get")){
                    //invoke 执行get方法获取属性值
                    Object value=method.invoke(bean);
                    //根据setXXXX 通过以下算法取得属性名称
                    String key=methodName.substring(methodName.indexOf("get")+3);
                    Object temp=key.substring(0,1).toString().toLowerCase();
                    key=key.substring(1);
                    //最终得到属性名称
                    key=temp+key;
                    keyValues.put(key,value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return keyValues;
    }

}
