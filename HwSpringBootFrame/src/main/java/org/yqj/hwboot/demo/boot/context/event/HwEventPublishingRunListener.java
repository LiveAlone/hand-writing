package org.yqj.hwboot.demo.boot.context.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.yqj.hwboot.demo.boot.HwApplication;
import org.yqj.hwboot.demo.boot.HwApplicationRunListener;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class HwEventPublishingRunListener implements HwApplicationRunListener, Ordered {

    private final HwApplication application;

    private final String[] args;

    private final SimpleApplicationEventMulticaster initialMulticaster;

    public HwEventPublishingRunListener(HwApplication hwApplication, String[] args) {
        this.application = hwApplication;
        this.args = args;
        this.initialMulticaster = new SimpleApplicationEventMulticaster();
        for (ApplicationListener<?> listener : application.getListeners()) {
            this.initialMulticaster.addApplicationListener(listener);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void starting() {
        log.info("starting log content");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

        log.info("hw event pushing run listener environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener started");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("hw event pushing run listener failed");
    }
}
