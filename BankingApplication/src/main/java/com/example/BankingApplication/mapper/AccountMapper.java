package com.example.BankingApplication.mapper;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.model.AccountModel;
import com.example.BankingApplication.model.PassbookAccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "accountBranch", source = "accountBranch")
    @Mapping(target = "accountBalance", source = "accountBalance")
    @Mapping(target = "accountType", source = "accountType")
    AccountModel accountToAccountModel(Account account);

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "user", ignore = true)
    Account accountModelToAccount(AccountModel accountModel);

    PassbookAccountModel accountToPassbookAccountModel(Account account);

    List<AccountModel> accountListToAccountModelList(List<Account> accounts);

    List<Account> accountModelListToAccountList(List<AccountModel> accountModels);

}

