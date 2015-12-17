package com.infinities.skyport.distributed.impl.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.distributed.DistributedCondition;

public class LocalLockTest {

	private LocalLock lock;


	@Before
	public void setUp() throws Exception {
		lock = new LocalLock();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewCondition() {
		DistributedCondition condition = lock.newCondition("test");
		DistributedCondition condition2 = lock.newCondition("test");
		assertEquals(condition, condition2);
		assertEquals(1, lock.conditions.size());
		DistributedCondition condition3 = lock.newCondition("test2");
		assertNotEquals(condition, condition3);
		assertEquals(2, lock.conditions.size());
	}

}
