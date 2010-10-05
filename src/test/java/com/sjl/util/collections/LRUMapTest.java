package com.sjl.util.collections;

import static junit.framework.Assert.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import org.junit.*;

import com.sjl.util.*;

public class LRUMapTest {

	private Random random = new Random();
	
	@Test
	public void testPutAndGetWorkNormallyWhileNotAtCapacity()
	throws Exception {
		LRUMap<Integer, Integer> _map = new LRUMap<Integer, Integer>(20);
		List<Integer> _values = new ArrayList<Integer>();
		for (int i=0; i<20; i++) {
			Integer j = random.nextInt();
			_map.put(j, j);
			_values.add(j);
		}
		
		for (Integer _i : _values) {
			assertNotNull("Map should contain " + _i, _map.get(_i)); 
		}
	}
			
	@Test
	public void testPutGetAndRemoveWorkNormallyWhileNotAtCapacity()
	throws Exception {
		LRUMap<Integer, Integer> _map = new LRUMap<Integer, Integer>(20);
		List<Integer> _values = new ArrayList<Integer>();
		for (int i=0; i<20; i++) {
			Integer j = random.nextInt(20);
			_map.put(j, j);
			_values.add(j);
		}
		
		for (Integer _i : _values) {
			assertNotNull("Map should contain " + _i, _map.get(_i)); 
		}
		
		for (int i=0; i<_values.size(); i++) {
			Integer _toRemove = _values.get(i);
			_map.remove(_toRemove);
			assertNull("Expected removed (but was present): " + _toRemove, _map.get(_toRemove));
		}		
	}
	
	@Test
	public void testRemovesOldestWhenPutAtCapacity()
	throws Exception {
		final String[] _removed = new String[1];
		LRUMap<String, String> _map = new LRUMap<String, String>(20) {
			public void onRemoveOldest(String aKey, String aValue) {
				_removed[0] = aKey;
			}
		};
		List<String> _values = new ArrayList<String>();
		for (int i=0; i<20; i++) {
			String _s = ""+i;
			_map.put(_s, _s);
			_values.add(_s);
		}
		
		for (int i=0; i<20; i++) {
			String _in = (i+20)+"";
			_values.add(_in);			
			String _out = _values.remove(0);
						
			_map.put(_in, _in);
			assertNotNull(_map.get(_in)); 
			assertEquals(_out, _removed[0]);
			assertNull(_map.get(_out)); // oldest is removed
		}			
	}
	
	@Test
	public void testMaintainsMaxSizeUnderMultiThreadedAccess()
	throws Exception {			
		final int _threadCount = 10;
		final int _iterations = 1000;
		final int _mapSize = 100;
		
		final AtomicInteger _removes = new AtomicInteger(0);
		final LRUMap<String, String> _map = new LRUMap<String, String>(_mapSize) {			
			public void onRemoveOldest(String aKey, String aValue) {
				_removes.incrementAndGet();
			}			
		};
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread anT, Throwable anE) {
				anE.printStackTrace();
			}			
		});
		
		List<Thread> _threads = new ArrayList<Thread>(_threadCount);
		for (int i=0; i<_threadCount; i++) {
			final int _n = i;
			_threads.add(new Thread() {				
				@Override
				public void run() {
					int _start = (_n*_iterations);
					int _end = ((_n+1)*_iterations);
					for (int j=_start; j<_end; j++) {
						String _k = "" + j;
						_map.put(_k, _k);
					}
				}				
			});
		}
		
		StopWatch _sw = new StopWatch().start();
		
		for (Thread _t : _threads) {
			_t.start();
		}
		
		for (Thread _t : _threads) {
			_t.join();
		}
		
//		System.out.println(_sw.stop());
		_sw.stop();
		
		assertEquals(_removes.get(), ((_threadCount * _iterations) - _mapSize));
		assertEquals(_mapSize, _map.size());
		assertEquals(100, _map.toString().split(",").length);
	}
}
