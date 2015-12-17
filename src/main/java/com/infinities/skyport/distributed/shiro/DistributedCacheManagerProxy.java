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
package com.infinities.skyport.distributed.shiro;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.skyport.distributed.DistributedObjectFactory;
import com.infinities.skyport.distributed.util.DistributedUtil;

public class DistributedCacheManagerProxy implements DistributedCacheManager {

	public static final Logger logger = LoggerFactory.getLogger(DistributedCacheManagerProxy.class);

	private final DistributedCacheManager delegate;


	public DistributedCacheManagerProxy() throws Exception {
		DistributedObjectFactory objectFactory = DistributedUtil.getDefaultDistributedObjectFactory();
		this.delegate = objectFactory.getDistributedCacheManager();
	}

	@Override
	public void destroy() throws Exception {
		delegate.destroy();
	}

	@Override
	public void init() throws ShiroException {
		delegate.init();
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return delegate.getCache(name);
	}

	@Override
	public void setCacheManagerConfigFile(String classpathLocation) {
		delegate.setCacheManagerConfigFile(classpathLocation);
	}

	@Override
	public boolean getSchedulerEnabled() {
		return delegate.getSchedulerEnabled();
	}

}
