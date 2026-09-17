package com.dtorrez.main.common.providers;

import com.dtorrez.main.security.controllers.dto.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public Long getUserId() {
        return getUserContext().getUserId();
    }

    public String getUsername() {
        return getUserContext().getUserName();
    }

    public String getUserRol() {
        return getUserContext().getRolName();
    }

    public Long getUserTenantId() {
        return getUserContext().getTenantId();
    }


    public UserContext getUserContext() {
        UserContext userContext = null;
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated()) {

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserContext) {
                userContext = (UserContext) principal;

            } else {
                userContext = UserContext.createWithName(principal.toString());
            }
        }

        return userContext;
    }
}
