package java8.base.juc.mutithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import java8.base.juc.mutithread.factory.OwnThreadFactory;

public class OwnThreadPool {
    //核心线程数
    private static int corePoolSize =3;
    //最大线程数：线程队列用完之后允许再建的线程数（maxinumPoolSize-corePoolSize=5）
    private static int maximumPoolSize=8;
    //空闲线程 存活时间
    private static long keepAliveTime=1L;
    //空闲线程 存活时间单位
    private static TimeUnit timeUnit = TimeUnit.SECONDS;
    //线程池实例
    private volatile static ThreadPoolExecutor instance = null;

    public static ThreadPoolExecutor getInstance(){
        //单例模式-双重检测锁
        if(instance==null){
            synchronized(OwnThreadPool.class){
                if(instance==null){
                    instance = new ThreadPoolExecutor(
                        corePoolSize,
                        maximumPoolSize, 
                        keepAliveTime, 
                        timeUnit, 
                        new ArrayBlockingQueue(10),
                        new OwnThreadFactory("MyThreadPool"),
                        new CallerRunsPolicy()//当线程池中的线程数达到其最大容量，并且工作队列也满了，无法再接受新的任务时，使用CallerRunsPolicy策略会将任务交由调用者线程（即提交任务的线程）来执行。如果调用者线程已经在执行一个任务，则会创建一个新线程来执行被拒绝的任务。
                        );
                }
            }
        }
        return instance;
    }

}