package com.zhaihao.accounting.user.commons.demo.pojo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

/**
 * @author :zh
 * @date : 2024/9/12
 * @description :
 */
public class ReactorDemo {

    public static void main(String[] args) throws IOException {
        Flux<Integer> just = Flux.range(1, 7);
        just.delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> {
                    System.out.println("流正常结束");
                }).doOnCancel(() -> {
                    System.out.println("流已被取消");
                }).doOnError(throwable -> {
                    System.out.println("流出错" + throwable);
                }).doOnNext(integer -> {
                    System.out.println("");
                });
        just.subscribe(e -> System.out.println("e1 = " + e));

        // Flux<Long> flux= Flux.interval(Duration.ofSeconds(1));
        // flux.subscribe(System.out::println);

        // Mono<Integer> just1 =Mono.just(1);
        // just1.subscribe(System.out::println);
        System.in.read();

    }
}
