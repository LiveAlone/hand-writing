package org.yqj.hwboot.demo.boot.builder;

import lombok.extern.slf4j.Slf4j;
import org.yqj.hwboot.demo.boot.HwApplication;
import org.yqj.hwboot.demo.boot.HwBanner;
import org.yqj.hwboot.demo.context.HwConfigurableApplicationContext;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Description: hw application builder 构建方式
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class HwApplicationBuilder {

    /**
     * 构造 primary source 资源方式
     */
    private Set<Class<?>> source = new LinkedHashSet<>();

    private HwApplication application;

    public HwApplicationBuilder(Class<?>... source) {
        this.application = createApplication(source);
    }

    /**
     * 子类构建不同的 application 上下文
     * @param source
     * @return
     */
    protected HwApplication createApplication(Class<?>... source){
        return new HwApplication(source);
    }


    public HwApplicationBuilder banner(HwBanner banner){
        this.application.setBanner(banner);
        return this;
    }

    public HwApplicationBuilder sources(Class<?>... primarySource){
        this.source.addAll(new LinkedHashSet<>(Arrays.asList(primarySource)));
        return this;
    }

    public HwConfigurableApplicationContext run(String... args){
        log.info("start run in spring application context");
        return null;
    }
}
