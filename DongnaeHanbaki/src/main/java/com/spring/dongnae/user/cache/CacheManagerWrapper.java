package com.spring.dongnae.user.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheManagerWrapper {
    private final CacheManager cacheManager;
    private Map<Object, Object> cacheEntries;

    public CacheManagerWrapper(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.cacheEntries = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        refreshCacheEntries();
    }

    public synchronized Map<Object, Object> getAllCacheEntries() {
        return new HashMap<>(cacheEntries);
    }
    
    public synchronized void refreshCacheEntries() {
        cacheEntries.clear();
        Cache cache = cacheManager.getCache("users");
        if (cache != null) {
            net.sf.ehcache.Ehcache ehcache = ((net.sf.ehcache.Cache) cache.getNativeCache());
            for (Object key : ehcache.getKeys()) {
                net.sf.ehcache.Element element = ehcache.get(key);
                if (element != null) {
                    cacheEntries.put(element.getObjectKey(), element.getObjectValue());
                }
            }
        }
    }

    public <T> T getCacheEntry(String cacheName, String key, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return type.cast(valueWrapper.get());
            }
        }
        return null;
    }
    
    public void putCacheEntry(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
            cacheEntries.put(key, value); // 캐시 엔트리에 명시적으로 추가
        }
    }
    
    public void printAllCacheKeys() {
    	System.out.println("프린트 캐쉬 실행");
        Cache cache = cacheManager.getCache("users");
        if (cache != null) {
            net.sf.ehcache.Ehcache ehcache = ((net.sf.ehcache.Cache) cache.getNativeCache());
            for (Object key : ehcache.getKeys()) {
                System.out.println("Cache Key: " + key);
            }
        }
    }
}