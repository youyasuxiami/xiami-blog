package com.xiami.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description：分页请求的dto
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­31 11:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {
    private int pageNum;//当前页
    private int pageSize;//每页总数
}
