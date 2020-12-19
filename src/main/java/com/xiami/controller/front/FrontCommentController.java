package com.xiami.controller.front;

import com.xiami.base.ResponseResult;
import com.xiami.dto.CommentDto;
import com.xiami.service.TCommentService;
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
 * @date：2020­09­06 21:51
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/web")
public class FrontCommentController {

    @Autowired
    private TCommentService tCommentService;

    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody CommentDto commentDto){
        return tCommentService.addComment(commentDto);
    }
}
