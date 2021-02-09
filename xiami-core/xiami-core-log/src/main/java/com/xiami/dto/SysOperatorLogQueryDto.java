package com.xiami.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­16 22:40
 */
@Data
public class SysOperatorLogQueryDto extends PageRequestDto {

    /**
     * 日志类型
     */
    private String type;

    /**
     * 日志标题
     */
    private String title;


    /**
     * 操作人员
     */
    private String operName;


    /**
     * 主机地址
     */
    private String operIp;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;




}
