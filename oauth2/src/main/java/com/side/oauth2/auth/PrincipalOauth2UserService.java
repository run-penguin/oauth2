package com.side.oauth2.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.side.oauth2.auth.userInfo.KakaoUserInfo;
import com.side.oauth2.auth.userInfo.OAuth2UserInfo;
import com.side.oauth2.model.account.Account;
import com.side.oauth2.model.account.AccountRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    
    @Autowired private AccountRepository accountRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if (provider.equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        if (oAuth2UserInfo == null) throw new OAuth2AuthenticationException("not found provider id");

        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;  // 사용자가 입력한 적은 없지만 만들어준다.

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드" + uuid);  // 사용자가 입력한 적은 없지만 만들어준다.

        String email = oAuth2UserInfo.getEmail();

        Account account = accountRepository.findByUsername(username);

        // 신규 회원
        if (account == null) {

            account = Account.builder()
                .username(username)
                .password(password)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .build();

            accountRepository.save(account);
        }

        return new PrincipalDetails(account, oAuth2UserInfo);
    }    
}
