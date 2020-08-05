package com.xiami.dto;

import lombok.Data;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­07­30 21:06
 */
@Data
public class BlogDto {
    private String flag;
    private String title;
    private String content;
    private Integer typeId;
    private Integer[] tagIds;
    private String firstPicture;
    private String description;
    private Boolean recommend;
    private Boolean shareStatement;
    private Boolean appreciation;
    private Boolean commentabled;
}
