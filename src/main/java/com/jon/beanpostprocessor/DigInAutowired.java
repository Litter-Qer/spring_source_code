package com.jon.beanpostprocessor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DigInAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2", new Bean2()); // creation and initialized will not be executed
        beanFactory.registerSingleton("bean3", new Bean3());
        // resolve @Value annotation
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        // resolve interpretation of @Value placeholders
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        // search for properties @Autowired
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        Bean1 bean1 = new Bean1();
//        System.out.println(bean1);
        // execute during the dependencies injection -----------> InjectionMETADATA
//        beanPostProcessor.postProcessProperties(null, bean1, "bean1");
//        System.out.println(bean1);

        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        // acquire values of Bean1 with @Value and @Autowired
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(beanPostProcessor, "bean1", Bean1.class, null);
        System.out.println(metadata);

        // inject bean1
        metadata.inject(bean1, "bean1", null);
        System.out.println(bean1);

        // acquire value of a type injection
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor descriptor = new DependencyDescriptor(bean3, false);
        Object o = beanFactory.doResolveDependency(descriptor, null, null, null);
        System.out.println(o);

        // acquire value of a method injection
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor descriptor2 = new DependencyDescriptor(new MethodParameter(setBean2, 0), false);
        Object o1 = beanFactory.doResolveDependency(descriptor2, null, null, null);
        System.out.println(o1);

        // acquire value of a value injection
        Method setHome = Bean1.class.getDeclaredMethod("setHome", String.class);
        DependencyDescriptor descriptor3 = new DependencyDescriptor(new MethodParameter(setHome, 0), false);
        Object o2 = beanFactory.doResolveDependency(descriptor3, null, null, null);
        System.out.println(o2);
    }
}
