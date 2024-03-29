package com.mutualCircle.security;


import com.mutualCircle.service.UsersService;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;



@Configuration
public class AuthorizationTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UsersService usersService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        com.mutualCircle.model.Users users = usersService.findByEmail(user.getUsername());
                
        final Map<String, Object> additionalInfo = new HashMap<String, Object>();
        additionalInfo.put("user", users);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}
