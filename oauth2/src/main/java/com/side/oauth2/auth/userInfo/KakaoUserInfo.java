package com.side.oauth2.auth.userInfo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {


    private Map<String, Object> attributes;
    private Map<String, Object> attributesAccount;


    @SuppressWarnings("unchecked")
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "Kakao";
    }

    @Override
    public String getEmail() {
        return attributesAccount.get("email").toString();
    }
}
