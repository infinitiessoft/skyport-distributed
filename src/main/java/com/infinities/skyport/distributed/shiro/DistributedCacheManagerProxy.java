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
