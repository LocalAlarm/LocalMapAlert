package com.spring.dongnae.user.cache;

import java.io.IOException;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.sf.ehcache.CacheException;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() throws CacheException, IOException {
        // CacheManager가 이미 존재하면 해당 CacheManager를 재사용
        return net.sf.ehcache.CacheManager.create(new ClassPathResource("ehcache.xml").getInputStream());
    }

    @Bean
    public EhCacheCacheManager cacheManager() throws CacheException, IOException {
        return new EhCacheCacheManager(ehCacheManager());
    }
}