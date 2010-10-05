package com.sjl.util;

public class Range {
	
    private int start;    
    private int batch;
    private int total;        
    
    public Range(int aStart, int aBatchSize) {
        this(aStart, aBatchSize, Integer.MAX_VALUE);
    }
    
    public Range(int aStart, int aBatchSize, int aTotal) {
        start = (aStart < 0) ? 0 : aStart;            
        batch = Math.max(1, aBatchSize);        
        total = Math.max(0, aTotal);
    }
    
    public boolean hasNext() {
        return (start+batch) < total;
    }
    
    public boolean hasPrevious() {
        return start > 0;
    }
    
    public Range next() {
        return new Range(Math.min(getEnd()+1, total), batch, total);
    }
    
    public Range previous() {
        return new Range(Math.max((start-batch), 0), batch, total);
    }
    
    public int getStart() {
        return start;
    }    
    
    public int getBatchSize() {
        return batch;
    }
    
    public int getSize() {
    	if (getEnd() == 0)
    		return 0;
    	
    	if (hasNext())
    		return batch;
    	
    	return total-start;
    }
    
    public int getEnd() {
        return Math.max(0, (Math.min(total, start+batch-1)));
    }
    
    public int getTotal() {
        return total;
    }
    
    public String toString() {
        return String.format("%s to %s of %s", (start+1), (Math.min(getEnd()+1, total)), total);
    }
    
    public int hashCode() {
    	return toString().hashCode() ^ getClass().hashCode();
    }
    
    public boolean equals(Object anObject) {
        if (anObject instanceof Range) {
            Range _other = (Range) anObject;
            return (start == _other.start) &&
                    (batch == _other.batch) &&
                    (total == _other.total);
        }
        return false;
    }
}
