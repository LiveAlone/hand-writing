package org.yqj.hwboot.demo.run;

import lombok.extern.slf4j.Slf4j;
import org.yqj.hwboot.demo.boot.HwBanner;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
@Slf4j
public class RunnerBanner implements HwBanner {
    @Override
    public void printBanner() {
        log.info("start out put banner info");
    }
}
