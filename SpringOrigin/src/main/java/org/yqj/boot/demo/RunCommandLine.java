package org.yqj.boot.demo;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yqj.boot.demo.manager.StudentManager;
import org.yqj.boot.demo.manager.TeacherManager;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/8/15
 * Email: yaoqijunmail@foxmail.com
 */
@Component
@Slf4j
public class RunCommandLine implements CommandLineRunner{

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private TeacherManager teacherManager;

    @Override
    public void run(String... args) throws Exception {
        log.info(" command info run");
        log.warn("now command line run ");
        log.warn("args content is " + Lists.newArrayList(args).toString());
    }
}
