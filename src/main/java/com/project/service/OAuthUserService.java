package com.project.service;

import com.project.dao.UserAccountDao;
import com.project.model.Role;
import com.project.model.UserAccount;
import com.project.oauth.OAuth2UserInfo;
import com.project.oauth.OAuth2UserInfoFactory;
import com.project.util.SocialUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OAuthUserService extends DefaultOAuth2UserService {

    @Autowired
    private UserAccountDao userAccountDao;

    public OAuthUserService() {
        super();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (OAuth2AuthenticationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration()
                .getRegistrationId(), oAuth2User.getAttributes());
        UserAccount user;
        Optional<UserAccount> userOptional = userAccountDao.findByEmail(oAuth2UserInfo.getEmail());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return create(user, oAuth2User.getAttributes());
    }

    private UserAccount registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        UserAccount user = new UserAccount();
        user.setProvider(String.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setRegDate(Instant.now().getEpochSecond());
        user.setEnabled(true);
        return userAccountDao.save(user);
    }

    private UserAccount updateExistingUser(UserAccount existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setEmail(oAuth2UserInfo.getEmail());
        existingUser.setFirstName(oAuth2UserInfo.getFirstName());
        existingUser.setLastName(oAuth2UserInfo.getLastName());
        return userAccountDao.save(existingUser);
    }

    public static UserAccount create(UserAccount user, Map<String, Object> attributes) {
        Role role = new Role(1L, "ROLE_USER");
        user.setAttributes(attributes);
        user.setRoles(role);
        return user;
    }
}