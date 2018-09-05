package org.yqj.hwboot.demo.boot;

/**
 * Description: 执行 banner
 *
 * @author yaoqijun
 * @date 2018/9/5
 * Email: qijunyao@xiaohongshu.com
 */
public interface HwBanner {

    /**
     * 上下文输入方式, todo 参数配置
     */
    void printBanner();


    /**
     * banner 不同的模式
     */
    enum Mode{
        /**
         * 关闭
         */
        OFF,

        /**
         * std 方式
         */
        CONSOLE,

        /**
         * 日志文件
         */
        LOG
    }
}
