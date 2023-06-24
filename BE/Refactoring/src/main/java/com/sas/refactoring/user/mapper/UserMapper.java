package com.sas.refactoring.user.mapper;

import com.sas.refactoring.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDto checkLogin(List<String> data) throws Exception;

    void registerUser(List<String> data) throws Exception;

    boolean checkDuplicate(List<String> data) throws Exception;

    boolean checkFirstAuth(List<String> data) throws Exception;

    void changeAuth(String id) throws Exception;
}
