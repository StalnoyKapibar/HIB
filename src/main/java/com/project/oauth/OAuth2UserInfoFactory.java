package com.project.oauth;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google":
                return new GoogleOAuthUserInfo(attributes);
            case "facebook":
                return new FacebookOAuthUserInfo(attributes);
            default:
                return null;
        }
    }
}