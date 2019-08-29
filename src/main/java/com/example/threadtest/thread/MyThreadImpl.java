package com.example.threadtest.thread;

import static java.lang.Thread.sleep;

/**
 * @author ：GYB
 * @date ：Created in 2019/8/13 12:57
 * @description：TODO
 * @version: 1.0
 */
public class MyThreadImpl implements Runnable {

    private int tickets = 100;

    @Override
    public void run() {
        while (tickets > 0) {
            synchronized (MyThreadImpl.class) {
                if (tickets > 0) {
                    System.out.println(Thread.currentThread().getName() + "正在卖第" + tickets + "张");
                    tickets--;
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }



}