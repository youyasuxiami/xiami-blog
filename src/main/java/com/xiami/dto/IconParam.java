package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author：郑锦
 * Date：2020­05­26 23:54
 * Description：<描述>
 */
@Data
public class IconParam implements Serializable {
    private static final long serialVersionUID = 3995479111219288438L;

    private String Username;

    private String path;
}
