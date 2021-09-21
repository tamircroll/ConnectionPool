package com.securithings.demo.models;

import com.securithings.demo.interfaces.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConnectionPoolImpl implements ObjectPool<DemoConnection>
{
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolImpl.class);
    private final LinkedBlockingDeque<DemoConnection> pool = new LinkedBlockingDeque();
    
    public ConnectionPoolImpl(int poolSize)
    {
        for(int i = 0; i < poolSize; i++)
        {
            DemoConnection connection = new DemoConnection(generateId());
            pool.add(connection);
        }
    }
    
    @Override
    public DemoConnection get() throws InterruptedException, TimeoutException
    {
        DemoConnection connection = pool.poll(1, TimeUnit.MINUTES);
        if(connection == null)
        {
            throw new TimeoutException("Thread failed to receive connection due to Timeout");
        }
        return connection.isBrokenConnection() ? new DemoConnection(generateId()) : connection;
    }
    
    @Override
    public void returnObject(DemoConnection connection)
    {
        if(connection.isBrokenConnection())
        {
            DemoConnection newConnection = new DemoConnection(generateId());
            pool.add(newConnection);
        }
        else
        {
            pool.add(connection);
        }
    }
    
    private String generateId()
    {
        Random random = new Random();
        return Integer.toString((random.nextInt()));
    }
}
