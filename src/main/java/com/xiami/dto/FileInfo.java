package com.xiami.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­05­25 8:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 7336213529377174709L;

    private String path;
}
