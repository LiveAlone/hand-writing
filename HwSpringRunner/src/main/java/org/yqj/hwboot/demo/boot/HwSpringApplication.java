package org.yqj.hwboot.demo.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class HwSpringApplication extends SpringApplication {

    public HwSpringApplication(Class<?>... primarySources) {
        super(primarySources);
    }

    @Override
    public ConfigurableApplicationContext run(String... args) {
        log.info("do nothing");
        return null;
    }
}
