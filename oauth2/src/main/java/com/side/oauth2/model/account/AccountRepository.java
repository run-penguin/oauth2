package com.side.oauth2.model.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {
    
}
