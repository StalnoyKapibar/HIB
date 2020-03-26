package com.project.oauth;

import java.util.Map;

public class GoogleOAuthUserInfo extends OAuth2UserInfo {

    public GoogleOAuthUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getFirstName() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getLastName() {
        return (String) attributes.get("family_name");
    }

    @Override
    public String getLocale() {
        return (String)attributes.get("locale");
    }

}