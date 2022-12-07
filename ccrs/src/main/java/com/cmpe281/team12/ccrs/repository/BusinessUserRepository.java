package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.BusinessUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusinessUserRepository extends CrudRepository<BusinessUser, Long> {
    List<BusinessUser> findByAccountId(String accountId);
}