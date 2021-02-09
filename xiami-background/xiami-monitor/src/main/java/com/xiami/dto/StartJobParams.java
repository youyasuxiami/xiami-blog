package com.xiami.dto;

import lombok.Data;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­22 8:22
 */
@Data
public class StartJobParams {

        private String jobName;

        private String jobGroup;

        private String executePath;

        private String cronExpression;

}
