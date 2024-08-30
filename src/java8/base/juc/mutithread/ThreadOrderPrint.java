package java8.base.juc.mutithread;
/**
 * 三个线程交替打印ABC
 */
public class ThreadOrderPrint {
    private static final Object lock = new Object();
    private static int count = 0;
    public static void main(String [] args){
       Thread t1 = new Thread(() -> {
            while(count<9){ 
                synchronized(lock){
                    if(count%3==0){
                        System.out.print("A");
                        //成功执行
                        count++;
                        //唤醒所有线程
                        lock.notifyAll();
                    }else{
                        try{
                            //阻塞当前线程
                            lock.wait();
                        }catch(InterruptedException e){
                            Thread.currentThread().interrupt();
                        }
                    }
                }

            }
       });
       Thread t2 = new Thread(()->{
        while (count<9) {
            synchronized(lock){
                if(count%3==1){
                    System.out.print("B");
                    count++;
                    lock.notifyAll();
                }else{
                    try{
                        lock.wait();
                    }catch(Exception e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
       });
       Thread t3 = new Thread(()->{
        while (count<9) {
            synchronized(lock){
                if(count%3==2){
                    System.out.print("C");
                    count++;
                    lock.notifyAll();
                }else{
                    try{
                        lock.wait();
                    }catch(Exception e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
       });
       t1.start();
       t2.start();
       t3.start();
    }
}
