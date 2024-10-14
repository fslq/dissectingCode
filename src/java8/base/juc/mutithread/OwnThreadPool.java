package java8.base.juc.mutithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
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
        if(instance==null){//首次检查：在没有进入同步块之前进行第一次null检查。如果instance已经被初始化，那么多个线程可以直接返回已有的实例，而无需进入开销较大的同步块，从而提高了程序的性能。这对于频繁调用获取单例实例的场景非常重要，可以避免不必要的线程阻塞和同步开销。
            synchronized(OwnThreadPool.class){
                if(instance==null){//第二次检查：在进入同步块后再次检查instance是否为null。这是因为可能有多个线程同时在首次检查时发现instance为null，然后都试图进入同步块。如果没有第二次检查，那么即使第一个进入同步块的线程已经初始化了instance，后续进入同步块的线程仍然会重复进行初始化操作。第二次检查确保只有在instance确实为null时才进行初始化，避免了重复初始化的问题
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