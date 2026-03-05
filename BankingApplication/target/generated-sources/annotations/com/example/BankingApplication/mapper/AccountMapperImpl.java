package com.example.BankingApplication.mapper;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.model.AccountModel;
import com.example.BankingApplication.model.PassbookAccountModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T19:30:16-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountModel accountToAccountModel(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountModel accountModel = new AccountModel();

        accountModel.setAccountNumber( account.getAccountNumber() );
        accountModel.setAccountBranch( account.getAccountBranch() );
        accountModel.setAccountBalance( account.getAccountBalance() );
        accountModel.setAccountType( account.getAccountType() );

        return accountModel;
    }

    @Override
    public Account accountModelToAccount(AccountModel accountModel) {
        if ( accountModel == null ) {
            return null;
        }

        Account account = new Account();

        account.setAccountBalance( accountModel.getAccountBalance() );
        account.setAccountBranch( accountModel.getAccountBranch() );
        account.setAccountType( accountModel.getAccountType() );

        return account;
    }

    @Override
    public PassbookAccountModel accountToPassbookAccountModel(Account account) {
        if ( account == null ) {
            return null;
        }

        PassbookAccountModel passbookAccountModel = new PassbookAccountModel();

        passbookAccountModel.setAccountNumber( account.getAccountNumber() );
        passbookAccountModel.setAccountType( account.getAccountType() );

        return passbookAccountModel;
    }

    @Override
    public List<AccountModel> accountListToAccountModelList(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountModel> list = new ArrayList<AccountModel>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( accountToAccountModel( account ) );
        }

        return list;
    }

    @Override
    public List<Account> accountModelListToAccountList(List<AccountModel> accountModels) {
        if ( accountModels == null ) {
            return null;
        }

        List<Account> list = new ArrayList<Account>( accountModels.size() );
        for ( AccountModel accountModel : accountModels ) {
            list.add( accountModelToAccount( accountModel ) );
        }

        return list;
    }
}
