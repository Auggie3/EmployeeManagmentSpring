package com.auggie.EmployeeManagement.configurations;

import lombok.extern.log4j.Log4j2;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CacheLoggerEventConfiguration implements CacheEventListener<Object,Object> {

    //TODO: make cache logging work

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
       log.info(
               "Event type: {}",
               cacheEvent.getType()
       );
    }
}
