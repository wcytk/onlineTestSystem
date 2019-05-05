package com.wcytk.service;

import com.wcytk.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User SelectUser(String name, String passwd);

    public boolean AddUser(String name, String passwd, int teacher);
}
