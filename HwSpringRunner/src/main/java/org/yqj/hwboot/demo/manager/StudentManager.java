package org.yqj.hwboot.demo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2018/9/6
 * Email: qijunyao@xiaohongshu.com
 */
@Component
public class StudentManager {

    @Autowired
    private TeacherManager teacherManager;

}
