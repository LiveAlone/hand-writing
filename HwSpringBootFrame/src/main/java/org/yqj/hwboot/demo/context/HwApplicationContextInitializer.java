package org.yqj.hwboot.demo.context;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
public interface HwApplicationContextInitializer<C extends HwConfigurableApplicationContext>{

    /**
     * 初始化ApplicationContext
     * @param applicationContext
     */
    void initialize(C applicationContext);

}
