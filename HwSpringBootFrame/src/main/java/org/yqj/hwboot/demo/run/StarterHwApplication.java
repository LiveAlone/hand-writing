package org.yqj.hwboot.demo.run;

import org.yqj.hwboot.demo.boot.builder.HwApplicationBuilder;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
public class StarterHwApplication {

    public static void main(String[] args) {
        String[] arguments = {"args1"};
        new HwApplicationBuilder()
                .banner(new RunnerBanner())
                .sources(StarterHwApplication.class)
                .run(arguments);
    }

}
