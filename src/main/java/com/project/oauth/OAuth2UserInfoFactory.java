package com.project.oauth;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuthUserInfo(attributes);
        }
        if (registrationId.equalsIgnoreCase("facebook")) {
            return new FacebookOauthUserInfo(attributes);
        }
        return null;
    }
}
