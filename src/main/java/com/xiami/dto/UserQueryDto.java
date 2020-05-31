package com.xiami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­31 13:28
 */
@Data
public class UserQueryDto extends  PageRequestDto {

    private String nickName;

    private String name;

    private String roleId;

    private String sex;

    private String accountStatus;

    //private Date createTime;
    //因为连续的日期是字符串
    private String[] createTime;
}
