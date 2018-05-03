package com.tsxy.carl.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.Doctor.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.Doctor.class.getName() + ".depts", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ConsultRoom.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.RegistrationBook.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ContentComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ContentComment.class.getName() + ".replys", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ContentCommentReply.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ThirdLevelDepartment.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ThirdLevelDepartment.class.getName() + ".rooms", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ThirdLevelDepartment.class.getName() + ".doctors", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ThirdLevelDepartment.class.getName() + ".secondLevelDepts", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.SecondLevelDepartment.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.SecondLevelDepartment.class.getName() + ".depts", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.Doctor.class.getName() + ".doctorVisits", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.ConsultRoom.class.getName() + ".doctorVisits", jcacheConfiguration);
            cm.createCache(com.tsxy.carl.domain.DoctorVisit.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
