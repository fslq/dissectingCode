package java8.base;
/**
 * java 基本数据类型相关：
 *  1.比较
 */
class BasicDataType {
    // 整型

    public static void main(String arg[]){
            
        int i1=1,i2=1;
        Integer i3=1,i4=1;
        int i5=127;
        Integer i6 = 127;
        int i7=128;
        Integer i8 = Integer.valueOf(128);

        System.out.println("i1=i2 is "+(i1==i2));//true
        System.out.println("i3=i4 is "+(i3==i4));//true
        System.out.println("i5=i6 is "+(i5==i6));//true
        System.out.println("i7=i8 is "+(i7==i8));//竟然是 true，与所学相悖
    }
    
}