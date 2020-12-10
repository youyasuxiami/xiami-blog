package com.xiami.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiami.base.PageResult;
import com.xiami.base.ResponseResult;
import com.xiami.dao.TCommentMapper;
import com.xiami.dao.TCommentReportMapper;
import com.xiami.dao.UserMapper;
import com.xiami.dto.CommentDto;
import com.xiami.dto.TCommentListDto;
import com.xiami.dto.TCommentReportListDto;
import com.xiami.dto.TcommentQueryDto;
import com.xiami.dto.TcommentReportQueryDto;
import com.xiami.entity.TComment;
import com.xiami.entity.TCommentReport;
import com.xiami.entity.User;
import com.xiami.service.TCommentService;
import com.xiami.utils.DictionaryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TCommentServiceImpl implements TCommentService {

    @Resource
    private TCommentMapper tCommentMapper;

    @Resource
    private DictionaryUtils dictionaryUtils;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TCommentReportMapper tCommentReportMapper;


    @Override
    public ResponseResult addComment(CommentDto commentDto) {
        TComment tComment = new TComment();
        BeanUtils.copyProperties(commentDto, tComment);
        try {
            int insert = tCommentMapper.insert(tComment);
            if (insert > 0) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "插入评论成功");
            }
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "插入评论失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "插入评论失败");
        }
    }

    @Override
    public ResponseResult getComments(TcommentQueryDto tcommentQueryDto) {
        //回复者名
        String name=tcommentQueryDto.getUserName();

        //被回复者名
        String toUserName=tcommentQueryDto.getToUserName();

        if(!StringUtils.isEmpty(name)){
            Set<Integer> collect = userMapper.getUserByName(name).stream().map(User::getId).collect(Collectors.toSet());
            tcommentQueryDto.setUserId(collect);
        }

        if(!StringUtils.isEmpty(toUserName)){
            Set<Integer> collect = userMapper.getUserByName(toUserName).stream().map(User::getId).collect(Collectors.toSet());
            tcommentQueryDto.setToUserId(collect);
        }

        //第一个参数是当前页
        PageHelper.startPage(tcommentQueryDto.getPageNum(), tcommentQueryDto.getPageSize());
        List<TComment> tComments = tCommentMapper.selectBySearch(tcommentQueryDto);

        List<TCommentListDto> tCommentListDtoList = new ArrayList<>();
        for (TComment tComment : tComments) {
            TCommentListDto tCommentListDto = new TCommentListDto();
            BeanUtils.copyProperties(tComment, tCommentListDto);

            //翻译被回复者的用户名
            Integer toUserId = tComment.getToUserId();
            if(toUserId!=null){
                User user1 = new User();
                user1.setId(toUserId);
                String toUserName1 = userMapper.selectOne(user1).getName();
                tCommentListDto.setToUserName(toUserName1);
            }

            //翻译回复者的用户名
            Integer userId = tComment.getUserId();
            if(userId!=null){
                User user1 = new User();
                user1.setId(userId);
                System.out.println("----------------");
                System.out.println(userId);
                User user = userMapper.selectOne(user1);
                String userName = user.getName();
                tCommentListDto.setUserName(userName);
            }


            //翻译评论审核状态
            String status1 = tComment.getStatus();
            String status = dictionaryUtils.toChinese("comment_status", status1);
            tCommentListDto.setStatus(status);

            //翻译发布还是草稿状态
            if ("0".equals(tComment.getType())) {
                tCommentListDto.setType("评论");
            } else if ("1".equals(tComment.getType())) {
                tCommentListDto.setType("点赞");
            }
            tCommentListDtoList.add(tCommentListDto);
        }

        PageInfo<TComment> info = new PageInfo<>(tComments);
        long total = info.getTotal();
        PageResult pageResult = new PageResult(total, tCommentListDtoList);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得评论列表数据成功", pageResult);
    }

    @Override
    public ResponseResult updateCommentStatus(TComment tComment) {
        int i = tCommentMapper.updateByPrimaryKeySelective(tComment);
        String msg = "";
        if (i > 0) {
            switch (tComment.getStatus()) {
                case "0":
                    msg = "删除成功";
                    break;
                case "1":
                    msg = "激活成功";
                    break;
                case "2":
                    msg = "冻结成功";
                    break;
                case "3":
                    msg = "置顶成功";
                    break;
                default:
                    msg = "成功";
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, msg);
        }
        return new ResponseResult(ResponseResult.CodeStatus.FAIL, "获得评论列表数据失败");
    }

    @Override
    public ResponseResult getCommentReportList(TcommentReportQueryDto tcommentReportQueryDto) {
        //举报者用户名
        String name=tcommentReportQueryDto.getReportUserName();

        //被举报者用户名
        String toUserName=tcommentReportQueryDto.getReportToUserName();

        if(!StringUtils.isEmpty(name)){
            Set<Integer> collect = userMapper.getUserByName(name).stream().map(User::getId).collect(Collectors.toSet());
            tcommentReportQueryDto.setUserId(collect);
        }

        if(!StringUtils.isEmpty(toUserName)){
            Set<Integer> collect = userMapper.getUserByName(toUserName).stream().map(User::getId).collect(Collectors.toSet());
            tcommentReportQueryDto.setToUserId(collect);
        }

        //第一个参数是当前页
        PageHelper.startPage(tcommentReportQueryDto.getPageNum(), tcommentReportQueryDto.getPageSize());
        List<TCommentReportListDto> tCommentReportList = tCommentReportMapper.selectBySearch(tcommentReportQueryDto);

        List<TCommentReportListDto> tCommentReportListDtos = new ArrayList<>();
        for (TCommentReportListDto tCommentReportListDto : tCommentReportList) {

            //翻译举报人用户名
            Integer toUserId = tCommentReportListDto.getReportUserId();
            if(toUserId!=null){
                User user1 = new User();
                user1.setId(toUserId);
                String toUserName1 = userMapper.selectOne(user1).getName();
                tCommentReportListDto.setReportUserName(toUserName1);
            }

            //翻译被举报人用户名
            Integer reportToUserId = tCommentReportListDto.getReportToUserId();
            if(reportToUserId!=null){
                User user1 = new User();
                user1.setId(reportToUserId);
                System.out.println("***********");
                System.out.println(reportToUserId);
                String userName = userMapper.selectOne(user1).getName();
                tCommentReportListDto.setReportToUserName(userName);
            }


            //翻译评论审核状态
            Integer progressStatus1 = tCommentReportListDto.getProgressStatus();
            String progressStatus = dictionaryUtils.toChinese("progress_status", String.valueOf(progressStatus1));
            tCommentReportListDto.setProgressStatusName(progressStatus);
        //
        //    //翻译发布还是草稿状态
        //    if ("0".equals(tComment.getType())) {
        //        tCommentListDto.setType("评论");
        //    } else if ("1".equals(tComment.getType())) {
        //        tCommentListDto.setType("点赞");
        //    }
            tCommentReportListDtos.add(tCommentReportListDto);
        }

        //全部的数据
        PageInfo<TCommentReportListDto> info = new PageInfo<>(tCommentReportList);
        long total = info.getTotal();
        //过滤后的数据
        PageResult pageResult = new PageResult(total, tCommentReportListDtos);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获得举报列表成功", pageResult);
    }

    @Override
    public ResponseResult updateProgressStatusName(TCommentReport tCommentReport) {
        int i = tCommentReportMapper.updateByPrimaryKeySelective(tCommentReport);
        String msg="";
        if (i > 0) {
            switch (tCommentReport.getProgressStatus()) {
                case 1:
                    msg = "举报通过";
                    break;
                case 2:
                    msg = "举报不通过";
                    break;
                default:
                    msg = "失败";
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, msg);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "举报异常");
    }
}

