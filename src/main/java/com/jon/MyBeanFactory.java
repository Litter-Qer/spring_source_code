package com.jon;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Log4j2
public class MyBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // definition of a bean, (class, scope, initial, deletion)
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        // cannot find bean1 and bean2, meaning beanFactory will not interpret @Bean annotation
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            log.info("before: {}", beanDefinitionName);
        }

        // add regular post processors to beanFactory, extend bean definitions in the bean factory
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // find some post processors who would process annotations orderly
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            log.info("after: {}", beanDefinitionName);
        }

        // get all bean post processors of beanFactory and then process
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor->{
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        // find some post processors who would process annotations orderly
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            log.info("after post process: {}", beanDefinitionName);
        }

    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

    }

    static class Bean1 {
        public static final Logger log = LoggerFactory.getLogger(Bean1.class);
    }

    static class Bean2 {
        public static final Logger log = LoggerFactory.getLogger(Bean2.class);
    }
}
