package com.xiami.dto;

import lombok.Data;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­03 23:01
 */
@Data
public class TypeQueryDto extends PageRequestDto {
    private String typeName;

    private String beginTime;

    private String endTime;
}
