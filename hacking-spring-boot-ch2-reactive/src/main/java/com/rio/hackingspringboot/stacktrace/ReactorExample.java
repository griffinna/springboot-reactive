package com.rio.hackingspringboot.stacktrace;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

public class ReactorExample {
    public static void main(String[] args) {
        Mono<Integer> source;
        if (new Random().nextBoolean()) {
            source = Flux.range(1, 10).elementAt(5);
        } else {
            source = Flux.just(1, 2, 3, 4).elementAt(5);
        }

        source
            .subscribeOn(Schedulers.parallel())
            .block();
    }
}

/*
* Exception in thread "main" java.lang.IndexOutOfBoundsException
	at reactor.core.publisher.MonoElementAt$ElementAtSubscriber.onComplete(MonoElementAt.java:160)
	at reactor.core.publisher.FluxArray$ArraySubscription.fastPath(FluxArray.java:177)
	at reactor.core.publisher.FluxArray$ArraySubscription.request(FluxArray.java:97)
	at reactor.core.publisher.MonoElementAt$ElementAtSubscriber.request(MonoElementAt.java:99)
	at reactor.core.publisher.MonoSubscribeOn$SubscribeOnSubscriber.trySchedule(MonoSubscribeOn.java:189)
	at reactor.core.publisher.MonoSubscribeOn$SubscribeOnSubscriber.onSubscribe(MonoSubscribeOn.java:134)
	at reactor.core.publisher.MonoElementAt$ElementAtSubscriber.onSubscribe(MonoElementAt.java:114)
	at reactor.core.publisher.FluxArray.subscribe(FluxArray.java:53)
	at reactor.core.publisher.FluxArray.subscribe(FluxArray.java:59)
	at reactor.core.publisher.Mono.subscribe(Mono.java:4046)
	at reactor.core.publisher.MonoSubscribeOn$SubscribeOnSubscriber.run(MonoSubscribeOn.java:126)
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:84)
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:37)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:835)
	Suppressed: java.lang.Exception: #block terminated with an error
		at reactor.core.publisher.BlockingSingleSubscriber.blockingGet(BlockingSingleSubscriber.java:99)
		at reactor.core.publisher.Mono.block(Mono.java:1703)
		at com.rio.hackingspringboot.stacktrace.ReactorExample.main(ReactorExample.java:20) // Flux 생성 지점까지 출력 불가능
* */