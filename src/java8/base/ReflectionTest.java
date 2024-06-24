package java8.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射
 */
public class ReflectionTest{

    public static void main(String[] args) throws Exception {
        //反射：通过类之全路径
        Class<?> targetClass = Class.forName("java8.base.Demo");
        //强制转换，获取实例
        Demo demo = (Demo)targetClass.newInstance();
        demo.setName("我");
        demo.print("小米");
        //获取方法
        for (Method declaredMethods : targetClass.getDeclaredMethods()) {
            System.out.println(declaredMethods.getName());
        }
        //获取方法并调用
        Method targetMethod = targetClass.getDeclaredMethod("print", String.class);
        targetMethod.invoke(demo, "小红");
        //修改成员变量
        Field targetField = targetClass.getDeclaredField("name");
        targetField.setAccessible(true);
        targetField.set(demo,"他");
        //执行私有方法:不能传参数
        Method targetlMethod = targetClass.getDeclaredMethod("printl");
        targetlMethod.setAccessible(true);
        targetlMethod.invoke(demo);
    }

}

class Demo{
    private String name;
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void print(String who){
        System.out.println(this.name+" is "+who);
    }
    private void printl(){
        System.out.println(this.name+" is "+"who");
    }
}