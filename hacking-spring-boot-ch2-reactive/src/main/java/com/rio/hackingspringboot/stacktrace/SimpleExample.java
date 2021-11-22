package com.rio.hackingspringboot.stacktrace;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        List<Integer> source;
        if (new Random().nextBoolean()) {
            source = IntStream.range(1, 11).boxed()
                    .collect(Collectors.toList());
        } else {
            source = Arrays.asList(1, 2, 3, 4);
        }

        try {
            executor.submit(() -> source.get(5)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

/*
* java.util.concurrent.ExecutionException: java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 4
	at java.base/java.util.concurrent.FutureTask.report(FutureTask.java:122)
	at java.base/java.util.concurrent.FutureTask.get(FutureTask.java:191)
	at com.rio.hackingspringboot.stacktrace.SimpleExample.main(SimpleExample.java:25)       // 주석 맨 아래에 위치
Caused by: java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 4
	at java.base/java.util.Arrays$ArrayList.get(Arrays.java:4372)
	at com.rio.hackingspringboot.stacktrace.SimpleExample.lambda$main$0(SimpleExample.java:25)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:835)
* */