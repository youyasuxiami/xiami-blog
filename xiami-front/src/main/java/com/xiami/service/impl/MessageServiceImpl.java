package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TCommentMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.CommentListDto;
import com.xiami.dto.MessageDto;
import com.xiami.entity.TComment;
import com.xiami.entity.User;
import com.xiami.enums.ECommentStatus;
import com.xiami.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­12 8:33
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TCommentMapper tCommentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult addMessage(MessageDto messageDto) {
        TComment tComment = new TComment();
        BeanUtils.copyProperties(messageDto, tComment);
        tComment.setCreateTime(new Date());
        tComment.setUpdateTime(new Date());
        tComment.setType("2");//2表示评论类型
        tComment.setStatus("1");//1表示
        tComment.setLikes(0);//评论点赞数

        Integer toCommentId = messageDto.getToCommentId();
        if (toCommentId != null) {//被回复的评论的id

            //定义一个变量，保存firstCommentId
            Integer firstCommentId = null;
            TComment tComment1 = tCommentMapper.selectByPrimaryKey(toCommentId);//第一条被回复的评论的信息

            while (true) {
                Integer parentCommentId = tComment1.getParentCommentId();
                if (parentCommentId == null) {
                    Integer id = tComment1.getId();
                    firstCommentId = id;
                    break;
                }
                tComment1 = tCommentMapper.selectByPrimaryKey(tComment1.getParentCommentId());
            }
            System.out.println("firstCommentId");
            System.out.println(firstCommentId);
            tComment.setFirstCommentId(firstCommentId);
            tComment.setParentCommentId(toCommentId);
        }

        //根据被回复者的id，获取被回复者的头像
        Integer toUserId = messageDto.getToUserId();
        if (toUserId != null) {
            String avatar = userMapper.selectByPrimaryKey(toUserId).getAvatar();
            tComment.setToUserAvatar(avatar);
        }

        try {
            int insert = tCommentMapper.insert(tComment);

            //设置额外信息
            CommentListDto commentListDto = new CommentListDto();
            BeanUtils.copyProperties(tComment, commentListDto);
            //设置用户名
            User user = userMapper.selectByPrimaryKey(tComment.getUserId());
            commentListDto.setUserName(user.getName());
            //设置被@的用户名
            if (null != tComment.getToUserId()) {
                //设置被@的用户名
                commentListDto.setToUserName(userMapper.selectByPrimaryKey(tComment.getToUserId()).getName());
            }

            if (insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "发布评论成功", commentListDto);
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "发布评论失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "发布评论失败");
        }
    }

    @Override
    public ResponseResult getAllMessageList(MessageDto messageDto) {
        PageHelper.startPage(messageDto.getCurrentPage(), messageDto.getPageSize());
        //获取所有的一级评论
        List<TComment> tComments = tCommentMapper.selectAll().stream()
                .filter(tComment -> "2".equals(tComment.getType()) && tComment.getParentCommentId() == null)
                .collect(Collectors.toList());

        //CommentListDto=一级评论+回复的集合
        List<CommentListDto> listDtos = new ArrayList<>();

        for (TComment tComment : tComments) {
            CommentListDto commentListDto = new CommentListDto();
            BeanUtils.copyProperties(tComment, commentListDto);

            //设置用户名
            commentListDto.setUserName(userMapper.selectByPrimaryKey(tComment.getUserId()).getName());
            if (tComment.getToUserId() != null) {
                //设置被@的用户名
                commentListDto.setToUserName(userMapper.selectByPrimaryKey(tComment.getToUserId()).getName());
            }
            //获得所有的子评论
            List<TComment> replyList = tCommentMapper.getReplyList(tComment.getId());
            List<CommentListDto> replyDtoList = new ArrayList<>();

            replyList.forEach(r -> {
                CommentListDto commentListDto1 = new CommentListDto();
                BeanUtils.copyProperties(r, commentListDto1);
                //设置用户名
                commentListDto1.setUserName(userMapper.selectByPrimaryKey(r.getUserId()).getName());
                //设置被@的用户名
                commentListDto1.setToUserName(userMapper.selectByPrimaryKey(r.getToUserId()).getName());
                if (ECommentStatus.ENABLE.equals(commentListDto1.getStatus())) {//激活才添加进来
                    replyDtoList.add(commentListDto1);
                }
            });

            commentListDto.setReplyList(replyDtoList);
            if (ECommentStatus.ENABLE.equals(commentListDto.getStatus())) {//激活才添加进来
                listDtos.add(commentListDto);
            }
        }

        PageInfo<CommentListDto> info = new PageInfo<>(listDtos);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, listDtos);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取评论列表数据成功", pageResult);
    }
}
