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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.skyport.distributed.DistributedCondition;
import com.infinities.skyport.distributed.DistributedLock;

public class LocalLock implements DistributedLock {

	private final ReentrantLock delegate = new ReentrantLock();
	protected final Map<String, DistributedCondition> conditions = new ConcurrentHashMap<String, DistributedCondition>();
	private static final Logger logger = LoggerFactory.getLogger(LocalLock.class);


	@Override
	public void destroy() {
		conditions.clear();
	}

	@Override
	public void lock() {
		delegate.lock();
	}

	@Override
	public boolean tryLock() {
		return delegate.tryLock();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return delegate.tryLock(time, unit);
	}

	@Override
	public void unlock() {
		delegate.unlock();
	}

	@Override
	public void lock(long leaseTime, TimeUnit timeUnit) {
		delegate.lock();
	}

	@Override
	public void forceUnlock() {
		delegate.lock();
	}

	@Override
	public DistributedCondition newCondition(String name) {
		DistributedCondition condition = conditions.get(name);
		logger.debug("get distributedLock name: {}, exist? {}", new Object[] { name, condition != null });
		if (condition != null) {
			return condition;
		}
		condition = new LocalCondition(delegate.newCondition());
		conditions.put(name, condition);
		return condition;
	}

	@Override
	public boolean isLocked() {
		return delegate.isLocked();
	}

	@Override
	public boolean isLockedByCurrentThread() {
		return delegate.isHeldByCurrentThread();
	}

	@Override
	public int getLockCount() {
		return delegate.getHoldCount();
	}

	@Override
	public long getRemainingLeaseTime() {
		return 0;
	}

}
