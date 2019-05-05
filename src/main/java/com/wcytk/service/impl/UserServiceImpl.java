package com.wcytk.service.impl;

import com.wcytk.dao.UserDao;
import com.wcytk.entity.User;
import com.wcytk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User SelectUser(String name, String passwd) {
        return userDao.SelectUser(name, passwd);
    }

    @Override
    public boolean AddUser(String name, String passwd, int teacher) { return userDao.AddUser(name, passwd, teacher); }
}
