package com.jon.beanfactory;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Log4j2
public class MyBeanFactory {

    /*
        Bean Factory will not do:
            - add BeanFactory post processors
            - add Bean post processors
            - instantiate singletons
            - interpret ${} or #{}
     */

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
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        // find some post processors who would process annotations orderly
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            log.info("after bean factory post process: {}", beanDefinitionName);
        }

        // find that bean2 is null in Bean1
//        log.info("get bean2 --> {}", beanFactory.getBean(Bean1.class).getBean2());

        // bean post processors, used to extend features of bean life cycle, such as @Autowired, @Resource...
        // add bean post processors to bean factory
        for (BeanPostProcessor beanPostProcessor : beanFactory.getBeansOfType(BeanPostProcessor.class).values()) {
            log.info(">>>>>>>>>>>> bean post process <<<<<<<<<<<<<: {}", beanPostProcessor.getClass().getName());
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }

        // create beans before using getBean. creates all singleton beans
        beanFactory.preInstantiateSingletons();

        log.info(">>>>>>>>>>>>>>>>>>>>>> Splitting Line >>>>>>>>>>>>>>>>>>>>>>");
        // getBean will create a new Bean. it will be null again if Bean1 is created before this (singleton mode)
        log.info("after bean processing, get bean2 --> {}", beanFactory.getBean(Bean1.class).getBean2());

        // bean post processor has order, so bean 3 is injected before bean 4
        log.info("after bean processing, get bean1 --> {}", beanFactory.getBean(Bean1.class).getInter());

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

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    @Log4j2
    static class Bean1 {
        public Bean1() {
            log.info("constructing bean1");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        public Inter getInter() {
            return bean3;
        }
    }

    @Log4j2
    static class Bean2 {
        public Bean2() {
            log.info("constructing bean2");
        }
    }

    interface Inter {
    }

    static class Bean3 implements Inter {
        public Bean3() {
            log.info("constructing bean3");
        }
    }

    static class Bean4 implements Inter {
        public Bean4() {
            log.info("constructing bean4");
        }
    }
}
