package com.ysx.util;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 获取当前毫秒值 不准确最大有 100ms 误差
 * @Author: ysx
 */
public class CurrentTimeMillisClock {
    private volatile long now;

    private CurrentTimeMillisClock() {
        this.now = System.currentTimeMillis();
        scheduleTick();
    }

    private void scheduleTick() {
        int flushTimeMillis = 100;
        new ScheduledThreadPoolExecutor(1, runnable -> {
            Thread thread = new Thread(runnable, "current-time-millis");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(() -> now = System.currentTimeMillis(), 1, flushTimeMillis, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return now;
    }
    
    public static CurrentTimeMillisClock getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CurrentTimeMillisClock INSTANCE = new CurrentTimeMillisClock();
    }


}