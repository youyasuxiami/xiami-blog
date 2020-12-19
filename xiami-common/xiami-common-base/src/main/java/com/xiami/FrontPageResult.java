package com.xiami;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description：前台分页
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontPageResult {

    private Long total;

    private List<?> data;

    //当前页，从1开始计数
    private Integer current;

    private Integer size;


}
