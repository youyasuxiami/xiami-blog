package com.xiami.service;

import com.xiami.base.ResponseResult;
import com.xiami.dto.front.MessageDto;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­12 8:32
 */
public interface MessageService {
    ResponseResult addMessage(MessageDto messageDto);

    ResponseResult getAllMessageList(MessageDto messageDto);
}
