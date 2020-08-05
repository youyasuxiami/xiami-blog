package com.xiami.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_blog_tags")
public class TBlogTags implements Serializable {
    @Column(name = "blogs_id")
    private Integer blogsId;

    @Column(name = "tags_id")
    private Integer tagsId;

    private static final long serialVersionUID = 1L;
}