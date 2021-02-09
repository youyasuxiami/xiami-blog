package com.xiami.dto;

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

    private Date createTime;

    private List<TTag> tTags;
}
