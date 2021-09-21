package com.securithings.demo.models;

import com.securithings.demo.client.PoolClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

public class DemoConnection
{
    private static final Logger logger = LoggerFactory.getLogger(DemoConnection.class);
    private final static Random random = new Random();
    private final String id;
    boolean isBrokenConnection;
    
    public DemoConnection(String id)
    {
        logger.info("creating new DemoConnection with id: {}", id);
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
    
    public void sendMessage(String message) throws Exception
    {
        try
        {
            Thread.sleep(1000);
            // NOTE: Random Connections failures
            if(random.nextInt() % 10 == 0)
            {
                throw new Exception("Connection is broken");
            }
            logger.info("Sending message: {}. using connection {}", message, id);
        }
        catch (Exception e)
        {
            isBrokenConnection = true;
            throw e;
        }
        logger.info("Successfully sent message {} using connection {}", message, id);
    }
}
