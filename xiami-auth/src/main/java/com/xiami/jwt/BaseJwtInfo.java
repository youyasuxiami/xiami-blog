package com.xiami.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­21 10:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseJwtInfo {
    private String name;
    private Integer userId;

}
