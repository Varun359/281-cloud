package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Account;
import com.cmpe281.team12.ccrs.model.BusinessUser;
import com.cmpe281.team12.ccrs.model.User;
import com.cmpe281.team12.ccrs.model.payload.CreateAccountRequest;
import com.cmpe281.team12.ccrs.model.payload.CreateBusinessUserRequest;
import com.cmpe281.team12.ccrs.model.payload.SignupRequest;
import com.cmpe281.team12.ccrs.repository.AccountRepository;
import com.cmpe281.team12.ccrs.repository.BusinessUserRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BusinessUserRepository businessUserRepository;

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public List<Account> getAllAccounts() {
        return Lists.newArrayList(accountRepository.findAll());
    }

    @Override
    public Account getAccount(String accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        logger.info("getAccount() -- account: " + account);
        // TODO: extend this method to also fetch details for robots, current invoice, orders, etc.
        return account;
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {
        Account account = new Account(request);
        String accountId = account.getAccountId();
        accountRepository.save(account);
        logger.info("AccountServiceImpl::createAccount() -- created new account record in the db: " + account);
        return getAccount(accountId);
    }

    @Override
    public BusinessUser createBusinessUser(CreateBusinessUserRequest request) {

        BusinessUser dbSavedEntity = null;

        try {
            // Create a new record in the 'users' table, along with 'user_role'.
            SignupRequest signupRequest = new SignupRequest(request.getEmail(),
                    request.getEmail(),
                    request.getPassword(),
                    Sets.newHashSet("business"));
            User newUser = authService.registerUser(signupRequest);

            // Create a new business_user record that links to the users record.
            BusinessUser businessUser = new BusinessUser(request);
            businessUser.setUserId(newUser.getId());
            dbSavedEntity = businessUserRepository.save(businessUser);
            logger.info("AccountServiceImpl::createBusinessUser() -- created new business_user record in the db: "
                    + dbSavedEntity);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dbSavedEntity;
    }

    @Override
    public List<BusinessUser> getUsersForAccount(String accountId) {
        List<BusinessUser> businessUsers = businessUserRepository.findByAccountId(accountId);
        logger.info("AccountServiceImpl::getUsersForAccount() -- results: " + businessUsers);
        return businessUsers;
    }

    @Override
    public BusinessUser getBusinessUser(Long userId) {
        BusinessUser businessUser = businessUserRepository.findById(userId).orElse(null);
        logger.info("AccountServiceImpl::getBusinessUser() -- results: " + businessUser);
        return businessUser;
    }
}