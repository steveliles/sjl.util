package com.sjl.util.testing;

import java.text.*;
import java.util.concurrent.atomic.*;

import com.sjl.util.collections.*;

public class ThroughputComparison {

	public static abstract class Worker {		
		private String name;
		
		public Worker(String aName) {
			name = aName;
		}
		
		public String getName() {
			return name;
		}
		
		public abstract void performOneIteration();		
	}
	
	public interface Throughput extends Comparable<Throughput> {
		long getCompletedIterationCount();
	}
	
	private class WorkerThroughput extends Worker implements Throughput {
		private Worker worker;
		private AtomicLong count;
		
		public WorkerThroughput(Worker aWorker) {
			super(aWorker.getName());
			worker = aWorker;
			count = new AtomicLong(0);
		}
		
		public long getCompletedIterationCount() {
			return count.get();
		}

		public int compareTo(Throughput aThroughput) {
			if (getCompletedIterationCount() > aThroughput.getCompletedIterationCount()) {
				return 1;
			} else if (getCompletedIterationCount() < aThroughput.getCompletedIterationCount()) {
				return -1;
			} else {
				return 0;
			}				
		}

		public void performOneIteration() {
			worker.performOneIteration();
			count.incrementAndGet();
		}		
		
		public String toString() {
			return getName() + ": " + NumberFormat.getInstance().format(count.get());
		}
	}
	
	private FuncList<WorkerThroughput> workers = Lists.newList();
	
	public Throughput add(Worker aWorker) {		
		WorkerThroughput _wt = new WorkerThroughput(aWorker);
		workers.add(_wt);
		return _wt;
	}
	
	public void testConcurrentlyFor(int aMilliseconds) 
	throws Exception {
		final UncheckedBarrier _barrier = new UncheckedBarrier(workers.size());
		final AtomicBoolean _continue = new AtomicBoolean(true);
		
		workers.each(new Task<WorkerThroughput, Void>(){
			public void perform(final WorkerThroughput aWorker) {
				new Thread() {
					public void run() {
						_barrier.await();
						while (_continue.get()) {
							aWorker.performOneIteration();
						}
					}										
				}.start();
			}				
		});
		
		Thread.sleep(aMilliseconds);
		
		_continue.set(false);
	}
}
