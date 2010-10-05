package com.sjl.util;

import java.text.*;

public class StopWatch
{
    private long start = 0;
    private long elapsed = 0;
    
    public StopWatch start() {
        start = System.nanoTime();
        return this;
    }
    
    public StopWatch stop() {
        if (start > 0)
            elapsed += System.nanoTime() - start;
        start = 0;
        return this;
    }
    
    public long getTime() {
        return elapsed;
    }
    
    public String toString() {
        return NumberFormat.getInstance().format(getTime()) + "ns";
    }
}
