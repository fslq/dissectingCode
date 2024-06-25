package java8.base.proxy.jdk;
/**
 * 实现类
 */
public class SmsImpl implements SmsInterface {
    @Override
    public void send(String message){
        System.out.println("发送信息："+message);
    };
}