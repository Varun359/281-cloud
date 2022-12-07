package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
