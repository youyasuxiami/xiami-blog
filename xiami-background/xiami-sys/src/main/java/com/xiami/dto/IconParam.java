package com.xiami.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author：郑锦
 * Date：2020­05­26 23:54
 * Description：<描述>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IconParam implements Serializable {
    private static final long serialVersionUID = 3995479111219288438L;

    private String Username;

    private String path;
}
