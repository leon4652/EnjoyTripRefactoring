package com.sas.refactoring.user.service;

import com.sas.refactoring.user.dto.UserDto;
import com.sas.refactoring.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDto checkLogin(List<String> data) throws Exception {
        return userMapper.checkLogin(data);
    }

    @Override
    public void registerUser(List<String> data) throws Exception {
        userMapper.registerUser(data);
    }

    @Override
    public boolean checkDuplicate(List<String> data) throws Exception {
        return userMapper.checkDuplicate(data);
    }

    @Override
    public boolean checkFirstAuth(List<String> data) throws Exception {
        return userMapper.checkFirstAuth(data);
    }

    @Override
    public void changeAuth(String id) throws Exception {
        userMapper.changeAuth(id);
    }
}
