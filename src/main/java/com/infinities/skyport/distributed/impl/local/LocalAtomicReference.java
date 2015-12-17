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

import java.util.concurrent.atomic.AtomicReference;

import com.infinities.skyport.distributed.DistributedAtomicReference;
import com.infinities.skyport.distributed.DistributedFunction;

public class LocalAtomicReference<E> implements DistributedAtomicReference<E> {

	private final AtomicReference<E> delegate;


	public LocalAtomicReference() {
		delegate = new AtomicReference<E>();
	}

	@Override
	public void destroy() {

	}

	@Override
	public boolean compareAndSet(E expect, E update) {
		return delegate.compareAndSet(expect, update);
	}

	@Override
	public E get() {
		return delegate.get();
	}

	@Override
	public void set(E newValue) {
		delegate.set(newValue);
	}

	@Override
	public E getAndSet(E newValue) {
		return delegate.getAndSet(newValue);
	}

	@Override
	public synchronized E setAndGet(E update) {
		delegate.set(update);
		return delegate.get();
	}

	@Override
	public boolean isNull() {
		return delegate.get() == null;
	}

	@Override
	public void clear() {
		delegate.set(null);
	}

	@Override
	public boolean contains(E value) {
		return delegate.get().equals(value);
	}

	@Override
	public synchronized void alter(DistributedFunction<E, E> function) {
		set(function.apply(get()));
	}

}
