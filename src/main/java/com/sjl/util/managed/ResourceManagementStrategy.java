package com.sjl.util.managed;

public interface ResourceManagementStrategy<R, W>
{
    
    public R obtainReadResource();
    
    public void releaseReadResource(R aReadResource);
    
    public W obtainWriteResource();
    
    public void releaseWriteResource(W aWriteResource);        
    
}
