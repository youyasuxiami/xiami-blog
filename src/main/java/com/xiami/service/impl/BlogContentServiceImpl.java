package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TBlogContentMapper;
import com.xiami.dao.TCommentMapper;
import com.xiami.dao.TCommentReportMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.BlogContentDto;
import com.xiami.dto.CommentDeteleDto;
import com.xiami.dto.CommentDto;
import com.xiami.dto.CommentListDto;
import com.xiami.dto.CommentReportDto;
import com.xiami.entity.TBlog;
import com.xiami.entity.TComment;
import com.xiami.entity.TCommentReport;
import com.xiami.entity.TTag;
import com.xiami.entity.User;
import com.xiami.enums.ECommentStatus;
import com.xiami.service.BlogContentService;
import com.xiami.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­12 17:31
 */
@Transactional
@Service
public class BlogContentServiceImpl implements BlogContentService {

    @Autowired
    private TBlogContentMapper tBlogContentMapper;

    @Autowired
    private TCommentMapper tCommentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TCommentReportMapper tCommentReportMapper;

    @Override
    public ResponseResult getBlogById(Integer id) {
        BlogContentDto blogContentDtos = tBlogContentMapper.selectById(id);
        String content = blogContentDtos.getContent();
        String newContent = MarkdownUtils.markdownToHtmlExtensions(content);
        String replace = newContent.replace(" class=\"language-language\"", "");
        blogContentDtos.setContent(replace);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取详情成功", blogContentDtos);
    }

    @Override
    public ResponseResult praiseBlogById(Integer id) {
        //TComment tComment=new TComment();
        //tComment.setBlogId(id);
        //TComment tComment1 = tCommentMapper.selectOne(tComment);
        //if(tComment1!=null){
        //
        //}
        TBlog tBlog = tBlogContentMapper.selectByPrimaryKey(id);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取点赞数", tBlog.getCollectCount());
    }

    @Override
    public ResponseResult getSameBlogByBlogId(Integer id) {
        BlogContentDto blogContentDtos = tBlogContentMapper.selectById(id);
        List<Integer> tagIds = blogContentDtos.getTTagList().stream()
                .map(TTag::getId)
                .collect(Collectors.toList());
        List<TBlog> blogsByTagIds = tBlogContentMapper.getBlogsByTagIds(tagIds);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取相关文章数据成功", blogsByTagIds);
    }

    @Override
    public ResponseResult addComment(CommentDto commentDto) {
        TComment tComment = new TComment();
        BeanUtils.copyProperties(commentDto, tComment);
        tComment.setCreateTime(new Date());
        tComment.setUpdateTime(new Date());
        tComment.setType("0");//0表示评论类型
        tComment.setStatus("1");//1表示
        tComment.setLikes(0);//评论点赞数

        Integer toCommentId = commentDto.getToCommentId();
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
        Integer toUserId = commentDto.getToUserId();
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
    public ResponseResult getCommentList(CommentDto commentDto) {
        PageHelper.startPage(commentDto.getCurrentPage(), commentDto.getPageSize());
        //获取所有的一级评论
        List<TComment> tComments = tCommentMapper.getCommentList(commentDto.getBlogId());

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

    @Override
    public ResponseResult reportComment(CommentReportDto commentReportDto) {
        TCommentReport tCommentReport = new TCommentReport();
        tCommentReport.setProgressStatus(0);
        tCommentReport.setReportUserId(commentReportDto.getUserId());//举报人id
        tCommentReport.setReportCommentId(commentReportDto.getCommentId());//被举报的评论id
        //根据被举报的评论id，获取评论的信息
        TComment tComment = tCommentMapper.selectByPrimaryKey(commentReportDto.getCommentId());
        //设置信息
        tCommentReport.setReportToUserId(tComment.getUserId());//评论人/被举报人

        try {
            int insert = tCommentReportMapper.insert(tCommentReport);
            if (insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "举报成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "举报失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "举报失败");
        }
    }

    @Override
    public ResponseResult deleteComment(CommentDeteleDto commentDeteleDto) {
        Integer commentId = commentDeteleDto.getCommentId();
        TComment tComment = tCommentMapper.selectByPrimaryKey(commentId);
        Integer parentCommentId = tComment.getParentCommentId();
        if (parentCommentId != null) {
            TComment tComment1 = new TComment();
            tComment1.setId(commentDeteleDto.getCommentId());
            tComment1.setStatus(ECommentStatus.DISABLED);//设置为删除状态
            int i = tCommentMapper.updateByPrimaryKeySelective(tComment1);
            if (i > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "删除成功", tComment);
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除失败");
        } else {//根评论
            Example example = new Example(TComment.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.orEqualTo("id", commentDeteleDto.getCommentId());
            criteria.orEqualTo("firstCommentId", commentDeteleDto.getCommentId());
            List<Integer> collect = tCommentMapper.selectByExample(example).stream()
                    .map(TComment::getId)
                    .collect(Collectors.toList());
            int i = tCommentMapper.updateByTCommentIds(collect);
            if (i > 0) {
                //查根评论的信息
                TComment tComment1 = tCommentMapper.selectByPrimaryKey(commentDeteleDto.getCommentId());
                return new ResponseResult(ResponseResult.CodeStatus.OK, "删除成功", tComment1);
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "删除失败");
        }
    }
}
