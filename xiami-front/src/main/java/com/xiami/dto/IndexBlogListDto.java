package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiami.entity.TTag;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­08­01 0:08
 */
@Data
public class IndexBlogListDto {
    private Integer id;

    private String title;

    private String firstPicture;

    private String description;

    private Integer userId;

    private String userName;

    private Integer views;

    private Integer collectCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<TTag> tTags;
}
