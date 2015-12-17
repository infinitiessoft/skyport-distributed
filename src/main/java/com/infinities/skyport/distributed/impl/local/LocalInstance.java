package com.infinities.skyport.distributed.impl.local;

import java.util.concurrent.ConcurrentHashMap;

import com.infinities.skyport.distributed.DistributedObjectFactory;

public class LocalInstance {

	public final static ConcurrentHashMap<String, DistributedObjectFactory> instances = new ConcurrentHashMap<String, DistributedObjectFactory>();


	private LocalInstance() {

	}

	public synchronized static DistributedObjectFactory getInstanceByName(String topology) {
		DistributedObjectFactory instance = instances.get(topology);
		if (instance == null) {
			instance = newLocalObjectFactory(topology);
		}

		return instance;
	}

	private synchronized static LocalObjectFactory newLocalObjectFactory(String topologyName) {
		LocalObjectFactory factory = new LocalObjectFactory(topologyName);
		if (instances.putIfAbsent(topologyName, factory) != null) {
			throw new IllegalStateException("An instance for the name " + topologyName + " already exists!");
		}

		return factory;
	}

	public static String getGroup() {
		return "none";
	}

}
