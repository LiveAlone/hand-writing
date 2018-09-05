package org.yqj.hwboot.demo.boot;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
public interface HwApplicationRunListener {

    /**
     * 容器启动开始时刻
     */
    void starting();

    /**
     * 环境上下文准备完成
     * @param environment
     */
    void environmentPrepared(ConfigurableEnvironment environment);

    /**
     * context 注备完成 但是 source 等资源没有被加载等
     * @param context
     */
    void contextPrepared(ConfigurableApplicationContext context);

    /**
     * resource 等 资源 已经被 loaded 了， 但是没有 refresh
     * @param context
     */
    void contextLoaded(ConfigurableApplicationContext context);

    /**
     * application context refreshed
     * @param context
     */
    void started(ConfigurableApplicationContext context);

    /**
     * all runner 执行完成 例如 CommandLineRunners 执行完成
     * @param context
     */
    void running(ConfigurableApplicationContext context);

    /**
     * 容器启动失败，异常信息抛出
     * @param context
     * @param exception
     */
    void failed(ConfigurableApplicationContext context, Throwable exception);
}
