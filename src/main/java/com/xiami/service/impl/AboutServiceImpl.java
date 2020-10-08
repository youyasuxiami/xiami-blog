package com.xiami.service.impl;

import com.xiami.base.ResponseResult;
import com.xiami.dao.UserMapper;
import com.xiami.entity.User;
import com.xiami.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­09­05 17:21
 */
@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult getMe(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return new ResponseResult(ResponseResult.CodeStatus.OK,"获取数据成功",user);
    }
}
