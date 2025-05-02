package com.example.BankingApplication.mapper;

import com.example.BankingApplication.entity.Passbook;
import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.model.PassbookModel;
import com.example.BankingApplication.model.UserPassbookModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassbookMapper {

    List<PassbookModel> passbookListToPassbookModelList(List<Passbook> passBookList);


    UserPassbookModel userToUserPassBookModel(User user);
}
