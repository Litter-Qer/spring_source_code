package com.jon.beanfactory;

import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {

    /**
     * create a new registration event
     * @param source who (actual bean object) is publishing the event
     */
    public RegistrationEvent(Object source) {
        super(source);
    }
}
