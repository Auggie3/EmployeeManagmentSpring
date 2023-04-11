package com.auggie.EmployeeManagement.configurations;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CacheHelper {
    @Caching(
            evict = {
                    @CacheEvict(value = "employees", allEntries = true),
                    @CacheEvict(value = "employee", allEntries = true),
                    @CacheEvict(value = "employee-details", allEntries = true)
            }
    )
    public void evictAllEntriesFromCache(){
        log.warn("All cache entries are evicted!!!");
    }
}
