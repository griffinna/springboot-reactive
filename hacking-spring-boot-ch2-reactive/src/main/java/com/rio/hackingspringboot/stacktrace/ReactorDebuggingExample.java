package com.rio.hackingspringboot.stacktrace;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

public class ReactorDebuggingExample {
    public static void main(String[] args) {

        // 리액터가 처리 흐름 어셈블 시점에서의 호출부 세부정보를 수집하고
        // 구독해서 실행되는 시점에 세부정보를 넘겨준다.
        // 성능 문제를 일으킬 수 있으므로 실제 운영환경에서 사용 하면 안됨
        Hooks.onOperatorDebug();

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
*
Exception in thread "main" java.lang.IndexOutOfBoundsException
	at reactor.core.publisher.MonoElementAt$ElementAtSubscriber.onComplete(MonoElementAt.java:160)
	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
Assembly trace from producer [reactor.core.publisher.MonoElementAt] :
	reactor.core.publisher.Flux.elementAt(Flux.java:4715)
	com.rio.hackingspringboot.stacktrace.ReactorDebuggingExample.main(ReactorDebuggingExample.java:19)
Error has been observed at the following site(s):
	|_   Flux.elementAt ⇢ at com.rio.hackingspringboot.stacktrace.ReactorDebuggingExample.main(ReactorDebuggingExample.java:19)
	|_ Mono.subscribeOn ⇢ at com.rio.hackingspringboot.stacktrace.ReactorDebuggingExample.main(ReactorDebuggingExample.java:23)
Stack trace:
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
		at com.rio.hackingspringboot.stacktrace.ReactorDebuggingExample.main(ReactorDebuggingExample.java:24)

Process finished with exit code 1

* */