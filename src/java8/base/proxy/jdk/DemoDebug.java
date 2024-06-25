package java8.base.proxy.jdk;
/**
 * AOP 实现基于接口的类之代理用jdk方式，否则用cglib 详见：https://javaguide.cn/java/basis/proxy.html#_3-1-jdk-%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%E6%9C%BA%E5%88%B6
 */
public class DemoDebug {
    
    public static void main(String[] args) {
        SmsInterface sms = (SmsInterface)ProxyFactory.getProxyInstance(new SmsImpl());
        sms.send("Hello");
    }
}
