package com.securithings.demo.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.securithings.demo.client.PoolClient;
import com.securithings.demo.interfaces.ObjectPool;
import com.securithings.demo.models.DemoConnection;

public class DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	private static final int POOL_SIZE = 5;
	private static final int NUM_OF_WORKERS = 10;
	private static final int NUM_OF_TASKS = 1000;
	
	public static void main(String[] args) throws InterruptedException {
		int numberOfTasks = args != null && args.length > 0 ? Integer.parseInt(args[0]): NUM_OF_TASKS;
		int numOfWorkers = args != null && args.length > 1 ? Integer.parseInt(args[1]): NUM_OF_WORKERS;
		logger.info("Initializing Executor service with thread pool size of: {}", numOfWorkers);
		ExecutorService executor = Executors.newFixedThreadPool(numOfWorkers);

		int connectionPoolSize = args != null && args.length > 2 ? Integer.parseInt(args[2]): POOL_SIZE;
		logger.info("Initializing a connection pool with pool size of: {}", connectionPoolSize);
		
		// TODO: initiate your pool implementation here
		ObjectPool<DemoConnection> connectionPool = null;
		
		PoolClient poolClient = new PoolClient();
		poolClient.setConnectionPool(connectionPool);
		
		
		logger.info("=======================================================================");
		logger.info("About to execute {} concurrent report event tasks", numberOfTasks);
		for (int i = 0; i < numberOfTasks; i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					poolClient.createAndSendMessage();
				}
			});
		}
		logger.info("Finished executing {} concurrent report event tasks", numberOfTasks);
	}

}
