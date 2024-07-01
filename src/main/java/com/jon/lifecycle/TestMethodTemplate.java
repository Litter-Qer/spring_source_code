package com.jon.lifecycle;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class TestMethodTemplate {

    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(bean -> log.info("inject bean class name: {}", bean.getClass()));
        beanFactory.getBean();
    }


    static class MyBeanFactory {
        public Object getBean() {
            Object bean = new Object();
            log.info("Bean created");
            for (MyBeanPostProcessor processor : processors) {
                processor.inject(bean);
            }
            return bean;
        }

        private final List<MyBeanPostProcessor> processors = new ArrayList<>();

        public void addBeanPostProcessor(MyBeanPostProcessor processor) {
            processors.add(processor);
        }
    }

    interface MyBeanPostProcessor {
        /**
         * dependencies injection
         */
        void inject(Object bean);
    }
}
