package org.yqj.hwboot.demo.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Description: hw application 启动主要类
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class HwApplication {

    private HwBanner banner;

    private Set<Class<?>> primarySources;

    /**
     * todo ?? 默认 TRUE 数据格式类型
     */
    private boolean registerShutdownHook = true;

    private List<ApplicationContextInitializer<?>> initializers;

    private List<ApplicationListener<?>> listeners;

    /**
     * spring bean 主要 Bean Loader
     * @param primarySource
     */
    public HwApplication(Class<?>... primarySource){
        this(null, primarySource);
    }

    private ResourceLoader resourceLoader;

    private WebApplicationType webApplicationType;

    /**
     * todo 对 变量的替换
     */
    private static final String REACTIVE_WEB_ENVIRONMENT_CLASS = "org.springframework."
            + "web.reactive.DispatcherHandler";

    private static final String MVC_WEB_ENVIRONMENT_CLASS = "org.springframework."
            + "web.servlet.DispatcherServlet";

    private static final String JERSEY_WEB_ENVIRONMENT_CLASS = "org.glassfish.jersey.server.ResourceConfig";

    /**
     * 没有servlet 默认 非容器方式启动
     */
    private static final String[] WEB_ENVIRONMENT_CLASSES = { "javax.servlet.Servlet",
            "org.springframework.web.context.ConfigurableWebApplicationContext" };

    private static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

    private boolean headless = true;

    /**
     * 程序运行 Main 参数 Class 获取方式
     */
    private Class<?> mainApplicationClass;

    /**
     * 加载对应不同的 bean 资源
     * @param hwResourceLoader
     * @param primarySource
     */
    public HwApplication(ResourceLoader hwResourceLoader, Class<?>... primarySources){
        this.resourceLoader = hwResourceLoader;
        // 可以知道 primary source 是一切的 source 来源 定义扩展， 资源加载方式
        Assert.notNull(primarySources, "primary source not allow null");
        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
        this.webApplicationType = deduceWebApplicationType();

        // initalizers
        setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
        setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

        this.mainApplicationClass = deduceMainApplicationClass();
    }

    private Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTraceElements = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if ("main".equals(stackTraceElement.getMethodName())){
                    return Class.forName(stackTraceElement.getClassName());
                }
            }

        }catch (ClassNotFoundException e){

        }
        return null;
    }

    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type){
        return getSpringFactoriesInstances(type, new Class[0]);
    }

    /**
     * 支持 class 初始化 instance 同时参数指定方式
     * @param type
     * @param parameterTypes
     * @param args
     * @param <T>
     * @return
     */
    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
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

    private WebApplicationType deduceWebApplicationType(){
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

    public void setBanner(HwBanner banner) {
        this.banner = banner;
    }

    public ConfigurableApplicationContext run(String... args){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ConfigurableApplicationContext context = null;
        Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
        configureHeadlessProperty();
        HwApplicationRunListeners listeners = getRunListeners(args);
        listeners.starting();
        try {
            // todo running
            log.info("1 设置 handle less property, 设置 awt 的 属性 信息 配置方式等");

            log.info("2. listeners 通知 starting 项目启动开始了");

            log.info("3. 准备 environment  上下文环境 配置准备");

            log.info("4 创建对应的 application context 上下文");

            log.info("5 prepare context 准备上下文");

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

    public HwApplicationRunListeners getRunListeners(String[] args){
        Class<?>[] types = new Class<?>[]{HwApplication.class, String[].class};
        return new HwApplicationRunListeners(getSpringFactoriesInstances(HwApplicationRunListener.class, types, this, args));
    }

    private void configureHeadlessProperty() {
        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(
                SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(this.headless)));
    }

    public void addPrimarySources(Collection<Class<?>> additionalPrimarySources) {
        this.primarySources.addAll(additionalPrimarySources);
    }

    public void setRegisterShutdownHook(boolean registerShutdownHook) {
        this.registerShutdownHook = registerShutdownHook;
    }

    public void setInitializers(
            Collection<? extends ApplicationContextInitializer<?>> initializers) {
        this.initializers = new ArrayList<>();
        this.initializers.addAll(initializers);
    }

    public void addInitializers(ApplicationContextInitializer<?>... initializers) {
        this.initializers.addAll(Arrays.asList(initializers));
    }

    public void setListeners(Collection<? extends ApplicationListener<?>> listeners){
        this.listeners = new ArrayList<>();
        this.listeners.addAll(listeners);
    }

    public void addListener(ApplicationListener<?>... listener){
        this.listeners.addAll(Arrays.asList(listener));
    }
}
