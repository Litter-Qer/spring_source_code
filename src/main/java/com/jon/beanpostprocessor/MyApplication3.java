package com.jon.beanpostprocessor;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

public class MyApplication3 {
    public static void main(String[] args) {
        // create a generic application context, it is a clean container
        GenericApplicationContext context = new GenericApplicationContext();

        // register beans
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        // set autowired candidate resolver to resolve @Value
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // interpret @Autowired Bean Processor
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // interpret @Resource @PostConstruct @PreDestroy
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // interpret @Configuration related annotations
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        // initialize context, execute bean post processors, add bean post processors, instantiate all singletons
        context.refresh();

        System.out.println(context.getBean(Bean4.class));

        // destroy the context container
        context.close();
    }
}
