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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.infinities.skyport.distributed.DistributedCache;

public class LocalCache<K, V> implements DistributedCache<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static final Logger logger =
	// LoggerFactory.getLogger(LocalCache.class);
	private volatile Map<K, V> map;
	private volatile Throwable e;


	// private final Striped<Lock> lazeWeakLock = Striped.lazyWeakLock(1);
	// private final ConcurrentHashMap<K, Lock> lockMap = new
	// ConcurrentHashMap<K, Lock>();

	public LocalCache() {
		this.e = new IllegalStateException("cache not initialized yet");
		this.map = Maps.newConcurrentMap();
	}

	public LocalCache(Map<K, V> map) {
		reload(map);
	}

	public LocalCache(Throwable e) {
		if (e == null) {
			throw new NullPointerException("e cannot be null");
		}
		this.e = e;
		this.map = Maps.newConcurrentMap();
	}

	@Override
	public void reload(Map<K, V> m) {
		if (m == null) {
			throw new NullPointerException("map cannot be null");
		}
		this.map = new ConcurrentHashMap<K, V>(m);
		e = null;
	}

	@Override
	public void reload(Throwable e) {
		this.e = e;
		// map.clear();
	}

	@Override
	public void clear() {
		this.map = Maps.newConcurrentMap();
	}

	@Override
	public boolean containsKey(Object key) {
		checkMessage();
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		checkMessage();
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		checkMessage();
		return Sets.newHashSet(map.entrySet());
	}

	@Override
	public V get(Object key) {
		checkMessage();
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		checkMessage();
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		checkMessage();
		return Sets.newHashSet(map.keySet());
	}

	@Override
	public V put(K key, V value) {
		checkMessage();
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null) {
			throw new NullPointerException("map cannot be null");
		}
		map.putAll(m);
	}

	@Override
	public V remove(Object key) {
		checkMessage();
		return map.remove(key);
	}

	@Override
	public int size() {
		checkMessage();
		return map.size();
	}

	@Override
	public Collection<V> values() {
		checkMessage();
		return Sets.newHashSet(map.values());
	}

	@Override
	public void destroy() {
		this.e = new IllegalStateException("cache not initialized yet");
		this.map = Maps.newConcurrentMap();
	}

	// @Override
	// public synchronized boolean tryLock(K key) {
	// logger.debug("trylock: {}", key);
	// Lock lock = lockMap.get(key);
	//
	// if (lock == null) {
	// lock = new ReentrantLock();
	// lockMap.put(key, lock);
	// }
	//
	// logger.debug("trylock: {}", lock);
	//
	// try {
	// return lock.tryLock(100, TimeUnit.SECONDS);
	// } catch (InterruptedException e) {
	// logger.debug("interrupt trylock", e);
	// return false;
	// }
	// }
	//
	// @Override
	// public synchronized void unlock(K key) {
	// logger.debug("unlock: {}", key);
	// Lock lock = lockMap.remove(key);
	//
	// logger.debug("unlock: {}", lock);
	// if (lock == null) {
	// return;
	// }
	// lock.unlock();
	// }

	@Override
	public void set(K key, V value) {
		map.put(key, value);
	}

	// @Override
	// public synchronized void lock(K key) {
	// logger.debug("lock: {}", key);
	// Lock lock = lockMap.get(key);
	//
	// if (lock == null) {
	// lock = new ReentrantLock();
	// lockMap.put(key, lock);
	// }
	//
	// logger.debug("lock: {}", lock);
	//
	// lock.lock();
	// }

	@Override
	public void delete(K key) {
		map.remove(key);
	}

	@Override
	public void refresh() {
		e = null;
	}

	private void checkMessage() {
		if (e != null) {
			throw new IllegalStateException(e);
		}
	}

}
