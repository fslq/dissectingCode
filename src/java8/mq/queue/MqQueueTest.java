package java8.mq.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 生产者-消费者：阻塞队列实现
 */
public class MqQueueTest {

    static final ArrayBlockingQueue<StringBuilder> msg = new ArrayBlockingQueue<>(5);
    /**
     * 生产者 
     * @param a
     * @param b
     * @return
     */
    public static int productor(int a,int b) throws Exception{
        StringBuilder msgBody = new StringBuilder();
        msgBody.append(a).append(",").append(b);
        System.out.println("生产消息");
        //消息满了阻塞，否则，加入消息
        msg.put(msgBody);
        System.out.println("消息放入队列成功");
        return 0;
    }
    /**
     * 消费者
     * @param c
     * @param d
     * @return
     */
    public static int consumer(int c, int d) throws Exception{
        System.out.println("消费消息");
        //没有消息阻塞（监听），否则，取出并删除
        StringBuilder msgBody = msg.take();
        if (msgBody != null) {
            System.out.println("解析并处理消息");
            return Integer.valueOf(msgBody.substring(0,msgBody.indexOf(",")))+Integer.valueOf(msgBody.substring(msgBody.indexOf(",")+1,msgBody.length()));
        }
        return 0;
    }
    
    public static void main(String[] args) {
        new Thread(()->{
            int condition=0;
            while (condition<10) {
                try {
                    productor(1,condition);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }   
                condition++;             
            }
        },"生产").start();
        new Thread(()->{
            int condition=0;
            while (condition<10) {
                int result;
                try {
                    result = consumer(1,1); 
                    System.out.println("结果："+result);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                condition++;
            }
        },"消费").start();
    }

}
