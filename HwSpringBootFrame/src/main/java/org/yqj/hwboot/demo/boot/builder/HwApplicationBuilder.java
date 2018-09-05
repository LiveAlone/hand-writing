package org.yqj.hwboot.demo.boot.builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.ParentContextApplicationContextInitializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.yqj.hwboot.demo.boot.HwApplication;
import org.yqj.hwboot.demo.boot.HwBanner;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private ConfigurableApplicationContext context;

    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * 父 builder
     */
    private HwApplicationBuilder parent;

    private boolean configuredAsChild = false;

    private boolean registerShutdownHookApplied;

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

    public ConfigurableApplicationContext run(String... args){
        if (isRunning.get()){
            // application 已经运行状态， 可以直接返回
            return context;
        }

        if (isRunning.compareAndSet(false, true)){
            synchronized (isRunning){
                context = build().run(args);
            }
        }

        // 显然 context 启动成功了， context == null 其他进程抢占失败
        return context;
    }

    /**
     * build 构建, 子容器初始化，设置HwApplicationContextBuilder 数据进入 HwApplication 中
     * @return
     */
    public HwApplication build(){
        return build(new String[0]);
    }

    public HwApplication build(String... args){
        // config as child
        configureAsChildIfNecessary(args);
        // set sources
        this.application.addPrimarySources(source);
        return this.application;
    }

    private void configureAsChildIfNecessary(String... args) {
        if (this.parent != null && !this.configuredAsChild){
            // 这是一个滞后变量， 判断是否为 null
            this.configuredAsChild = true;
            if (!this.registerShutdownHookApplied){
                this.application.setRegisterShutdownHook(false);
            }

            // 添加 parent init 参数 的配置方式
            initializers(new ParentContextApplicationContextInitializer(this.parent.run(args)));
        }
    }

    public HwApplicationBuilder initializers(ApplicationContextInitializer<?> initializer){
        this.application.addInitializers(initializer);
        return this;
    }

}
