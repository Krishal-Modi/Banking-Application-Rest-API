package com.example.BankingApplication.mapper;


import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.model.UserAccountModel;
import com.example.BankingApplication.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);



    UserModel userToUserModel(User user);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "area", source = "area")
    @Mapping(target = "gender", source = "gender")
    User userModelToUser(UserModel userModel);

    User updateUserModel(UserModel userModel, @MappingTarget User user);

    List<UserAccountModel> userListToUserModelList(List<User> userList);

    @Mapping(target = "account", ignore = true)
    UserAccountModel userToUserAccountModel(User user);

}
