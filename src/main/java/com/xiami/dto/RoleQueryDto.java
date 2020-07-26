package com.xiami.dto;

import lombok.Data;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­31 13:28
 */
@Data
public class RoleQueryDto extends  PageRequestDto {

    private String roleName;

    private String roleDesc;
}
