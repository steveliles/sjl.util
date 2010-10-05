package com.sjl.util;

import static org.junit.Assert.*;

import org.junit.*;

public class RangeTest
{
	@Test
    public void testSimpleCases()
    throws Exception
    {
        Range _r = new Range(0, 10);
        assertEquals(0, _r.getStart());
        assertEquals(9, _r.getEnd());
        assertEquals(10, _r.getBatchSize());
        assertEquals(10, _r.getSize());
        assertEquals(Integer.MAX_VALUE, _r.getTotal());
        assertTrue(_r.hasNext());
        assertFalse(_r.hasPrevious());
        assertEquals("1 to 10 of " + Integer.MAX_VALUE, _r.toString());
        
        _r = new Range(10, 10);
        assertEquals(10, _r.getStart());
        assertEquals(19, _r.getEnd());
        assertEquals(10, _r.getBatchSize());
        assertEquals(10, _r.getSize());
        assertEquals(Integer.MAX_VALUE, _r.getTotal());
        assertTrue(_r.hasNext());
        assertTrue(_r.hasPrevious());
        assertEquals("11 to 20 of " + Integer.MAX_VALUE, _r.toString());
        
        _r = new Range(0, 10, 34);
        assertEquals(0, _r.getStart());
        assertEquals(9, _r.getEnd());
        assertEquals(10, _r.getBatchSize());
        assertEquals(10, _r.getSize());
        assertEquals(34, _r.getTotal());
        assertTrue(_r.hasNext());
        assertFalse(_r.hasPrevious());
        assertEquals("1 to 10 of 34", _r.toString());
        
        _r = new Range(10, 10, 34);
        assertEquals(10, _r.getStart());
        assertEquals(19, _r.getEnd());
        assertEquals(10, _r.getBatchSize());
        assertEquals(10, _r.getSize());
        assertEquals(34, _r.getTotal());
        assertTrue(_r.hasNext());
        assertTrue(_r.hasPrevious());
        assertEquals("11 to 20 of 34", _r.toString());
        
        _r = new Range(10, 10, 16);
        assertEquals(10, _r.getStart());
        assertEquals(16, _r.getEnd());
        assertEquals(10, _r.getBatchSize());
        assertEquals(6, _r.getSize());
        assertEquals(16, _r.getTotal());
        assertFalse(_r.hasNext());
        assertTrue(_r.hasPrevious());
        assertEquals("11 to 16 of 16", _r.toString());
    }
    
	@Test
    public void testAlwaysMakesAValidRange()
    throws Exception
    {
        Range _r = new Range(-20, -6);
        assertEquals(0, _r.getStart());
        assertEquals(0, _r.getEnd());
        assertEquals(1, _r.getBatchSize());
        assertEquals(0, _r.getSize());
        assertTrue(_r.hasNext());
        assertFalse(_r.hasPrevious());        
        assertEquals(_r, _r.previous());
        
        _r = new Range(-3,-3,-3);
        assertEquals(0, _r.getStart());        
        assertEquals(0, _r.getEnd());
        assertEquals(1, _r.getBatchSize());        
        assertEquals(0, _r.getSize());
        assertFalse(_r.hasNext());
        assertFalse(_r.hasPrevious());    
        assertEquals(_r, _r.next());
        assertEquals(_r, _r.previous());
    }
    
	@Test
    public void testGetNextReturnsValidRange()
    throws Exception
    {
        Range _r = new Range(0, 10);
        Range _r2 = _r.next();
        assertEquals(10, _r2.getStart());        
        assertEquals(19, _r2.getEnd());
        assertEquals(10, _r2.getBatchSize());
        assertEquals(10, _r2.getSize());
        
        _r = new Range(0, 10, 14);
        _r2 = _r.next();
        assertEquals(10, _r2.getStart());        
        assertEquals(14, _r2.getEnd());
        assertEquals(10, _r2.getBatchSize());
        assertEquals(4, _r2.getSize());
    }
    
	@Test
    public void testGetPreviousReturnsValidRange()
    throws Exception
    {
        Range _r = new Range(10, 10);
        Range _r2 = _r.previous();
        assertEquals(0, _r2.getStart());        
        assertEquals(9, _r2.getEnd());
        assertEquals(10, _r2.getBatchSize());
        assertEquals(10, _r2.getSize());
        
        _r = new Range(0, 10, 14);
        _r2 = _r.previous();
        assertEquals(_r, _r2);    
        
        _r = new Range(3, 10, 14);
        _r2 = _r.previous();        
        assertEquals(0, _r2.getStart());
        assertEquals(9, _r2.getEnd());
        assertEquals(10, _r2.getBatchSize());        
        assertEquals(10, _r2.getSize());
    }
    
}
