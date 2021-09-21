package com.securithings.demo.models;

import com.securithings.demo.client.PoolClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoConnection
{
    private static final Logger logger = LoggerFactory.getLogger(PoolClient.class);
    private final String id;
    boolean isBrokenConnection;
    
    public DemoConnection(String id)
    {
        this.id = id;
    }
    
    public boolean isBrokenConnection()
    {
        return isBrokenConnection;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void sendRequest(String message) throws Exception
    {
        try
        {
			logger.info("Sending message {} using connection {}", message, id);
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            isBrokenConnection = true;
            throw e;
        }
        logger.info("Successfully sent message {} using connection {}", message, id);
    }
}
