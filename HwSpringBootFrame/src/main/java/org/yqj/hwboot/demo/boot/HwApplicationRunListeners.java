package org.yqj.hwboot.demo.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description: 容器运行时刻容器监听
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class HwApplicationRunListeners {

    private final List<HwApplicationRunListener> listeners;

    public HwApplicationRunListeners(Collection<? extends HwApplicationRunListener> listeners) {
        this.listeners = new ArrayList<>(listeners);
    }

    public void starting() {
        for (HwApplicationRunListener listener : this.listeners) {
            listener.starting();
        }
    }

    public void environmentPrepared(ConfigurableEnvironment environment) {

        log.info("hw event pushing run listener environmentPrepared");
    }

    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener contextPrepared");
    }

    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener contextLoaded");
    }

    public void started(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener started");
    }

    public void running(ConfigurableApplicationContext context) {
        log.info("hw event pushing run listener started");
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("hw event pushing run listener failed");
    }

}
