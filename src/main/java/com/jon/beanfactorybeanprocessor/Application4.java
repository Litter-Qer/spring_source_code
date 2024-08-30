package com.jon.beanfactorybeanprocessor;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

@Log4j2
public class Application4 {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);

        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            log.info("Bean Definition -------> {}", beanDefinitionName);
        }

        context.close();
    }
}
