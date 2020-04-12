package com.project.oauth;

import java.util.Map;

public class FacebookOauthUserInfo extends OAuth2UserInfo {

    String firstName = getName((String)attributes.get("name"))[0];
    String lastName = getName((String)attributes.get("name"))[1];

    public FacebookOauthUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public String[] getName(String fullNameStr) {
        return fullNameStr.split(" ");
    }

}
