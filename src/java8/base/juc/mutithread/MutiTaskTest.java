package java8.base.juc.mutithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MutiTaskTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //创建任务
        List<CompletableFuture<Void>> tasks = new ArrayList<>();
            for(int i=0;i<20;i++){
                final int ii=i;
                tasks.add(CompletableFuture.runAsync(()->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务"+ii+"执行线程："+Thread.currentThread().getName());
                },OwnThreadPool.getInstance())
            );
        };
        
        //执行任务
        CompletableFuture<Void> allOf = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allOf.get();
        System.out.println("所有任务执行完成");
    }
}
