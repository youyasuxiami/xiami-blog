package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­07­06 12:55
 */
@Data
public class RoleParam implements Serializable {
    private static final long serialVersionUID = 8710058978001646489L;

    private String roleName;
    private String roleDesc;
    private String[] menusSelect;
}
