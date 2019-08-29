package com.example.threadtest.thread;

/**
 * @author ：GYB
 * @date ：Created in 2019/8/13 11:22
 * @description：TODO
 * @version: 1.0
 */
public class MyThread extends Thread{

    private int i = 10;

    // 可以自行定义锁，也可以使用实例的锁
    Object mutex = new Object();

    public void selltickets(){
        synchronized (mutex) {
            if(i>0){
                i--;
                //getName()获取线程的名字
                System.out.println(Thread.currentThread().getName()+" :"+ i);
            }
        }
    }

    @Override
    public void run() {
        while(i>0){
            selltickets();
        }
    }
}
