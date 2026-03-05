package com.example.BankingApplication.mapper;

import com.example.BankingApplication.entity.Passbook;
import com.example.BankingApplication.entity.User;
import com.example.BankingApplication.model.PassbookModel;
import com.example.BankingApplication.model.UserPassbookModel;
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
public class PassbookMapperImpl implements PassbookMapper {

    @Override
    public List<PassbookModel> passbookListToPassbookModelList(List<Passbook> passBookList) {
        if ( passBookList == null ) {
            return null;
        }

        List<PassbookModel> list = new ArrayList<PassbookModel>( passBookList.size() );
        for ( Passbook passbook : passBookList ) {
            list.add( passbookToPassbookModel( passbook ) );
        }

        return list;
    }

    @Override
    public UserPassbookModel userToUserPassBookModel(User user) {
        if ( user == null ) {
            return null;
        }

        UserPassbookModel userPassbookModel = new UserPassbookModel();

        userPassbookModel.setEmail( user.getEmail() );
        userPassbookModel.setFirstName( user.getFirstName() );

        return userPassbookModel;
    }

    protected PassbookModel passbookToPassbookModel(Passbook passbook) {
        if ( passbook == null ) {
            return null;
        }

        PassbookModel passbookModel = new PassbookModel();

        passbookModel.setCredit( passbook.getCredit() );
        passbookModel.setDateTime( passbook.getDateTime() );
        passbookModel.setDebit( passbook.getDebit() );
        passbookModel.setTotal( passbook.getTotal() );

        return passbookModel;
    }
}
