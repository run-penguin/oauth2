package com.side.oauth2.model.account;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {


    private final JPAQueryFactory jqf;
    QAccount account = QAccount.account;


    @Override
    public Account findByUsername(String username) {
        return jqf
            .selectFrom(account)
            .where(account.username.eq(username))
            .fetchOne();
    }
    
}
