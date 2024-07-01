package com.jon.lifecycle;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log4j2
@Component
public class LifeCycleBean {
    public LifeCycleBean() {
        log.debug("1. construct life cycle bean");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String name) {
        log.debug("2. autowiring life cycle bean {}", name);
    }

    @PostConstruct
    public void init() {
        log.debug("3. init life cycle bean");
    }

    @PreDestroy
    public void destroy() {
        log.debug("4. destroying life cycle bean");
    }
}
