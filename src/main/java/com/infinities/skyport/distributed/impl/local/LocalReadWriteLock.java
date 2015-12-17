package com.infinities.skyport.distributed.impl.local;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.infinities.skyport.distributed.DistributedReadWriteLock;

public class LocalReadWriteLock implements DistributedReadWriteLock {

	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


	@Override
	public Lock readLock() {
		return readWriteLock.readLock();
	}

	@Override
	public Lock writeLock() {
		return readWriteLock.writeLock();
	}

	@Override
	public void destroy() {
	}

}
