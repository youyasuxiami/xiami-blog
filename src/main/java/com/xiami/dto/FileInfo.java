package com.xiami.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author：郑锦
 * Date：2020­05­26 23:10
 * Description：<描述>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {
    /**
     * 文件路径
     */
    private String path;
}