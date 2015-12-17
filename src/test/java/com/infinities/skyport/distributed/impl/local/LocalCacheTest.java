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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalCacheTest {

	private LocalCache<String, String> cache;


	@Before
	public void setUp() throws Exception {
		cache = new LocalCache<String, String>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = IllegalStateException.class)
	public void testLocalCache() {
		// not initialized yet
		cache.get("test");
	}

	@Test(expected = NullPointerException.class)
	public void testLocalCacheMapOfKV() {
		Map<String, String> map = null;
		cache = new LocalCache<String, String>(map);
	}

	@Test
	public void testLocalCacheMapOfKV2() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache = new LocalCache<String, String>(map);
		assertTrue(cache.entrySet().equals(map.entrySet()));
	}

	@Test(expected = IllegalStateException.class)
	public void testLocalCacheThrowable() {
		cache = new LocalCache<String, String>(new IllegalArgumentException("test"));
		cache.get("test");
	}

	@Test
	public void testReloadMapOfKV() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertTrue(cache.entrySet().equals(map.entrySet()));
	}

	@Test(expected = IllegalStateException.class)
	public void testReloadThrowable() {
		Throwable e = new IllegalArgumentException("test");
		cache.reload(e);
		cache.get("test");
	}

	@Test
	public void testClear() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		cache.clear();
		assertEquals(0, cache.size());
	}

	@Test
	public void testClear2() {
		cache.clear();
	}

	@Test
	public void testContainsKey() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertTrue(cache.containsKey("test"));
		assertFalse(cache.containsKey("test3"));
	}

	@Test(expected = IllegalStateException.class)
	public void testContainsKey2() {
		assertTrue(cache.containsKey("test"));
	}

	@Test
	public void testContainsValue() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertTrue(cache.containsValue("test"));
		assertFalse(cache.containsValue("test3"));
	}

	@Test(expected = IllegalStateException.class)
	public void testContainsValue2() {
		assertTrue(cache.containsValue("test"));
	}

	@Test
	public void testEntrySet() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertEquals(map.entrySet(), cache.entrySet());
		map.put("test3", "test3");
		assertNotEquals(map.entrySet(), cache.entrySet());
	}

	@Test(expected = IllegalStateException.class)
	public void testEntrySet2() {
		cache.entrySet();
	}

	@Test
	public void testGet() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertEquals("test", cache.get("test"));
	}

	@Test(expected = IllegalStateException.class)
	public void testGet2() {
		cache.get("test");
	}

	@Test
	public void testIsEmpty() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertFalse(cache.isEmpty());
		cache.clear();
		assertTrue(cache.isEmpty());
	}

	@Test(expected = IllegalStateException.class)
	public void testIsEmpty2() {
		cache.isEmpty();
	}

	@Test
	public void testKeySet() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertEquals(map.keySet(), cache.keySet());
		map.put("test3", "test3");
		assertNotEquals(map.keySet(), cache.keySet());
	}

	@Test(expected = IllegalStateException.class)
	public void testKeySet2() {
		cache.keySet();
	}

	@Test
	public void testPut() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		cache.put("test3", "test3");
		assertTrue(cache.containsKey("test3"));
	}

	@Test(expected = IllegalStateException.class)
	public void testPut2() {
		cache.put("test3", "test3");
	}

	@Test
	public void testPut3() {
		cache.refresh();
		cache.put("test3", "test3");
	}

	@Test(expected = IllegalStateException.class)
	public void testPutAll() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.putAll(map);
		assertEquals(map.entrySet(), cache.entrySet());
	}

	@Test(expected = NullPointerException.class)
	public void testPutAll2() {
		Map<String, String> map = null;
		cache.putAll(map);
	}

	@Test
	public void testPutAll3() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.putAll(map);
		cache.refresh();
		assertEquals(map.entrySet(), cache.entrySet());
	}

	@Test
	public void testRemove() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertEquals("test", cache.remove("test"));
	}

	@Test(expected = IllegalStateException.class)
	public void testRemove2() {
		cache.remove("test");
	}

	@Test
	public void testSize() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertEquals(2, cache.size());
	}

	@Test(expected = IllegalStateException.class)
	public void testSize2() {
		cache.size();
	}

	@Test
	public void testValues() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		assertTrue(map.values().containsAll(cache.values()));
		assertTrue(cache.values().containsAll(map.values()));
		map.put("test3", "test3");
		assertNotEquals(map.values(), cache.values());
	}

	@Test(expected = IllegalStateException.class)
	public void testValues2() {
		cache.values();
	}

	@Test(expected = IllegalStateException.class)
	public void testDestroy() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		cache.destroy();
		cache.values();
	}

	@Test
	public void testSet() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		cache.set("test3", "test3");
		assertTrue(cache.containsKey("test3"));
	}

	@Test
	public void testSet2() {
		cache.set("test3", "test3");
	}

	@Test
	public void testDelete() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "test");
		map.put("test2", "test2");
		cache.reload(map);
		cache.delete("test");
		assertFalse(cache.containsKey("test3"));
	}

	@Test
	public void testDelete2() {
		cache.delete("test");
	}

	@Test
	public void testRefresh() {
		cache.refresh();
		cache.get("test");
	}

}
