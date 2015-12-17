package com.infinities.skyport.distributed.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.distributed.DistributedObjectFactory;
import com.infinities.skyport.distributed.DistributedObjectFactory.Delegate;
import com.infinities.skyport.model.configuration.Configuration;

public class DistributedUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateDistributedNameAccessConfig3() {
		Configuration configuration = new Configuration();
		configuration.setId("id");
		String ret = DistributedUtil.generateDistributedName(configuration);
		assertEquals("id", ret);
	}

	@Test
	public void testGetDistributedObjectFactory() throws Exception {
		DistributedObjectFactory factory = DistributedUtil.getDistributedObjectFactory(Delegate.disabled, "test");
		DistributedObjectFactory factory2 = DistributedUtil.getDistributedObjectFactory(Delegate.disabled, "test");
		assertEquals(factory, factory2);
	}

	// @Category(IntegrationTest.class)
	@Test
	public void testGetDistributedObjectFactory2() throws Exception {
		DistributedObjectFactory factory = DistributedUtil.getDistributedObjectFactory(Delegate.hazelcast,
				"testDistributedUtil");
		DistributedObjectFactory factory2 = DistributedUtil.getDistributedObjectFactory(Delegate.hazelcast,
				"testDistributedUtil");
		assertEquals(factory, factory2);
		factory.close();
		factory2.close();
	}

	@Test
	public void testGetDelegate() {
		Delegate delegate = DistributedUtil.getDelegate();
		assertEquals(Delegate.disabled, delegate);
	}

}
