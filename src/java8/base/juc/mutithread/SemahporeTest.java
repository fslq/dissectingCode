package java8.base.juc.mutithread;

import java.util.concurrent.Semaphore;

/**
 * AQS信号量机制测试：限流作用
 */
public class SemahporeTest {
    public static void main(String[] args) {
        int num =30;
        Semaphore semaphore =new Semaphore(2);//并发数限制为2
        for (int index = 0; index < num; index++) {
            final int ii=index;
            Runnable runnable = () -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                test(ii);
                semaphore.release();
                System.out.println("已经执行了："+ii+"次");
            };
            OwnThreadPool.getInstance().execute(runnable);
        }
    }
    private static void test(int num){
        System.out.println("线程："+Thread.currentThread().getName()+"执行数字："+num);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
