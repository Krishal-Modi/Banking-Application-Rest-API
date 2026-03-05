package com.example.BankingApplication.mapper;

import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.model.UserAccountModel;
import com.example.BankingApplication.model.UserModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T19:30:17-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserModel userToUserModel(User user) {
        if ( user == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setArea( user.getArea() );
        userModel.setDateOfBirth( user.getDateOfBirth() );
        userModel.setEmail( user.getEmail() );
        userModel.setFirstName( user.getFirstName() );
        userModel.setGender( user.getGender() );
        userModel.setLastName( user.getLastName() );
        userModel.setPassword( user.getPassword() );
        userModel.setPhoneNumber( user.getPhoneNumber() );
        userModel.setUserId( user.getUserId() );

        return userModel;
    }

    @Override
    public User userModelToUser(UserModel userModel) {
        if ( userModel == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( userModel.getFirstName() );
        user.setLastName( userModel.getLastName() );
        user.setDateOfBirth( userModel.getDateOfBirth() );
        user.setEmail( userModel.getEmail() );
        user.setPhoneNumber( userModel.getPhoneNumber() );
        user.setPassword( userModel.getPassword() );
        user.setArea( userModel.getArea() );
        user.setGender( userModel.getGender() );
        user.setUserId( userModel.getUserId() );

        return user;
    }

    @Override
    public User updateUserModel(UserModel userModel, User user) {
        if ( userModel == null ) {
            return user;
        }

        user.setArea( userModel.getArea() );
        user.setDateOfBirth( userModel.getDateOfBirth() );
        user.setEmail( userModel.getEmail() );
        user.setFirstName( userModel.getFirstName() );
        user.setGender( userModel.getGender() );
        user.setLastName( userModel.getLastName() );
        user.setPassword( userModel.getPassword() );
        user.setPhoneNumber( userModel.getPhoneNumber() );
        user.setUserId( userModel.getUserId() );

        return user;
    }

    @Override
    public List<UserAccountModel> userListToUserModelList(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserAccountModel> list = new ArrayList<UserAccountModel>( userList.size() );
        for ( User user : userList ) {
            list.add( userToUserAccountModel( user ) );
        }

        return list;
    }

    @Override
    public UserAccountModel userToUserAccountModel(User user) {
        if ( user == null ) {
            return null;
        }

        UserAccountModel userAccountModel = new UserAccountModel();

        if ( user.getDateOfBirth() != null ) {
            userAccountModel.setDateOfBirth( DateTimeFormatter.ISO_LOCAL_DATE.format( user.getDateOfBirth() ) );
        }
        userAccountModel.setEmail( user.getEmail() );
        userAccountModel.setFirstName( user.getFirstName() );
        userAccountModel.setLastName( user.getLastName() );
        userAccountModel.setPhoneNumber( user.getPhoneNumber() );
        userAccountModel.setUserId( user.getUserId() );

        return userAccountModel;
    }
}
