package com.zhaihao.accounting.user.commons.demo;

import com.zhaihao.accounting.user.commons.demo.pojo.MyProcessor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author :zh
 * @date : 2024/9/12
 * @description :
 */
public class FlowDemo {

    public static void main(String[] args) {
        // jvm底层对于整个发布订阅关系，做好了异步 + 缓存区处理
        // 定义一个发布者
        // Flow.Publisher<String> publisher = new Flow.Processor<>() {
        //     private Flow.Subscriber<? super String> subscriber;
        //     @Override
        //     public void subscribe(Flow.Subscriber<? super String> subscriber) {
        //         this.subscriber = subscriber;
        //     }
        // }  等价于下面的写法
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        MyProcessor myProcessor = new MyProcessor("哈哈");

        MyProcessor myProcessor2 = new MyProcessor("嘻嘻");

        MyProcessor myProcessor3 = new MyProcessor("啊啊");

        // 定义一个订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread() + "订阅开始了" + subscription);
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println(Thread.currentThread() + "订阅者，接受到数据" + item);
                if (item.equals("p-7")) {
                    subscription.cancel();
                }
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread() + "订阅者，接受到错误:" + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("接收到完成信号");
            }
        };


        publisher.subscribe(myProcessor);

        myProcessor.subscribe(myProcessor2);

        myProcessor2.subscribe(myProcessor3);

        myProcessor3.subscribe(subscriber);

        for (int i = 0; i < 10; i++) {
            // if (i == 9) {
            //     publisher.closeExceptionally(new RuntimeException("数字为9"));
            // } else {
            publisher.submit("p-" + i);
            // }
        }


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
