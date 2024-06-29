package java8.base.juc.mutithread.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class OwnThreadFactory implements ThreadFactory {
    // 自定义线程名称
    private String threadNamePrefix = "Own-Thread-DefaultName-";
    // 生成（工厂）线程序号
    private static AtomicInteger threadNum = new AtomicInteger(1);
    public OwnThreadFactory(String threadNamePrefix){
        this.threadNamePrefix=threadNamePrefix;
    }
    @Override
    public Thread newThread(Runnable r) {
        // TODO Auto-generated method stub
        Thread thread = new Thread(r,threadNamePrefix+"-"+threadNum.getAndIncrement());
        //设置线程优先级
        thread.setPriority(Thread.NORM_PRIORITY);
        System.out.println("生产线程："+thread.getName()+"完成");
        return thread;
    }
    
}
