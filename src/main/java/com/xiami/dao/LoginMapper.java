package com.xiami.dao;

import java.util.List;

public interface LoginMapper  {
    List<String> getRoleNames(String username);
}