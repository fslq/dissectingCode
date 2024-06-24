package java8.base;
import java.util.*;
/**
 * hash 相关
 */
public class HashMapTest{
    
    public static void main(String[] args){
        Test1 test1 = new Test1("t1");
        Test1 test2 = new Test1("t1");
        Map<Test1,Integer> map = new HashMap();
        map.put(test1, 1);
        //Key相同
        if (test1.equals(test2)) {
            //按照hashmap的定义：Key唯一
            System.out.println(map.containsKey(test2)==Boolean.TRUE); 
        }
    }
}
// 1.重写equals,没有重写hashcode方法
class Test1{
    private String name;
    public Test1(String name){
        this.name=name;
    }
    @Override
    public boolean equals(Object obj){
        //值与引用
        if(this==obj){
            return true;
        }
        //非空且类型不同
        if(obj==null||getClass()!=obj.getClass()){
            return false;
        }
        //重写：值比较
        Test1 oTest1=(Test1)obj;
        return this.name==oTest1.name;
    }
    /**
     * 重写hashcode
     */
    // @Override
    // public int hashCode(){
    //     return name.hashCode();
    // }
}
