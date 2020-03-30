package com.yangzhichao.yzclib.future;

import com.yangzhichao.yzclib.chain.Student;
import com.yangzhichao.yzclib.chain.StudentA;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompleTableFutureTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //五返回值的future
//        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
//
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("runAsync");
//
//        });
//        System.out.println("---------");
//        runAsync.get();
//
//    }

        CompletableFuture<Student> objectCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new StudentA();
        });

        objectCompletableFuture.whenComplete((t, u) -> {
            t.humanSay();

        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return null;
        }).get();

        System.out.println('a');
        System.out.println("a");


    }

}
