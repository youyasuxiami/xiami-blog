package com.xiami;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description：分页响应类
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­30 17:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {

    private Long total;

    private List<?> data;
}
