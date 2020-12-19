package com.xiami.controller;

import com.xiami.base.ResponseResult;
import com.xiami.dto.MessageDto;
import com.xiami.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­12 17:29
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/message")
public class MessageBoardController {

    @Autowired
    private MessageService messageService;




    //@RequestMapping("/getCommentList")
    //public ResponseResult getCommentList(@RequestBody CommentDto commentDto){
    //    ResponseResult commentList = blogContentService.getCommentList(commentDto);
    //    return commentList;
    //}

    @PostMapping("/addMessage")
    public ResponseResult addMessage(@RequestBody MessageDto messageDto){
        return messageService.addMessage(messageDto);
    }


    /**
     * 获得所有的留言列表
     * @param MessageDto
     * @return
     */
    @PostMapping("/getAllMessageList")
    public ResponseResult getAllMessageList(@RequestBody MessageDto MessageDto){
        ResponseResult commentList = messageService.getAllMessageList(MessageDto);
        return commentList;
    }
}
