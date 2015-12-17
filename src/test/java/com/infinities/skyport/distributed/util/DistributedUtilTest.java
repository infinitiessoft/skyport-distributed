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
