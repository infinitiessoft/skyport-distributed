package com.infinities.skyport.distributed.impl.local;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import com.infinities.skyport.distributed.DistributedCondition;

public class LocalCondition implements DistributedCondition {

	private final Condition delegate;


	public LocalCondition(Condition delegate) {
		this.delegate = delegate;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void await() throws InterruptedException {
		delegate.await();
	}

	@Override
	public void awaitUninterruptibly() {
		delegate.awaitUninterruptibly();
	}

	@Override
	public long awaitNanos(long nanosTimeout) throws InterruptedException {
		return delegate.awaitNanos(nanosTimeout);
	}

	@Override
	public boolean await(long time, TimeUnit unit) throws InterruptedException {
		return delegate.await(time, unit);
	}

	@Override
	public boolean awaitUntil(Date deadline) throws InterruptedException {
		return delegate.awaitUntil(deadline);
	}

	@Override
	public void signal() {
		delegate.signal();
	}

	@Override
	public void signalAll() {
		delegate.signalAll();
	}

}
