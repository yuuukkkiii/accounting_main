package com.zhaihao.accounting.user.commons.demo.pojo;

import lombok.Data;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author :zh
 * @date : 2024/9/13
 * @description :
 */
@Data
public class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {

    private String prefix;

    private Flow.Subscription subscription;

    public MyProcessor(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println(prefix+"processor订阅绑定完成");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        System.out.println(prefix+"processor拿到数据：" + item);
        item += prefix;
        submit(item);
        subscription.request(1);
        if(prefix.equals("嘻嘻")){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
