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
