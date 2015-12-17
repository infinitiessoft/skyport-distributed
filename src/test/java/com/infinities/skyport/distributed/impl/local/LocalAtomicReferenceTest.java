package com.infinities.skyport.distributed.impl.local;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.distributed.impl.local.LocalAtomicReference;

public class LocalAtomicReferenceTest {

	private LocalAtomicReference<String> reference = new LocalAtomicReference<String>();


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsNull() {
		assertTrue(reference.isNull());
	}
	
	@Test
	public void testIsNull2() {
		reference.set("test");
		assertFalse(reference.isNull());
	}


}
