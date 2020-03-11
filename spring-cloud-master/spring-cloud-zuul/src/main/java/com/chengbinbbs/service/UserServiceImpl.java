package com.chengbinbbs.service;

import com.chengbinbbs.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean insert(UserEntity userEntity) {
        return false;
    }

    @Override
    public UserEntity getByUsername(String username) {
        return null;
    }
}
