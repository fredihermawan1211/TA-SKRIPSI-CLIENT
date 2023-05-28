package org.vaadin.RequestDto;

import org.vaadin.model.user.UserRole;

public class AuthorizationRequest {
    
    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    

}
