package org.yqj.hwboot.demo.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.yqj.hwboot.demo.context.HwApplicationContextInitializer;
import org.yqj.hwboot.demo.context.HwConfigurableApplicationContext;
import org.yqj.hwboot.demo.core.io.HwResourceLoader;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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

    private List<HwApplicationContextInitializer<?>> initializers;

    /**
     * spring bean 主要 Bean Loader
     * @param primarySource
     */
    public HwApplication(Class<?>... primarySource){
        this(null, primarySource);
    }

    /**
     * 加载对应不同的 bean 资源
     * @param hwResourceLoader
     * @param primarySource
     */
    public HwApplication(HwResourceLoader hwResourceLoader, Class<?>... primarySources){
        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
        log.info("start create hw application with resourceLoader:{}, primarySource:{}", hwResourceLoader, primarySources);
    }

    public void setBanner(HwBanner banner) {
        this.banner = banner;
    }

    public HwConfigurableApplicationContext run(String... args){
        log.info("hw configuration application context is to running, args: {}", args);
        return null;
    }

    public void addPrimarySources(Collection<Class<?>> additionalPrimarySources) {
        this.primarySources.addAll(additionalPrimarySources);
    }

    public void setRegisterShutdownHook(boolean registerShutdownHook) {
        this.registerShutdownHook = registerShutdownHook;
    }

    public void addInitializers(HwApplicationContextInitializer<?>... initializers) {
        this.initializers.addAll(Arrays.asList(initializers));
    }
}
