package com.sjl.util.managed;

public interface WriteOperation<W, T, E extends Exception>
{

    /**
     * Perform a write operation on the given resource.
     *
     * When the method exits the WriteResource must not be used for any other purpose.
     * Do not store it or attempt to use it outside the scope of this method call.
     * 
     * The intended usage pattern is to perform only the minimum necessary work within
     * this method call, so that the resource can be freed up quickly.
     * 
     * @param aWriteResource
     * @return T
     */
    public T write(W aWriteResource) throws E;
    
}
