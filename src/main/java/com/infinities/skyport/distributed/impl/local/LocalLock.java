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
