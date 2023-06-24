package com.sas.refactoring.user.service;

import com.sas.refactoring.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto checkLogin(List<String> data) throws Exception;

    void registerUser(List<String> data) throws Exception;

    boolean checkDuplicate(List<String> data) throws Exception;

    boolean checkFirstAuth(List<String> data) throws Exception;

    void changeAuth(String id) throws Exception;
}
