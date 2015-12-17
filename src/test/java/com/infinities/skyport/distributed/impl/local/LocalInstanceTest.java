package com.infinities.skyport.distributed.impl.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.distributed.DistributedObjectFactory;

public class LocalInstanceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstanceByName() {
		DistributedObjectFactory factory = LocalInstance.getInstanceByName("test");
		DistributedObjectFactory factory2 = LocalInstance.getInstanceByName("test");
		assertEquals(factory, factory2);
		assertEquals(1, LocalInstance.instances.size());
		DistributedObjectFactory factory3 = LocalInstance.getInstanceByName("test2");
		assertNotEquals(factory, factory3);
		assertEquals(2, LocalInstance.instances.size());
	}

}
