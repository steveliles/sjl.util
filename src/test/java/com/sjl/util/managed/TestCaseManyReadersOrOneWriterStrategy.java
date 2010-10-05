package com.sjl.util.managed;

import static org.junit.Assert.*;

import java.util.concurrent.locks.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(JMock.class)
public class TestCaseManyReadersOrOneWriterStrategy
{
	private Mockery ctx;
	
    private ResourceProvider<String> readProvider;
    private ResourceProvider<String> writeProvider;
    private ReadWriteLock lock;
    private Lock readLock;
    private Lock writeLock;
    
    @Before
    public void setUp() throws Exception
    {
    	ctx = new JUnit4Mockery();
    	
        readProvider = newResourceProvider("read");
        writeProvider = newResourceProvider("write");
        
        lock = ctx.mock(ReadWriteLock.class);
        readLock = ctx.mock(Lock.class, "readlock");
        writeLock = ctx.mock(Lock.class, "writelock");
    }

    @SuppressWarnings("unchecked")
    private <T> ResourceProvider<T> newResourceProvider(String aName)
    {        
        return ctx.mock(ResourceProvider.class, aName);
    }
    
    @Test
    public void testReadResourceProviderIsPreparedBeforeResourceIsObtained()
    throws Exception
    {        
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);
        
        ctx.checking(new Expectations(){{
            allowing(writeProvider).standDown();
            oneOf(readProvider).prepare();
            oneOf(readProvider).takeResource();
            will(returnValue("success"));
        }});
        
        assertEquals("success", _strategy.obtainReadResource());
    }
    
    @Test
    public void testWriteResourceProviderIsPreparedBeforeResourceIsObtained()
    throws Exception
    {        
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(1);
        
        ctx.checking(new Expectations(){{
            allowing(readProvider).standDown();
            oneOf(writeProvider).prepare();
            oneOf(writeProvider).takeResource();
            will(returnValue("success"));
        }});
        
        assertEquals("success", _strategy.obtainWriteResource());
    }
    
    @Test
    public void testReadResourceProviderIsStoodDownPriorToWriteResourceAccess()
    throws Exception
    {        
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
 
        expectWriteLockIsTaken(1);
        
        ctx.checking(new Expectations(){{
            oneOf(readProvider).standDown();
            oneOf(writeProvider).prepare();
            oneOf(writeProvider).takeResource();
            will(returnValue("success"));
        }});
        
        assertEquals("success", _strategy.obtainWriteResource());
    }
    
    @Test
    public void testWriteResourceProviderIsStoodDownPriorToReadResourceAccess()
    throws Exception
    {        
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);
        
        ctx.checking(new Expectations(){{
            oneOf(writeProvider).standDown();
            oneOf(readProvider).prepare();
            oneOf(readProvider).takeResource();
            will(returnValue("success"));
        }});
        
        assertEquals("success", _strategy.obtainReadResource());
    }
    
    @Test
    public void testReaderAccessIsControlledByReadLock()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(5); // assert that each obtainReadResource gets a read-lock
        
        ctx.checking(new Expectations(){{
            atLeast(1).of(writeProvider).standDown();
            atLeast(1).of(readProvider).prepare();
            exactly(5).of(readProvider).takeResource();
            will(returnValue("success"));
        }});
        
        for (int i=0; i<5; i++)
            assertEquals("success", _strategy.obtainReadResource());                
    }
    
    @Test
    public void testWriterAccessIsControllerByWriteLock()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(5); // assert that each obtainWriteResource gets a write-lock
        
        ctx.checking(new Expectations(){{
            atLeast(1).of(readProvider).standDown();
            atLeast(1).of(writeProvider).prepare();
            
            exactly(5).of(writeProvider).takeResource();
            will(returnValue("success"));                        
        }});
        
        for (int i=0; i<5; i++)
            assertEquals("success", _strategy.obtainWriteResource());
    }

    @Test
    public void testReadLockReleasedIfProblemsDuringWriteProviderStandDown()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(writeProvider).standDown();
            will(throwException(new RuntimeException()));
            
            oneOf(readLock).unlock();
        }});        
        expectExceptionOnObtainReadResource(_strategy);               
    }
    
    @Test
    public void testReadLockReleasedIfProblemsDuringReadProviderPrepare()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(writeProvider).standDown();
            oneOf(readProvider).prepare();
            will(throwException(new RuntimeException()));
            
            oneOf(readLock).unlock();
        }});        
        expectExceptionOnObtainReadResource(_strategy);               
    }
    
    @Test
    public void testReadLockReleasedIfProblemsDuringTakeResource()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(writeProvider).standDown();
            oneOf(readProvider).prepare();
            oneOf(readProvider).takeResource();
            will(throwException(new RuntimeException()));
            
            oneOf(readLock).unlock();
        }});        
        expectExceptionOnObtainReadResource(_strategy);               
    }

    @Test
    public void testWriteLockReleasedIfProblemsDuringReadProviderStandDown()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(readProvider).standDown();
            will(throwException(new RuntimeException()));
            
            oneOf(writeLock).unlock();
        }});        
        expectExceptionOnObtainWriteResource(_strategy);               
    }
    
    @Test
    public void testWriteLockReleasedIfProblemsDuringWriteProviderPrepare()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(readProvider).standDown();
            oneOf(writeProvider).prepare();
            will(throwException(new RuntimeException()));
            
            oneOf(writeLock).unlock();
        }});        
        expectExceptionOnObtainWriteResource(_strategy);               
    }
    
    @Test
    public void testWriteLockReleasedIfProblemsDuringTakeResource()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(readProvider).standDown();
            oneOf(writeProvider).prepare();
            oneOf(writeProvider).takeResource();
            will(throwException(new RuntimeException()));
            
            oneOf(writeLock).unlock();
        }});        
        expectExceptionOnObtainWriteResource(_strategy);               
    }

    @Test
    public void testReleasesResourceBeforeReleasingReadLock()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(writeProvider).standDown();
            oneOf(readProvider).prepare();
            oneOf(readProvider).takeResource();
            will(returnValue("success"));
            
            oneOf(readProvider).releaseResource(with(equal("success")));
            oneOf(readLock).unlock();
        }});        
        
        _strategy.releaseReadResource(_strategy.obtainReadResource());
    }
    
    @Test
    public void testReadLockReleasedIfExceptionOnReleasingReadResource()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectReadLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(writeProvider).standDown();
            oneOf(readProvider).prepare();
            oneOf(readProvider).takeResource();
            will(returnValue("success"));
            
            oneOf(readProvider).releaseResource(with(equal("success")));
            will(throwException(new RuntimeException()));
            
            oneOf(readLock).unlock();
        }});        
        
        try
        {
            _strategy.releaseReadResource(_strategy.obtainReadResource());
            fail();
        }
        catch (RuntimeException anExc)
        {
            // expected
        }
    }
    
    @Test
    public void testReleasesResourceBeforeReleasingWriteLock()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(readProvider).standDown();
            oneOf(writeProvider).prepare();
            oneOf(writeProvider).takeResource();
            will(returnValue("success"));
            
            oneOf(writeProvider).releaseResource(with(equal("success")));
            oneOf(writeLock).unlock();
        }});        
        
        _strategy.releaseWriteResource(_strategy.obtainWriteResource());
    }
    
    @Test
    public void testWriteLockReleasedIfExceptionOnReleasingWriteResource()
    throws Exception
    {
        ManyReadersOrOneWriterStrategy<String, String> _strategy = 
            newManyReadersOneWriterStrategy(readProvider, writeProvider);
     
        expectWriteLockIsTaken(1);        
        ctx.checking(new Expectations(){{
            oneOf(readProvider).standDown();
            oneOf(writeProvider).prepare();
            oneOf(writeProvider).takeResource();
            will(returnValue("success"));
            
            oneOf(writeProvider).releaseResource(with(equal("success")));
            will(throwException(new RuntimeException()));
            
            oneOf(writeLock).unlock();
        }});        
        
        try
        {
            _strategy.releaseWriteResource(_strategy.obtainWriteResource());
            fail();
        }
        catch (RuntimeException anExc)
        {
            // expected
        }
    }
    
    private void expectExceptionOnObtainReadResource(ManyReadersOrOneWriterStrategy<String, String> aStrategy)
    {
        try
        {
            aStrategy.obtainReadResource();
            fail();
        }
        catch (Exception anExc)
        {
            // expected
        }
    }
    
    private void expectExceptionOnObtainWriteResource(ManyReadersOrOneWriterStrategy<String, String> aStrategy)
    {
        try
        {
            aStrategy.obtainWriteResource();
            fail();
        }
        catch (Exception anExc)
        {
            // expected
        }
    }
    
    private void expectReadLockIsTaken(final int aTimes)
    {
        ctx.checking(new Expectations(){{
            atLeast(1).of(lock).readLock();
            will(returnValue(readLock));            
            
            atLeast(aTimes).of(readLock).lock();
        }});
    }
    
    private void expectWriteLockIsTaken(final int aTimes)
    {
        ctx.checking(new Expectations(){{
            atLeast(1).of(lock).writeLock();
            will(returnValue(writeLock));            
            
            atLeast(aTimes).of(writeLock).lock();
        }});
    }
    
    private ManyReadersOrOneWriterStrategy<String, String> newManyReadersOneWriterStrategy(ResourceProvider<String> aReadProvider, ResourceProvider<String> aWriteProvider)
    {
        return new ManyReadersOrOneWriterStrategy<String, String>(aReadProvider, aWriteProvider){                        
            @Override
            protected ReadWriteLock newLock()
            {                
                return lock;
            }            
        };
    }    
}
