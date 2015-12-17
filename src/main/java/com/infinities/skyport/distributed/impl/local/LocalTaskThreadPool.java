/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.skyport.distributed.impl.local;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.infinities.skyport.ServiceProvider;
import com.infinities.skyport.distributed.DistributedExecutor;
import com.infinities.skyport.distributed.DistributedThreadPool;
import com.infinities.skyport.distributed.impl.hazelcast.hazeltask.core.concurrent.NamedThreadFactory;
import com.infinities.skyport.exception.SkyportException;
import com.infinities.skyport.model.PoolConfig;
import com.infinities.skyport.model.PoolSize;
import com.infinities.skyport.util.NamedThreadPoolExecutor;

public class LocalTaskThreadPool implements DistributedThreadPool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalObjectFactory localObjectFactory;
	private String name;
	private DistributedExecutor longThreadPool;
	private DistributedExecutor mediumThreadPool;
	private DistributedExecutor shortThreadPool;


	public LocalTaskThreadPool(LocalObjectFactory localObjectFactory, String group, PoolConfig longPoolConfig,
			PoolConfig mediumPoolConfig, PoolConfig shortPoolConfig, ServiceProvider serviceProvider) {
		this.localObjectFactory = localObjectFactory;
		this.name = group;
		longThreadPool =
				new LocalExecutor(group + "_" + PoolSize.LONG.name(), new NamedThreadPoolExecutor(
						longPoolConfig.getCoreSize(), // core
						// size
						longPoolConfig.getMaxSize(), // max size
						longPoolConfig.getKeepAlive(), // idle timeout
						TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(longPoolConfig.getQueueCapacity()),
						new NamedThreadFactory(group, "Long-term")), serviceProvider);

		mediumThreadPool =
				new LocalExecutor(group + "_" + PoolSize.MEDIUM.name(), new NamedThreadPoolExecutor(
						mediumPoolConfig.getCoreSize(), // core
						// size
						mediumPoolConfig.getMaxSize(), // max size
						mediumPoolConfig.getKeepAlive(), // idle timeout
						TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(mediumPoolConfig.getQueueCapacity()),
						new NamedThreadFactory(group, "Mid-term")), serviceProvider);

		shortThreadPool =
				new LocalExecutor(group + "_" + PoolSize.SHORT.name(), new NamedThreadPoolExecutor(
						shortPoolConfig.getCoreSize(), // core
						// size
						shortPoolConfig.getMaxSize(), // max size
						shortPoolConfig.getKeepAlive(), // idle timeout
						TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(shortPoolConfig.getQueueCapacity()),
						new NamedThreadFactory(group, "Short-term")), serviceProvider);
	}

	@Override
	public DistributedExecutor getThreadPool(PoolSize poolSize) {
		switch (poolSize) {
		case SHORT:
			return shortThreadPool;
		case MEDIUM:
			return mediumThreadPool;
		case LONG:
			return longThreadPool;
		default:
			throw new SkyportException("No thread pool with size: " + poolSize);
		}
	}

	@Override
	public void shutdown() {
		if (!longThreadPool.isShutdown()) {
			longThreadPool.shutdown();
		}
		if (!mediumThreadPool.isShutdown()) {
			mediumThreadPool.shutdown();
		}
		if (!shortThreadPool.isShutdown()) {
			shortThreadPool.shutdown();
		}
		localObjectFactory.executorService.remove(name);
	}

	@Override
	public void destroy() {
		shutdown();
	}

}
