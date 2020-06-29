package com.xiami.dao;

import com.xiami.entity.Menu;
import tk.mybatis.mapper.MyMapper;import java.util.List;

public interface MenuMapper extends MyMapper<Menu> {
    List<String> getMenuPerms(Integer userId);
}