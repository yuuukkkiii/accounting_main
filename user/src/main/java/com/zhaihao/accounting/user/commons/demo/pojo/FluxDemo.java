package com.zhaihao.accounting.user.commons.demo.pojo;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zh
 * @date : 2024/9/18
 * @description :
 */
public class FluxDemo {

    public static void main(String[] args) {

    }

    private static void fluxDemo() {
        Flux<Integer> concat = Flux.concat(Flux.just(1, 2, 3), Flux.just(4, 5, 6));
        Flux.range(1, 7)
                //.log()
                .filter(i -> i > 3)
                .log()
                .map(i -> "------" + i)
                .subscribe(System.out::println);
        Flux.range(1,10)
                .buffer(3)
                .map(list->{
                    List<String> strList = new ArrayList<>();
                    for (Integer integer : list) {
                        strList.add(integer+"~");
                    }
                    return strList;
                }).subscribe(System.out::println);
    }


}
