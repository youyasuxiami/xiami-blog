package com.xiami.utils;

import com.xiami.dto.SysJobForm;
import lombok.extern.slf4j.Slf4j;
import org.quartz.TriggerKey;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­22 8:53
 */
@Slf4j
public  class QuartzUtils {

    public static Class  executePathToClazz(String executePath) throws ClassNotFoundException {
            Class aClass = Class.forName(executePath);
            return aClass;
    }

    /**
     * 获取定时任务触发器cron的唯一key
     *
     * @param sysJobForm
     * @return
     */
    public TriggerKey getTriggerKey(SysJobForm sysJobForm) {
        return TriggerKey.triggerKey(sysJobForm.getJobName(), sysJobForm.getJobGroup());
    }
}
