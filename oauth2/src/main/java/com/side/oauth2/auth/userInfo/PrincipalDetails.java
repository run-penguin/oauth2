package com.side.oauth2.auth.userInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.side.oauth2.model.account.Account;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PrincipalDetails implements UserDetails, OAuth2User {
    
    private Account account;
    private OAuth2UserInfo oAuth2UserInfo;
    private Boolean newAccount;

    // OAuth2 로그인
    public PrincipalDetails(Account account, OAuth2UserInfo oAuth2UserInfo) {
        this.account = account;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 목록 리턴
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                // return account.getRole().toString();
                return "ROLE_USER";
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        // PK값 반환
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부
        return true;  // 계정 만료 안됨
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김 여부
        return true;  // 계정 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 계정 비밀번호 만료 여부
        return true;  // 만료 안됨
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부
        return true;  // 활성화됨
    }


    @Override
    public String getName() {
        return oAuth2UserInfo.getProviderId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfo.getAttributes();
    }
}
