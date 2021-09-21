package com.securithings.demo.client;

import com.securithings.demo.interfaces.ObjectPool;
import com.securithings.demo.models.DemoConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoolClient
{
    private static final Logger logger = LoggerFactory.getLogger(PoolClient.class);
    private ObjectPool<DemoConnection> connectionPool;
    
    public void createAndSendMessage()
    {
        String message = "Some interesting event occurred!!!";
        DemoConnection connection = null;
        try
        {
            if(connectionPool == null)
            {
                logger.warn("Connection pool is not initialized");
                return;
            }
            connection = connectionPool.get();
            if(connection == null)
            {
                throw new Exception("Connection returned null from the pool");
            }
            connection.sendMessage(message);
        }
        catch (Exception e)
        {
            logger.error("Failed to send message: {0}", e);
        }
        finally
        {
            if(connection != null)
                connectionPool.returnObject(connection);
        }
    }
    
    public void setConnectionPool(ObjectPool<DemoConnection> connectionPool)
    {
        this.connectionPool = connectionPool;
    }
}
