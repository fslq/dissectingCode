package java8.base;
public class StringTest{
    public static void main(String[] args){
        String s1 = "abc";//字符串常量池引用指向堆
        System.out.println(s1==s1.intern());//true
        String s2 = "abc";//取得字符串常量池
        String s3 = new String("abc");//在堆中新建了一个引用地址
        System.out.println(s1==s2);//true
        System.out.println(s1==s3);//false

    }
}