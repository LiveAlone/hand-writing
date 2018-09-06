package org.springframework.boot;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class HwSpringApplication extends SpringApplication {

    private static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

    private boolean headless = true;

    public static final String DEFAULT_WEB_CONTEXT_CLASS = "org.springframework.boot."
            + "web.servlet.context.AnnotationConfigServletWebServerApplicationContext";

    private static final String[] WEB_ENVIRONMENT_CLASSES = { "javax.servlet.Servlet",
            "org.springframework.web.context.ConfigurableWebApplicationContext" };

    private static final String REACTIVE_WEB_ENVIRONMENT_CLASS = "org.springframework."
            + "web.reactive.DispatcherHandler";

    private static final String MVC_WEB_ENVIRONMENT_CLASS = "org.springframework."
            + "web.servlet.DispatcherServlet";

    private static final String JERSEY_WEB_ENVIRONMENT_CLASS = "org.glassfish.jersey.server.ResourceConfig";

    private WebApplicationType webApplicationType;

    private ConfigurableEnvironment environment;

    public HwSpringApplication(Class<?>... primarySources){
        super(primarySources);

        webApplicationType = deduceWebApplicationType();
    }

    @Override
    public ConfigurableApplicationContext run(String... args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ConfigurableApplicationContext context = null;
        Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
        configureHeadlessProperty();
        SpringApplicationRunListeners listeners = getRunListeners(args);
        listeners.starting();
        try {
            ApplicationArguments applicationArguments = new DefaultApplicationArguments(
                    args);
            ConfigurableEnvironment environment = prepareEnvironment(listeners,
                    applicationArguments);

            configureIgnoreBeanInfo(environment);

            context = createApplicationContext();

            exceptionReporters = getSpringFactoriesInstances(
                    SpringBootExceptionReporter.class,
                    new Class[] { ConfigurableApplicationContext.class }, context);

            prepareContext(context, environment, listeners, applicationArguments, null);

            log.info("6 refresh 刷新上下文");

            log.info("7. after refresh 刷新后 上下文");

            log.info("8 listener 通知 启动已完成");
        }catch (Throwable ex){
            throw new IllegalStateException(ex);
        }


        try {

            log.info("9 捕获异常信息， 或者返回 context 上下文信息内容");
        }catch (Throwable ex){
            throw new IllegalStateException(ex);
        }

        stopWatch.stop();

        return context;
    }

    private void prepareContext(ConfigurableApplicationContext context,
                                ConfigurableEnvironment environment, SpringApplicationRunListeners listeners,
                                ApplicationArguments applicationArguments, Banner printedBanner) {
        context.setEnvironment(environment);
        postProcessApplicationContext(context);
        applyInitializers(context);
        listeners.contextPrepared(context);

        // Add boot specific singleton beans
        context.getBeanFactory().registerSingleton("springApplicationArguments",
                applicationArguments);
        if (printedBanner != null) {
            context.getBeanFactory().registerSingleton("springBootBanner", printedBanner);
        }

        // Load the sources
        Set<Object> sources = getAllSources();
        Assert.notEmpty(sources, "Sources must not be empty");
        load(context, sources.toArray(new Object[0]));
        listeners.contextLoaded(context);
    }

    private void configureIgnoreBeanInfo(ConfigurableEnvironment environment) {
        if (System.getProperty(
                CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME) == null) {
            Boolean ignore = environment.getProperty("spring.beaninfo.ignore",
                    Boolean.class, Boolean.TRUE);
            System.setProperty(CachedIntrospectionResults.IGNORE_BEANINFO_PROPERTY_NAME,
                    ignore.toString());
        }
    }

    private ConfigurableEnvironment prepareEnvironment(
            SpringApplicationRunListeners listeners,
            ApplicationArguments applicationArguments) {
        // Create and configure the environment
        ConfigurableEnvironment environment = getOrCreateEnvironment();
        configureEnvironment(environment, applicationArguments.getSourceArgs());
        listeners.environmentPrepared(environment);
        bindToSpringApplication(environment);
        if (this.webApplicationType == WebApplicationType.NONE) {
            environment = new EnvironmentConverter(getClassLoader())
                    .convertToStandardEnvironmentIfNecessary(environment);
        }
        ConfigurationPropertySources.attach(environment);
        return environment;
    }

    private ConfigurableEnvironment getOrCreateEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        if (this.webApplicationType == WebApplicationType.SERVLET) {
            return new StandardServletEnvironment();
        }
        return new StandardEnvironment();
    }

    private WebApplicationType deduceWebApplicationType() {
        if (ClassUtils.isPresent(REACTIVE_WEB_ENVIRONMENT_CLASS, null)
                && !ClassUtils.isPresent(MVC_WEB_ENVIRONMENT_CLASS, null)
                && !ClassUtils.isPresent(JERSEY_WEB_ENVIRONMENT_CLASS, null)) {
            return WebApplicationType.REACTIVE;
        }
        for (String className : WEB_ENVIRONMENT_CLASSES) {
            if (!ClassUtils.isPresent(className, null)) {
                return WebApplicationType.NONE;
            }
        }
        return WebApplicationType.SERVLET;
    }

    private SpringApplicationRunListeners getRunListeners(String[] args) {
        Class<?>[] types = new Class<?>[] { SpringApplication.class, String[].class };
        return new SpringApplicationRunListeners(null, getSpringFactoriesInstances(
                SpringApplicationRunListener.class, types, this, args));
    }

    private void configureHeadlessProperty() {
        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(
                SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(this.headless)));
    }

    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type,
                                                          Class<?>[] parameterTypes, Object... args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // Use names and ensure unique to protect against duplicates
        Set<String> names = new LinkedHashSet<>(
                SpringFactoriesLoader.loadFactoryNames(type, classLoader));
        List<T> instances = createSpringFactoriesInstances(type, parameterTypes,
                classLoader, args, names);
        AnnotationAwareOrderComparator.sort(instances);
        return instances;
    }

    private <T> List<T> createSpringFactoriesInstances(Class<T> type,
                                                       Class<?>[] parameterTypes, ClassLoader classLoader, Object[] args,
                                                       Set<String> names) {
        List<T> instances = new ArrayList<>(names.size());
        for (String name : names) {
            try {
                Class<?> instanceClass = ClassUtils.forName(name, classLoader);
                Assert.isAssignable(type, instanceClass);
                Constructor<?> constructor = instanceClass
                        .getDeclaredConstructor(parameterTypes);
                T instance = (T) BeanUtils.instantiateClass(constructor, args);
                instances.add(instance);
            }
            catch (Throwable ex) {
                throw new IllegalArgumentException(
                        "Cannot instantiate " + type + " : " + name, ex);
            }
        }
        return instances;
    }
}
