package com.meygam.cache.spring.memcached;

import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class MemcachedCache implements Cache {

	private final MemcachedClient cache;
	private final int expiration;
	private final String name;

	public MemcachedCache(MemcachedClient cache, int expiration, String name) {
		this.cache = cache;
		this.expiration = expiration;
		this.name = name;
	}

	public void clear() {
		getCache().flush();
	}

	public void evict(Object key) {
		getCache().delete(String.valueOf(key));
	}

	public ValueWrapper get(Object key) {
		Object value = getCache().get(String.valueOf(key));
    	return (value != null ? new SimpleValueWrapper(value) : null);
	}

	public MemcachedClient getCache() {
		return cache;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getName() {
		return name;
	}

	public Object getNativeCache() {
		return getCache();
	}

	public void put(Object key, Object value) {
		getCache().set(String.valueOf(key), getExpiration(), value);
	}

}
