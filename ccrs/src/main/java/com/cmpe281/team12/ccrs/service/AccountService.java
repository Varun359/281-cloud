package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Account;
import com.cmpe281.team12.ccrs.model.BusinessUser;
import com.cmpe281.team12.ccrs.model.payload.CreateAccountRequest;
import com.cmpe281.team12.ccrs.model.payload.CreateBusinessUserRequest;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccount(String accountId);
    Account createAccount(CreateAccountRequest request);
    BusinessUser createBusinessUser(CreateBusinessUserRequest request);
    List<BusinessUser> getUsersForAccount(String accountId);
    BusinessUser getBusinessUser(Long userId);
}