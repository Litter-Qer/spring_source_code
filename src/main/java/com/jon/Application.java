package com.jon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;


@SpringBootApplication
public class Application {
    public static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        /*
            extract beans from the Bean factory, actual beans are stored in the SingletonObjects map
            DefaultSingletonBeanRegistry is an implementation of BeanFactory including all beans
         */
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> objs = (Map<String, Object>) singletonObjects.get(beanFactory);

        objs.entrySet().stream().filter(k -> k.getKey().contains("component")).forEach(e -> {
            log.info("key: {}, value: {}", e.getKey(), e.getValue());
        });

        /*
            Application Context has more features than BeanFactory
         */
        
    }
}
