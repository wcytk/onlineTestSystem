package com.wcytk.dao;

import com.wcytk.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User SelectUser(@Param("name") String name, @Param("passwd") String passwd);

    boolean AddUser(@Param("name") String name, @Param("passwd") String passwd, @Param("teacher") int teacher);
}
