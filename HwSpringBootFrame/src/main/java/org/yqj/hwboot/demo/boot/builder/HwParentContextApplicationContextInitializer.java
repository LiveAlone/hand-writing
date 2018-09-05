package org.yqj.hwboot.demo.boot.builder;

import org.yqj.hwboot.demo.context.HwApplicationContext;
import org.yqj.hwboot.demo.context.HwApplicationContextInitializer;
import org.yqj.hwboot.demo.context.HwConfigurableApplicationContext;
import org.yqj.hwboot.demo.core.HwOrdered;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
public class HwParentContextApplicationContextInitializer implements HwApplicationContextInitializer<HwConfigurableApplicationContext>, HwOrdered {

    public HwParentContextApplicationContextInitializer(HwApplicationContext hwApplicationContext) {
    }

    @Override
    public void initialize(HwConfigurableApplicationContext applicationContext) {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
