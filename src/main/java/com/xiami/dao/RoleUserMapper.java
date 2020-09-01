package com.xiami.dao;

import com.xiami.entity.RoleUser;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface RoleUserMapper extends MyMapper<RoleUser> {
    int deleteExist(List<Integer> ids);

    /**
     * 获得选中的所有用户   在用户-角色表中的数据
     *
     * @param ids
     * @return
     */
    List<RoleUser> selectByUserIds(Integer[] ids);

    /**
     * 根据userIds删除数据
     *
     * @param ids
     * @return
     */
    int deleteByUserIds(Integer[] ids);
}