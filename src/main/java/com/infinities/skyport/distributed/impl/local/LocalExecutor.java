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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.infinities.skyport.ServiceProvider;
import com.infinities.skyport.async.AsyncTask;
import com.infinities.skyport.distributed.DistributedExecutor;

public class LocalExecutor implements DistributedExecutor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ThreadPoolExecutor threadPool;
	private final ListeningExecutorService executorService;
	private final ServiceProvider serviceProvider;
	private final String name;


	// private final Logger logger =
	// LoggerFactory.getLogger(LocalExecutor.class);

	public LocalExecutor(String name, ThreadPoolExecutor threadPool, ServiceProvider serviceProvider) {
		this.name = name;
		this.threadPool = threadPool;
		this.executorService = MoreExecutors.listeningDecorator(threadPool);
		this.serviceProvider = serviceProvider;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return executorService.awaitTermination(timeout, unit);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return executorService.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		return executorService.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> arg0) throws InterruptedException, ExecutionException {
		return executorService.invokeAny(arg0);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return executorService.invokeAny(tasks, timeout, unit);
	}

	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}

	@Override
	public void execute(Runnable command) {
		executorService.execute(command);
	}

	@Override
	public void destroy() {
		shutdown();
	}

	@Override
	public <T> ListenableFuture<T> submit(Callable<T> task) {
		return executorService.submit(task);
	}

	@Override
	public <T> ListenableFuture<T> submit(Runnable task, T result) {
		return executorService.submit(task, result);
	}

	@Override
	public ListenableFuture<?> submit(Runnable task) {
		return executorService.submit(task);
	}

	@Override
	public <T> ListenableFuture<T> submit(AsyncTask<T> task) {
		task.setServiceProvider(serviceProvider);
		Callable<T> callable = task;// new TaskWrapper<I, T>(task);
		return submit(callable);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setCorePoolSize(int corePoolSize) {
		threadPool.setCorePoolSize(corePoolSize);
	}

	@Override
	public void setKeepAliveTime(int time, TimeUnit unit) {
		threadPool.setKeepAliveTime(time, unit);
	}

	@Override
	public void setMaximumPoolSize(int maximumPoolSize) {
		threadPool.setMaximumPoolSize(maximumPoolSize);
	}

	// for testing
	public ThreadPoolExecutor getThreadPool() {
		return this.threadPool;
	}

	// private class TaskWrapper<I extends Serializable, T extends Serializable>
	// implements Callable<T> {
	//
	// private AsyncTask<I, T> task;
	//
	//
	// private TaskWrapper(AsyncTask<I, T> task) {
	// this.task = task;
	// }
	//
	// @Override
	// public T call() throws Exception {
	// return task.call();
	// }
	// }

}
