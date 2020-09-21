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
 * @date：2020­09­12 18:24
 */
@Data
public class BlogContentDto {
    private Integer id;

    private Integer appreciation;

    private Integer commentabled;

    private String content;

    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    private Integer published;

    private Integer recommend;

    private Integer shareStatement;

    private String title;

    private Date updateTime;

    private Integer views;

    private Integer collectCount;

    private Integer typeId;

    private Integer userId;

    private List<TTag> tTagList;

    private String userName;

}
