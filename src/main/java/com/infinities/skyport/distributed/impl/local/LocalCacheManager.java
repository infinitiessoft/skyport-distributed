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

import org.apache.shiro.cache.ehcache.EhCacheManager;

import com.infinities.skyport.distributed.shiro.DistributedCacheManager;

public class LocalCacheManager extends EhCacheManager implements DistributedCacheManager {

	@Override
	public boolean getSchedulerEnabled() {
		return true;
	}
}
