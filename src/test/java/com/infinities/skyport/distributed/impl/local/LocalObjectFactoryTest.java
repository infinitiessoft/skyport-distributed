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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.distributed.DistributedAtomicLong;
import com.infinities.skyport.distributed.DistributedAtomicReference;
import com.infinities.skyport.distributed.DistributedCache;
import com.infinities.skyport.distributed.DistributedLock;
import com.infinities.skyport.distributed.DistributedMap;
import com.infinities.skyport.distributed.DistributedThreadPool;
import com.infinities.skyport.model.PoolConfig;
import com.infinities.skyport.model.PoolSize;

public class LocalObjectFactoryTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private LocalObjectFactory factory;


	@Before
	public void setUp() throws Exception {
		LocalInstance.instances.clear();
		factory = new LocalObjectFactory("test");
	}

	@After
	public void tearDown() throws Exception {
		factory.close();
		LocalInstance.instances.clear();
	}

	@Test
	public void testGetMap() {
		DistributedMap<String, String> map = factory.getMap("test");
		DistributedMap<String, String> map2 = factory.getMap("test");
		assertEquals(map, map2);
		assertEquals(1, factory.mapService.size());
		DistributedMap<String, String> map3 = factory.getMap("test3");
		assertNotEquals(map, map3);
		assertEquals(2, factory.mapService.size());
	}

	@Test
	public void testGetCacheStringMapOfKV() {
		Map<String, String> map = new HashMap<String, String>();
		DistributedCache<String, String> cache = factory.getCache("test", map);
		DistributedCache<String, String> cache2 = factory.getCache("test", map);
		assertEquals(cache, cache2);
		assertEquals(1, factory.cacheService.size());
		DistributedCache<String, String> cache3 = factory.getCache("test3", map);
		assertNotEquals(cache, cache3);
		assertEquals(2, factory.cacheService.size());
		cache.get("test");
	}

	@Test
	public void testGetCacheStringThrowable() {
		Throwable e = new IllegalStateException("test");
		DistributedCache<String, String> cache = factory.getCache("test", e);
		DistributedCache<String, String> cache2 = factory.getCache("test", e);
		assertEquals(cache, cache2);
		assertEquals(1, factory.cacheService.size());
		DistributedCache<String, String> cache3 = factory.getCache("test3", e);
		assertNotEquals(cache, cache3);
		assertEquals(2, factory.cacheService.size());
	}

	@Test(expected = IllegalStateException.class)
	public void testGetCacheStringThrowable2() {
		Throwable e = new IllegalStateException("test");
		DistributedCache<String, String> cache = factory.getCache("test", e);
		DistributedCache<String, String> cache2 = factory.getCache("test", e);
		assertEquals(cache, cache2);
		assertEquals(1, factory.cacheService.size());
		DistributedCache<String, String> cache3 = factory.getCache("test3", e);
		assertNotEquals(cache, cache3);
		assertEquals(2, factory.cacheService.size());
		cache.get("test");
	}

	@Test
	public void testGetAtomicLong() {
		DistributedAtomicLong atomicLong = factory.getAtomicLong("test");
		DistributedAtomicLong atomicLong2 = factory.getAtomicLong("test");
		assertEquals(atomicLong, atomicLong2);
		assertEquals(1, factory.longService.size());
		DistributedAtomicLong atomicLong3 = factory.getAtomicLong("test3");
		assertNotEquals(atomicLong, atomicLong3);
		assertEquals(2, factory.longService.size());
	}

	@Test
	public void testGetThreadPool() {
		DistributedThreadPool pool =
				factory.getThreadPool("test", new PoolConfig(PoolSize.LONG), new PoolConfig(PoolSize.MEDIUM),
						new PoolConfig(PoolSize.SHORT), null, null);
		DistributedThreadPool pool2 =
				factory.getThreadPool("test", new PoolConfig(PoolSize.LONG), new PoolConfig(PoolSize.MEDIUM),
						new PoolConfig(PoolSize.SHORT), null, null);
		assertEquals(pool, pool2);
		assertEquals(1, factory.executorService.size());
		DistributedThreadPool pool3 =
				factory.getThreadPool("test3", new PoolConfig(PoolSize.LONG), new PoolConfig(PoolSize.MEDIUM),
						new PoolConfig(PoolSize.SHORT), null, null);
		assertNotEquals(pool, pool3);
		assertEquals(2, factory.executorService.size());
	}

	@Test
	public void testShutdownThreadPool() {
		DistributedThreadPool pool =
				factory.getThreadPool("test", new PoolConfig(PoolSize.LONG), new PoolConfig(PoolSize.MEDIUM),
						new PoolConfig(PoolSize.SHORT), null, null);
		pool.shutdown();

		DistributedThreadPool pool2 =
				factory.getThreadPool("test", new PoolConfig(PoolSize.LONG), new PoolConfig(PoolSize.MEDIUM),
						new PoolConfig(PoolSize.SHORT), null, null);
		pool2.getThreadPool(PoolSize.SHORT).execute(new Runnable() {

			@Override
			public void run() {
				System.err.println("mock task");
			}
		});
	}

	// @Test
	// public void testGetReentrantReadWriteLock() {
	// DistributedReadWriteLock lock =
	// factory.getReentrantReadWriteLock("test");
	// DistributedReadWriteLock lock2 =
	// factory.getReentrantReadWriteLock("test");
	// assertEquals(lock, lock2);
	// assertEquals(1, factory.readWriteLockService.size());
	// DistributedReadWriteLock lock3 =
	// factory.getReentrantReadWriteLock("test3");
	// assertNotEquals(lock, lock3);
	// assertEquals(2, factory.readWriteLockService.size());
	// }

	// @Test
	// public void testGetReentrantReadWriteLock2() throws InterruptedException
	// {
	// LocalObjectFactory factory2 = new LocalObjectFactory("test");
	// DistributedReadWriteLock lock =
	// factory.getReentrantReadWriteLock("test");
	// DistributedReadWriteLock lock2 =
	// factory2.getReentrantReadWriteLock("test");
	// assertNotEquals(lock, lock2);
	// }

	@Test
	public void testGetAtomicReference() {
		DistributedAtomicReference<String> ref = factory.getAtomicReference("test");
		DistributedAtomicReference<String> ref2 = factory.getAtomicReference("test");
		assertEquals(ref, ref2);
		assertEquals(1, factory.referenceService.size());
		DistributedAtomicReference<String> ref3 = factory.getAtomicReference("test3");
		assertNotEquals(ref, ref3);
		assertEquals(2, factory.referenceService.size());
	}

	@Test
	public void testGetLock() {
		DistributedLock lock = factory.getLock("test");
		DistributedLock lock2 = factory.getLock("test");
		assertEquals(lock, lock2);
		assertEquals(1, factory.lockService.size());
		DistributedLock lock3 = factory.getLock("test3");
		assertNotEquals(lock, lock3);
		assertEquals(2, factory.lockService.size());
	}

	@Test
	public void testClose() {
		LocalInstance.instances.put(factory.name, factory);
		assertEquals(1, LocalInstance.instances.size());
		factory.getCache("test");
		factory.getLock("test");
		factory.getAtomicReference("test");
		// factory.getReentrantReadWriteLock("test");
		factory.getThreadPool("test", new PoolConfig(PoolSize.LONG), new PoolConfig(PoolSize.MEDIUM), new PoolConfig(
				PoolSize.SHORT), null, null);
		factory.getAtomicLong("test");
		factory.getMap("test");
		assertEquals(1, factory.cacheService.size());
		assertEquals(1, factory.referenceService.size());
		// assertEquals(1, factory.readWriteLockService.size());
		assertEquals(1, factory.mapService.size());
		assertEquals(1, factory.executorService.size());
		assertEquals(1, factory.longService.size());
		assertEquals(1, factory.lockService.size());
		factory.close();
		assertEquals(0, factory.cacheService.size());
		assertEquals(0, factory.referenceService.size());
		// assertEquals(0, factory.readWriteLockService.size());
		assertEquals(0, factory.mapService.size());
		assertEquals(0, factory.executorService.size());
		assertEquals(0, factory.longService.size());
		assertEquals(0, factory.lockService.size());
		assertEquals(0, LocalInstance.instances.size());
	}

	@Test
	public void testGetCacheString() {
		DistributedCache<String, String> cache = factory.getCache("test");
		DistributedCache<String, String> cache2 = factory.getCache("test");
		assertEquals(cache, cache2);
		assertEquals(1, factory.cacheService.size());
		DistributedCache<String, String> cache3 = factory.getCache("test3");
		assertNotEquals(cache, cache3);
		assertEquals(2, factory.cacheService.size());

	}

}
