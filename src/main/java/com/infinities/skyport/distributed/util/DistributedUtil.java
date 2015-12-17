package com.infinities.skyport.distributed.util;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.skyport.distributed.DistributedObjectFactory;
import com.infinities.skyport.distributed.DistributedObjectFactory.Delegate;
import com.infinities.skyport.distributed.impl.hazelcast.HazelcastHelper;
import com.infinities.skyport.distributed.impl.local.LocalInstance;
import com.infinities.skyport.model.configuration.Configuration;
import com.infinities.skyport.util.PropertiesHolder;

public class DistributedUtil {

	private final static Logger logger = LoggerFactory.getLogger(DistributedUtil.class);
	public static Delegate defaultDelegate;


	private DistributedUtil() {

	}

	public static String generateDistributedName(Configuration configuration) {
		String id = configuration.getId();
		// String provider = configuration.getProviderName();
		// String cloud = configuration.getCloudName();
		// String regionId = configuration.getRegionId();
		// String account = configuration.getAccount();

		checkNotNull(id, "id cannot be null");
		// checkNotNull(provider, "provider name cannot be null");
		// checkNotNull(cloud, "cloud name cannot be null");
		// checkNotNull(regionId, "region id cannot be null");
		// checkNotNull(account, "account cannot be null");
		return id;// generateDistributedName(name, provider, cloud, regionId,
					// account);
	}

	// public static String generateDistributedName(String name, String
	// provider, String cloud, String regionId, String account) {
	// return MessageFormat.format("{0}[ {1}, {2}, {3}, {4}, {5}]", name,
	// provider, cloud, regionId, account);
	// }

	public static DistributedObjectFactory getDistributedObjectFactory(String name) throws Exception {
		return getDistributedObjectFactory(getDelegate(), name);
	}

	public static DistributedObjectFactory getDistributedObjectFactory(Delegate delegate, String name) throws Exception {
		switch (delegate) {
		case hazelcast:
			return HazelcastHelper.getObjectFactoryByName(name);
		case disabled:
			return LocalInstance.getInstanceByName(name);
		default:
			return LocalInstance.getInstanceByName(name);
		}
	}

	public static DistributedObjectFactory getDefaultDistributedObjectFactory() throws Exception {
		return getDistributedObjectFactory(DistributedObjectFactory.DEFAULT_UNIT_NAME);
	}

	public static Delegate getDelegate() {
		Delegate delegate = Delegate.disabled;
		if (defaultDelegate != null) {
			return defaultDelegate;
		}
		try {
			delegate = Delegate.valueOf(PropertiesHolder.getProperty(PropertiesHolder.CLUSTER_DELEGATE));
		} catch (Throwable e) {
			logger.warn("cannot parse delegate, return disabled", e);
			delegate = Delegate.disabled;
		}

		return delegate;
	}
}
