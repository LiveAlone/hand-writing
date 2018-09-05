package org.yqj.hwboot.demo.boot;

import lombok.extern.slf4j.Slf4j;
import org.yqj.hwboot.demo.core.io.HwResourceLoader;

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
    public HwApplication(HwResourceLoader hwResourceLoader, Class<?>... primarySource){
        log.info("start create hw application with resourceLoader:{}, primarySource:{}", hwResourceLoader, primarySource);
    }

    public void setBanner(HwBanner banner) {
        this.banner = banner;
    }
}
