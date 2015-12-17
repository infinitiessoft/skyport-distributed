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

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import com.infinities.skyport.distributed.DistributedAtomicLong;

public class LocalAtomicLong
	implements DistributedAtomicLong, Serializable {

    private static final long serialVersionUID = 1L;
    private final AtomicLong delegate;
    private final String name;

    public LocalAtomicLong(String name) {
	this.name = name;
	delegate = new AtomicLong();
    }

    @Override
    public void destroy() {
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public long incrementAndGet() {
	return delegate.incrementAndGet();
    }

    @Override
    public long getAndIncrement() {
	return delegate.getAndIncrement();
    }

    @Override
    public long decrementAndGet() {
	return delegate.decrementAndGet();
    }

    @Override
    public long addAndGet(long delta) {
	return delegate.addAndGet(delta);
    }

    @Override
    public long getAndAdd(long delta) {
	return delegate.getAndAdd(delta);
    }

    @Override
    public boolean compareAndSet(long expect, long update) {
	return delegate.compareAndSet(expect, update);
    }

    @Override
    public long getAndSet(long newValue) {
	return delegate.getAndSet(newValue);
    }

    @Override
    public long get() {
	return delegate.get();
    }

    @Override
    public void set(long newValue) {
	delegate.set(newValue);
    }
}
