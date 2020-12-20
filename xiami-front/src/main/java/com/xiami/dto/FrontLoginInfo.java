package com.xiami.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description：登录信息
 *
 * @version v1.0.0
 * @author：zj
 * @date：2019­12­18 22:27
 */
@Data
public class FrontLoginInfo implements Serializable {
    private Integer userId;

    //用户名
    private String name;
    //头像
    private String avatar;

    //支付宝赞赏码
    private String aliPay;

    //微信赞赏码
    private String weixinPay;

}
