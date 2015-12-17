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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.skyport.ServiceProvider;
import com.infinities.skyport.distributed.DistributedAtomicLong;
import com.infinities.skyport.distributed.DistributedAtomicReference;
import com.infinities.skyport.distributed.DistributedCache;
import com.infinities.skyport.distributed.DistributedLock;
import com.infinities.skyport.distributed.DistributedMap;
import com.infinities.skyport.distributed.DistributedObjectFactory;
import com.infinities.skyport.distributed.DistributedThreadPool;
import com.infinities.skyport.distributed.shiro.DistributedCacheManager;
import com.infinities.skyport.model.Member;
import com.infinities.skyport.model.PoolConfig;

public class LocalObjectFactory implements DistributedObjectFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LocalObjectFactory.class);
	@SuppressWarnings("rawtypes")
	protected Map<String, DistributedMap> mapService = new ConcurrentHashMap<String, DistributedMap>();
	@SuppressWarnings("rawtypes")
	protected Map<String, DistributedCache> cacheService = new ConcurrentHashMap<String, DistributedCache>();
	protected Map<String, DistributedAtomicLong> longService = new ConcurrentHashMap<String, DistributedAtomicLong>();
	// protected Map<String, DistributedReadWriteLock> readWriteLockService =
	// new ConcurrentHashMap<String, DistributedReadWriteLock>();
	@SuppressWarnings("rawtypes")
	protected Map<String, DistributedAtomicReference> referenceService =
			new ConcurrentHashMap<String, DistributedAtomicReference>();
	protected Map<String, DistributedLock> lockService = new ConcurrentHashMap<String, DistributedLock>();
	protected Map<String, DistributedThreadPool> executorService = new ConcurrentHashMap<String, DistributedThreadPool>();
	protected String name;


	protected LocalObjectFactory(String name) {
		this.name = name;
	}

	@Override
	public <K, V> DistributedMap<K, V> getMap(String name) {
		@SuppressWarnings("unchecked")
		DistributedMap<K, V> map = mapService.get(name);
		if (map != null) {
			return map;
		}
		map = new LocalMap<K, V>();
		mapService.put(name, map);
		return map;
	}

	@Override
	public <K, V> DistributedCache<K, V> getCache(String name, Map<K, V> map) {
		@SuppressWarnings("unchecked")
		DistributedCache<K, V> cache = cacheService.get(name);
		if (cache != null) {
			return cache;
		}
		cache = new LocalCache<K, V>(map);
		cacheService.put(name, cache);
		return cache;
	}

	@Override
	public <K, V> DistributedCache<K, V> getCache(String name, Throwable e) {
		@SuppressWarnings("unchecked")
		DistributedCache<K, V> cache = cacheService.get(name);
		if (cache != null) {
			return cache;
		}
		cache = new LocalCache<K, V>(e);
		cacheService.put(name, cache);
		return cache;
	}

	@Override
	public DistributedAtomicLong getAtomicLong(String name) {
		DistributedAtomicLong atomicLong = longService.get(name);
		logger.debug("get distributedAtomicLong name: {}, exist? {}", new Object[] { name, atomicLong != null });
		if (atomicLong != null) {
			return atomicLong;
		}
		atomicLong = new LocalAtomicLong(name);
		longService.put(name, atomicLong);
		return atomicLong;
	}

	@Override
	public DistributedThreadPool getThreadPool(String name, PoolConfig longPoolConfig, PoolConfig mediumPoolConfig,
			PoolConfig shortPoolConfig, ScheduledExecutorService scheduler, ServiceProvider serviceProvider) {
		DistributedThreadPool pool = executorService.get(name);
		if (pool != null) {
			return pool;
		}
		pool = new LocalTaskThreadPool(this, name, longPoolConfig, mediumPoolConfig, shortPoolConfig, serviceProvider);
		executorService.put(name, pool);
		return pool;
	}

	@Override
	public <K> DistributedAtomicReference<K> getAtomicReference(String name) {
		@SuppressWarnings("unchecked")
		DistributedAtomicReference<K> atomicReference = referenceService.get(name);
		if (atomicReference != null) {
			return atomicReference;
		}
		atomicReference = new LocalAtomicReference<K>();
		referenceService.put(name, atomicReference);
		return atomicReference;
	}

	@Override
	public DistributedLock getLock(String name) {
		DistributedLock lock = lockService.get(name);
		logger.debug("get distributedLock name: {}, exist? {}", new Object[] { name, lock != null });
		if (lock != null) {
			return lock;
		}
		lock = new LocalLock();
		lockService.put(name, lock);
		return lock;
	}

	@Override
	public synchronized void close() {
		for (@SuppressWarnings("rawtypes")
		DistributedMap map : mapService.values()) {
			map.destroy();
		}
		mapService.clear();
		for (@SuppressWarnings("rawtypes")
		DistributedCache cache : cacheService.values()) {
			cache.destroy();
		}
		cacheService.clear();
		for (DistributedAtomicLong l : longService.values()) {
			l.destroy();
		}
		longService.clear();
		for (@SuppressWarnings("rawtypes")
		DistributedAtomicReference l : referenceService.values()) {
			l.destroy();
		}
		referenceService.clear();
		for (DistributedLock l : lockService.values()) {
			l.destroy();
		}
		lockService.clear();
		for (DistributedThreadPool l : executorService.values()) {
			l.destroy();
		}
		executorService.clear();
		LocalInstance.instances.remove(name);
	}

	@Override
	public <K, V> DistributedCache<K, V> getCache(String name) {
		@SuppressWarnings("unchecked")
		DistributedCache<K, V> cache = cacheService.get(name);
		if (cache != null) {
			return cache;
		}
		cache = new LocalCache<K, V>(new IllegalStateException(INITIALIZING));
		cacheService.put(name, cache);
		return cache;
	}

	@Override
	public DistributedCacheManager getDistributedCacheManager() {
		return new LocalCacheManager();
	}

	@Override
	public String getGroup() {
		return "none";
	}

	@Override
	public Set<Member> getMembers() {
		return new HashSet<Member>();
	}

}
