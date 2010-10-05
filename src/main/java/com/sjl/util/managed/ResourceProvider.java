package com.sjl.util.managed;

/**
 * Provides access to resources to be used via ManagedReadWriteResource.
 * 
 * It is expected that all access to the resources provided by this will be
 * via a ManagedReadWriteResource, and will ensure to correctly synchronize and order
 * calls.
 * 
 * @author steve
 *
 * @param <T>
 */
public interface ResourceProvider<T>
{
    
    public T takeResource();
    
    public void releaseResource(T aResource);
    
    public void prepare();
    
    public void standDown();
    
}
