package com.jon.beanfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Component2 {

    public static final Logger log = LoggerFactory.getLogger(Component2.class);

    @Override
    public String toString() {
        return "component 2";
    }

    @EventListener
    public void listen(RegistrationEvent event) {
        log.info("listening event: {}", event);
    }
}
