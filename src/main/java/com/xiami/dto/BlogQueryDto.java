package com.xiami.dto;

import lombok.Data;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­01 23:57
 */
@Data
public class BlogQueryDto extends  PageRequestDto {

    private String userName;
    private Integer typeId;
    private String title;
    private Integer flag;
    private Integer publish;
    private String beginTime;
    private String endTime;
}
