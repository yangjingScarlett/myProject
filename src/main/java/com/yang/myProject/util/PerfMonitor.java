package com.yang.myProject.util;

/**
 * @author Yangjing
 */
public class PerfMonitor {
    private long startTime;
    private long stopTime;

    private PerfMonitor() {

    }

    public static PerfMonitor startInstance() {
        PerfMonitor perfMonitor = new PerfMonitor();
        perfMonitor.startTime = System.nanoTime();
        return perfMonitor;
    }

    public void restart() {
        startTime = System.nanoTime();
    }

    public void stop() {
        stopTime = System.nanoTime();
    }

    /**
     * Return elapsed time in micro-seconds unit.
     *
     * @return elapsed time in micro-seconds unit
     */
    public double getElapsedTime() {
        return (stopTime - startTime) / 1000.0 / 1000.0;
    }
}
