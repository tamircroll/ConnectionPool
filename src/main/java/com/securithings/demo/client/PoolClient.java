package com.securithings.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securithings.demo.interfaces.ObjectPool;
import com.securithings.demo.models.DemoConnection;

public class PoolClient {
	private static final Logger logger = LoggerFactory.getLogger(PoolClient.class);
	
	private ObjectPool<DemoConnection> connectionPool;
	
	public void createAndSendMessage() {
		String message = "Some interesting event occured!!!";
		DemoConnection connection = null;
		try {
			if (connectionPool == null) {
				logger.warn("Connection pool is not initialized");
				return;
			}
			connection = connectionPool.get();
			if (connection == null) {
				throw new Exception("Connection returned null from the pool");
			}
			logger.info("Sending message {} using connection {}", message, connection.getId());
			Thread.sleep(100);
			logger.info("Successfully sent message {} using connection {}", message, connection.getId());
		} catch (Exception e) {
			logger.error("Failed to send message");
		} finally {
			if (connection != null)
				connectionPool.returnObject(connection);
		}
    }

	public void setConnectionPool(ObjectPool<DemoConnection> connectionPool) {
		this.connectionPool = connectionPool;
	}
}
