package com.xiami.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­26 16:52
 */
@Getter
@AllArgsConstructor
public enum QuartzStatus {
    /**
     * JOB状态：1已发布
     */
    JOB_STATUS_RELEASE("1", "未发布"),
    /**
     * JOB状态：2运行中
     */
    JOB_STATUS_RUNNING("2", "正在运行"),
    /**
     * JOB状态：3暂停
     */
    JOB_STATUS_NOT_RUNNING("3", "暂停"),
    /**
     * JOB状态：4删除
     */
    JOB_STATUS_DEL("4", "删除");
    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String description;
    }
