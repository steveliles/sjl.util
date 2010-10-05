package com.sjl.util.managed;

public class ManagedReadWriteResource<R, W>
{       
    private ResourceManagementStrategy<R, W> managementStrategy;
        
    public ManagedReadWriteResource(ResourceManagementStrategy<R, W> aManagementStrategy)
    {
        managementStrategy = aManagementStrategy;
    }
    
    public <T, E extends Exception> T execute(ReadOperation<R, T, E> aReadOperation) throws E
    {
        R _readResource = managementStrategy.obtainReadResource();
        try
        {
            return aReadOperation.read(_readResource);
        }
        finally
        {
            managementStrategy.releaseReadResource(_readResource);
        }
    }

    public <E extends Exception> void execute(ReadOperationWithoutResult<R, E> aReadOperation) throws E
    {
        R _readResource = managementStrategy.obtainReadResource();
        try
        {
            aReadOperation.read(_readResource);
        }
        finally
        {
            managementStrategy.releaseReadResource(_readResource);
        }
    }
    
    public <T, E extends Exception> T execute(WriteOperation<W, T, E> aWriteOperation) throws E
    {
        W _writeResource = managementStrategy.obtainWriteResource();
        try
        {
            return aWriteOperation.write(_writeResource);
        }
        finally
        {
            managementStrategy.releaseWriteResource(_writeResource);
        }
    }
    
    public <E extends Exception> void execute(WriteOperationWithoutResult<W, E> aWriteOperation) throws E
    {
        W _writeResource = managementStrategy.obtainWriteResource();
        try
        {
            aWriteOperation.write(_writeResource);
        }
        finally
        {
            managementStrategy.releaseWriteResource(_writeResource);
        }
    }
}
