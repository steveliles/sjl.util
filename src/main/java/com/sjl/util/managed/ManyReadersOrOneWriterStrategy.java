package com.sjl.util.managed;

import java.util.concurrent.locks.*;

/**
 * A ResourceManagementStrategy that allows many concurrent readers OR one writer.
 * Use this strategy if:
 *  - Any read must see the latest state of any data provided by write operations that began 
 *    before the read operation began.
 *  - many reads to the underlying resource can progress concurrently, but reads concurrent 
 *    with writes are not desirable or not allowed.
 * 
 * This strategy manages concurrency of read and write operations, but does not synchronize
 * access to the ResourcePool's.  Many concurrent take and release requests may be made of the
 * resourcePools, so the ResourcePool implementations must be thread safe if used in a multi-threaded
 * scenario.
 * 
 * @author steve
 *
 * @param <R>
 * @param <W>
 */
public class ManyReadersOrOneWriterStrategy<R, W> implements ResourceManagementStrategy<R, W>
{
    private ReadWriteLock lock;
    
    private ResourceProvider<R> readResourceProvider;
    private ResourceProvider<W> writeResourceProvider;

    public ManyReadersOrOneWriterStrategy(ResourceProvider<R> aReadResourceProvider, ResourceProvider<W> aWriteResourceProvider)
    {
        lock = newLock();
        readResourceProvider = aReadResourceProvider;
        writeResourceProvider = aWriteResourceProvider;
    }
    
    public R obtainReadResource()
    {
        lock.readLock().lock();
        
        try
        {
            writeResourceProvider.standDown();
            readResourceProvider.prepare();
            return readResourceProvider.takeResource();
        }
        catch (Exception anExc)
        {
            lock.readLock().unlock();
            throw new RuntimeException(anExc);
        }
    }

    public W obtainWriteResource()
    {
        lock.writeLock().lock();
        
        try
        {
            readResourceProvider.standDown();
            writeResourceProvider.prepare();
            return writeResourceProvider.takeResource();
        }
        catch (Exception anExc)
        {
            lock.writeLock().unlock();
            throw new RuntimeException(anExc);
        }                           
    }

    public void releaseReadResource(R aReadResource)
    {
        try
        {
            readResourceProvider.releaseResource(aReadResource);
        }
        finally
        {
            lock.readLock().unlock();    
        }        
    }

    public void releaseWriteResource(W aWriteResource)
    {
        try
        {
            writeResourceProvider.releaseResource(aWriteResource);
        }
        finally
        {
            lock.writeLock().unlock();
        }
    }
    
    protected ReadWriteLock newLock()
    {
        return new ReentrantReadWriteLock();
    }
    
}
