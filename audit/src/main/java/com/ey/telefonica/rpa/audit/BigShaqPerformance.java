package com.ey.telefonica.rpa.audit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.descriptive.SynchronizedSummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BigShaqPerformance implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(BigShaqPerformance.class);

    private static BigShaqPerformance instance;
    private SynchronizedSummaryStatistics checkDaStatistacsPerMinute = new SynchronizedSummaryStatistics();
    private SynchronizedSummaryStatistics checkDaStatistacsPerSecond = new SynchronizedSummaryStatistics();
    private int basicSecondCounter = 0;


    private BigShaqPerformance() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(this , 0, 1, TimeUnit.SECONDS);
    }

    public static BigShaqPerformance getInstance() {
        if (instance == null) {
            instance = new BigShaqPerformance();
        }
        return instance;
    }

    @Override
    public void run() {
        logger.debug("LastSecond: {}", checkDaStatistacsPerSecond.getSummary());
        checkDaStatistacsPerSecond.clear();
        if (basicSecondCounter >= 59) {
            logger.debug("LastMinute: {}", checkDaStatistacsPerMinute.getSummary());
            basicSecondCounter = 0;
            checkDaStatistacsPerMinute.clear();
        } else {
            basicSecondCounter++;
        }

    }

    public void newResponse(long elapsed) {
        checkDaStatistacsPerMinute.addValue(elapsed);
        checkDaStatistacsPerSecond.addValue(elapsed);
    }
}
