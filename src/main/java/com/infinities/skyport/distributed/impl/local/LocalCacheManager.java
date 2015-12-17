package com.infinities.skyport.distributed.impl.local;

import org.apache.shiro.cache.ehcache.EhCacheManager;

import com.infinities.skyport.distributed.shiro.DistributedCacheManager;

public class LocalCacheManager extends EhCacheManager implements DistributedCacheManager {

	@Override
	public boolean getSchedulerEnabled() {
		return true;
	}
}
