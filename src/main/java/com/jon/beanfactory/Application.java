package com.jon.beanfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class Application {
    public static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        /*
            extract beans from the Bean factory, actual beans are stored in the SingletonObjects map
            DefaultSingletonBeanRegistry is an implementation of BeanFactory including all beans
         */
//        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
//        singletonObjects.setAccessible(true);
//        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
//        Map<String, Object> objs = (Map<String, Object>) singletonObjects.get(beanFactory);
//
//        objs.entrySet().stream().filter(k -> k.getKey().contains("component")).forEach(e -> {
//            log.info("key: {}, value: {}", e.getKey(), e.getValue());
//        });

        /*
            Application Context has more features than BeanFactory
                - MessageSource:  language translation
                - ResourcePatternResolver: path find (using expression match)
                - ApplicationEventPublisher: publish event objects
                - EnvironmentCapable: read env info such as yaml, system configs
         */
//        String cn = context.getMessage("hi", null, Locale.CHINA);
//        String en = context.getMessage("hi", null, Locale.ENGLISH);
//        log.info("original string: hi, chinese: {}, english: {}", cn, en);
//
//        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
//        for (Resource resource : resources) {
//            log.info(String.valueOf(resource));
//        }
//
//        String javaPath = context.getEnvironment().getProperty("JAVA_HOME");
//        String port = context.getEnvironment().getProperty("server.port");
//        log.info("java path: {}, port: {}", javaPath, port);

//        context.publishEvent(new RegistrationEvent(context));

    }
}
