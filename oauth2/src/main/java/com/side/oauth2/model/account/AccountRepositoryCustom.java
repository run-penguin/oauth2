package com.side.oauth2.model.account;

public interface AccountRepositoryCustom {
    Account findByUsername(String username);
}
