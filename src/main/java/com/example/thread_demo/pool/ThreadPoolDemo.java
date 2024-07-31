package com.example.thread_demo.pool;

import java.util.concurrent.*;

/**
 * 通过线程池创建线程
 * <p>
 * 创建线程池的两大方法
 * 1、使用ThreadPoolExecutors创建
 * 2、使用Executors创建
 * <p>
 * <p>
 * 注：强制线程池不允许使用Executors创建，避免资源耗尽，推荐使用ThreadPoolExecutors创建线程池
 * 弊端如下：
 * 1.FixedThreadPool和SingleThreadPool允许的最大队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
 * 2.CachedThreadPool允许的创建线程数量为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
 * <p>
 * 线程池的优点如下：
 * 1.资源节约：通过复用线程，减少了线程创建和销毁的开销
 * 2.性能提升：任务可以快速启动，因为线程已经存在
 * 3.管理增强：线程池提供了更多的可控性，如线程数量，任务队列
 **/
public class ThreadPoolDemo {

    public static void main(String[] args) {
        //1.ThreadPoolExecutors
        ThreadPoolExecutorsDemo threadPoolExecutorsDemo = new ThreadPoolExecutorsDemo();
        threadPoolExecutorsDemo.usePoolDemo();
        //2/fixThreadPool
        FixedTheadPoolDemo fixedTheadPoolDemo = new FixedTheadPoolDemo();
        fixedTheadPoolDemo.usePoolDemo();


    }

}

/**
 * 使用 ThreadPoolExecutors 创建线程池
 * 1.各项参数
 * 1.1 corePoolSize：线程池中的常驻核心线程数
 * 1.2 maximumPoolSize：线程池中能够容纳同时执行的最大线程数
 * 1.3 keepAliveTime：当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
 * 1.4 unit：存活时间单位，keepAliveTime参数单位
 * 1.5 workQueue：任务队列，被提交但尚未被执行的任务
 * 1.6 threadFactory：线程工厂，用于创建线程
 * 1.7 handler：拒绝策略，当任务太多来不及处理时，如何拒绝任务
 */
class ThreadPoolExecutorsDemo {

    /**
     * 关键节点执行流程
     * 1.当线程数小于核心线程数时，直接创建线程执行任务；
     * 2.当线程数大于等于核心线程数，并且队列未满时，将任务放进任务队列
     * 3.当线程数大于等于核心线程数，并且队列已满时，
     * 若总线程数小于最大线程数，则创建线程，
     * 若总线程数大于等于最大线程数，抛出异常，拒绝任务
     */
    void usePoolDemo() {
        //创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 100, 100,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 100; i++) {
            System.out.println("添加进线程池" + i);
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "执行任务");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}

/**
 * 固定大小的线程池，可控制并发线程数，超出的线程会在队列中等待
 * 一般用于WEB削峰 如果长时间持续高峰会导致队列阻塞
 * 1.submit可以用于提交任务返回结果，并且开业接收Runnable和Callable接口的任务
 * 2.executor只适合Runnable并且没有返回值的任务
 */
class FixedTheadPoolDemo {
    void usePoolDemo() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("任务执行，线程" + Thread.currentThread().getName());
            }
        };
        fixedThreadPool.submit(runnable);
        fixedThreadPool.execute(runnable);
        fixedThreadPool.execute(runnable);
        System.out.println("主线程结束");
    }
}

/**
 * 可扩展线程池，核心线程未0 最大INTEGER 线程数无限
 */
class CachedThreadPoolDemo {

}

/**
 * 单线程线程池，任务顺序执行，先提交先执行
 */
class SingleThreadPoolDemo{

}
