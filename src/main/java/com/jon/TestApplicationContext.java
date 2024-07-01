package com.jon;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

@Log4j2
public class TestApplicationContext {

    public static void main(String[] args) {
//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
//        testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();
    }

    private static void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext();
    }

    /**
     *
     */
    private static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        for (String name : context.getBeanDefinitionNames()) {
            log.info("Bean name: {}", name);
        }

        Bean1 bean1 = context.getBean(Bean2.class).getBean1();
        log.info("Bean1 ---> {}", bean1);
    }

    /**
     * based on the file in disk
     */
    private static void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("Q:\\Aututech\\spring_source_code\\src\\main\\resources\\bean.xml");
        for (String name : context.getBeanDefinitionNames()) {
            log.info("Bean name: {}", name);
        }

        Bean1 bean1 = context.getBean(Bean2.class).getBean1();
        log.info("Bean1 ---> {}", bean1);

        // internal processing
        log.info("---------------------------------");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new FileSystemResource("Q:\\Aututech\\spring_source_code\\src\\main\\resources\\bean.xml"));
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            log.info("After XML loading Bean name: {}", name);
        }
    }

    /**
     * based on xml files under resources
     */
    private static void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        for (String name : context.getBeanDefinitionNames()) {
            log.info("Bean name: {}", name);
        }

        Bean1 bean1 = context.getBean(Bean2.class).getBean1();
        log.info("Bean1 ---> {}", bean1);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1) {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean1 {

    }

    static class Bean2 {
        private Bean1 bean1;

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }

        public Bean1 getBean1() {
            return bean1;
        }
    }
}
