package com.example.threadtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadTestApplication.class, args);
        test2();
    }

    public static void test2(){
        //只创建一个实例
        MyThreadImpl threadImpl = new MyThreadImpl();
        //将上面创建的唯一实例放入多个线程中，Thread类提供了多个构造方法，见下图（构造方法摘要）
        Thread thread1 = new Thread(threadImpl, "窗口1");
        Thread thread2 = new Thread(threadImpl, "窗口2");
        thread1.start();
        thread2.start();
    }

}
