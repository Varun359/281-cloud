package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Account;
import com.cmpe281.team12.ccrs.model.Robot;
import com.cmpe281.team12.ccrs.repository.AccountRepository;
import com.cmpe281.team12.ccrs.repository.RobotRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RobotRepository robotRepository;
    /**
     * Fetches the accounts and corresponding locations that have enabled (rented) robots.
     * Example output:
     * {
     *   "7f016db9-d838-4955-ba1f-1b93eabd770e": "123 Main St."
     * }
     * @return Map of accountId and corresponding address of each active account
     */
    @Override
    public List<Map<String,String>> getActiveAccountLocations() {

        // Get a map of accountId -> Account object
        Map<String, Account> accountMap = accountRepository.findAll()
                .stream()
                .collect(
                        Collectors.toMap(Account::getAccountId, Function.identity()));

        // Get the account IDs from the obtained records
        List<String> accountIds = accountMap.keySet().stream().toList();
        logger.info("CustomerServiceImpl::getActiveAccountLocations() -- active accountIds: " + accountIds);

        // Read the robot records, filter for only the ones that are
        // in common with the active account IDs above, then produce
        // the final map of account ID (key), and address (value) for each.
        Map<String,String> acctIdAddressMap = robotRepository.findByEnabledAccountIds(accountIds).stream()
                .map(Robot::getAccountId)
                .map(accountMap::get)
                .collect(Collectors.toMap(
                        Account::getAccountId,
                        Account::getAddress,
                        (l1,l2) -> l1)
                );
        List<Map<String,String>> results = Lists.newArrayList();
        acctIdAddressMap.forEach((k,v) -> {
            Map<String,String> entry = Maps.newHashMap();
            entry.put("account", k);
            entry.put("address", v);
            results.add(entry);
        });

        logger.info("CustomerServiceImpl::getActiveAccountLocations() -- results: " + results);
        return results;
    }
}