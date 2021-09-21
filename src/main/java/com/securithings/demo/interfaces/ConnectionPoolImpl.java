package com.securithings.demo.interfaces;

import com.securithings.demo.client.PoolClient;
import com.securithings.demo.models.DemoConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConnectionPoolImpl implements ObjectPool<DemoConnection>
{
    private static final Logger logger = LoggerFactory.getLogger(PoolClient.class);
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
        DemoConnection connection = pool.poll(60, TimeUnit.SECONDS);
        if(connection == null)
        {
            throw new TimeoutException("Thread failed to receive connection due to Timeout");
        }
        DemoConnection newConnection = new DemoConnection(generateId());
        return connection.isBrokenConnection() ? newConnection : connection;
    }
    
    @Override
    public void returnObject(DemoConnection connection)
    {
        try
        {
            if(connection.isBrokenConnection())
            {
                DemoConnection newConnection = new DemoConnection(generateId());
                pool.add(newConnection);
            }
            else
            {
                pool.put(connection);
            }
        }
        catch (InterruptedException e)
        {
            logger.error("Failed to return connection {}", connection.getId());
        }
    }
    
    private String generateId()
    {
        Random random = new Random();
        return Integer.toString((random.nextInt()));
    }
}
