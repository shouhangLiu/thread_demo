package com.example.thread_demo.single;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <p>
 * 创建单线程的方式
 * 1.继承Thread类
 * 2.实现Runnable接口
 * 3.实现Callable接口 和 Runnable的区别是一个是call() 一个是run() 一个有返回可以抛出异常 一个没有返回，不可以抛出异常
 **/
public class ThreadSingleDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 继承Thread类，创建线程
        ThreadOfThread threadOfThread = new ThreadOfThread();
        threadOfThread.start();
        //2.实现runnable接口，创建线程,然后创建Thread对象，然后调用start方法
        InterfaceOfThread interfaceOfThread = new InterfaceOfThread();
        Thread thread = new Thread(interfaceOfThread);
        thread.start();
        //3.实现callable接口，重写call方法，然后创建Thread对象，然后调用start方法
        CallableOfThread callableOfThread = new CallableOfThread();
        FutureTask<String> futureTask = new FutureTask<>(callableOfThread);
        Thread thread1 = new Thread(futureTask);
        thread1.start();
        System.out.println(futureTask.get());


    }

}

/**
 * 继承Thread类，重写run方法
 */
class ThreadOfThread extends Thread {

    @Override
    public void run() {
        System.out.println("继承Thread类");
    }
}

/**
 * 实现Runnable接口，重写run方法
 */
class InterfaceOfThread implements Runnable {
    @Override
    public void run() {
        System.out.println("实现Runnable接口");
    }
}

class CallableOfThread implements Callable<String> {
    @Override
    public String call() {
        return "实现Callable接口";
    }
}