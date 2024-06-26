package java8.base.juc.mutithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MutiTaskTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //创建任务
        List<CompletableFuture<String>> tasks = new ArrayList<>();
            for(int i=0;i<20;i++){
                final int ii=i;
                tasks.add(CompletableFuture.supplyAsync(()->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务"+ii+"执行线程："+Thread.currentThread().getName());
                    //模拟异常
                    if(ii==3){
                       int b= ii/0;
                    }
                    return "任务"+ii+"执行完成";
                },OwnThreadPool.getInstance())
            );
        };
        
        //执行任务
        CompletableFuture<Void> allOf = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allOf.whenComplete((res,ex)->{
            System.out.println("当前执行的是："+res);
            System.out.println("异常打印线程："+Thread.currentThread().getName()+" 异常："+ex.toString());
        });
        System.out.println("所有任务执行完成");
        tasks.forEach(t->{
            try {
                System.out.println("每个任务返回值："+t.get());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        OwnThreadPool.getInstance().shutdown();
    }
}
