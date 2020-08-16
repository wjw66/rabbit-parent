package com.wjw.factory.simplefactory;

import com.google.common.collect.Lists;
import com.wjw.api.Message;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author : wjwjava01@163.com
 * @date : 10:23 2020/8/15
 * @description :
 */
public class CompletableFutureTest {
    private static List<Message> list = Lists.newArrayList(new Message());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        String str = "";
        CompletableFuture<String> async = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenApplyAsync(p -> {
                    p = p + "_Word";
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return p;
                }).thenApplyAsync(String::toUpperCase);

        String s = async.get();
        System.out.println(s);
    }
}
