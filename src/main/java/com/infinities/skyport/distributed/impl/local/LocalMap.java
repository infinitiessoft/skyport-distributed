package com.infinities.skyport.distributed.impl.local;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.infinities.skyport.distributed.DistributedMap;

public class LocalMap<K, V> implements DistributedMap<K, V> {
	
	private final ConcurrentHashMap<K, V> delegate = new ConcurrentHashMap<K, V>();

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return delegate.entrySet();
	}

	@Override
	public V get(Object key) {
		return delegate.get(key);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return delegate.keySet();
	}

	@Override
	public V put(K key, V value) {
		return delegate.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		delegate.putAll(m);
	}

	@Override
	public V remove(Object key) {
		return delegate.remove(key);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Collection<V> values() {
		return delegate.values();
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public boolean tryLock(K key) {
		return true;
	}

	@Override
	public void lock(K key) {
		
	}

	@Override
	public void unlock(K key) {
		
	}

	@Override
	public void set(K key, V value) {
		delegate.put(key, value);
	}

	@Override
	public Set<K> localKeySet() {
		return delegate.keySet();
	}

}
