package com.example.threadtest;

import junit.textui.TestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.Thread.sleep;

/**
 * @author ：GYB
 * @date ：Created in 2019/8/13 11:25
 * @description：TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Test
    public void test1() {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        //给线程命名
        myThread1.setName("线程1");
        myThread2.setName("线程2");
        myThread1.start();
        myThread2.start();
    }

    @Test
    public void test2() {

        Thread t = Thread.currentThread();
        System.out.println("Thread name:"+t.getName());
        System.out.println("Thread.isDaemon="+t.isDaemon());;
        //只创建一个实例
        MyThreadImpl threadImpl = new MyThreadImpl();
        //将上面创建的唯一实例放入多个线程中，Thread类提供了多个构造方法，见下图（构造方法摘要）
        Thread thread1 = new Thread(threadImpl, "窗口1");
        Thread thread2 = new Thread(threadImpl, "窗口2");
//        thread1.setDaemon(true);
//        thread2.setDaemon(true);
        thread1.start();
        thread2.start();
        System.out.println("thread1.isDaemon() = " + thread1.isDaemon());
        try {
            //利用这种方法阻塞进程，是使得线程可以跑完
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.sleep(1000*10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void test3() {
        try {
            System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
            sleep(5000);
            System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        //创建用户线程(非守护线程)
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 99990; i++) {
                    try {
                        Thread.sleep(1000*1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("用户线程，i = " + i);
                }
            }
        });
        thread.start();
        for (int i = 0; i < 9; i++) {
            System.out.println("i = " + i);
        }
        System.out.println("主线程结束======");
    }

    //多线程的测试只能在main方法中测试，不然在test里子线程没跑完方法就结束了
    public static void main(String[] args) {
        //只创建一个实例
        MyThreadImpl threadImpl = new MyThreadImpl();
        //将上面创建的唯一实例放入多个线程中，Thread类提供了多个构造方法，见下图（构造方法摘要）
        Thread thread1 = new Thread(threadImpl, "窗口1");
        Thread thread2 = new Thread(threadImpl, "窗口2");
        thread1.setDaemon(true);
//        thread2.setDaemon(true);
        thread1.start();
        thread2.start();
    }

    @Test
    public void test6() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //只创建一个实例
                MyThreadImpl threadImpl = new MyThreadImpl();
                //将上面创建的唯一实例放入多个线程中，Thread类提供了多个构造方法，见下图（构造方法摘要）
                Thread thread1 = new Thread(threadImpl, "窗口1");
                Thread thread2 = new Thread(threadImpl, "窗口2");
                thread1.start();
                thread2.start();
                System.out.println("thread1.isDaemon() = " + thread1.isDaemon());
            }
        });
        //创建守护线程执行测试方法，不然主线程销毁，测试类跑不完就结束了
        thread.setDaemon(true);
        thread.start();
        thread.join();
    }
}
