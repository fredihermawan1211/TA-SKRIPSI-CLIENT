package org.vaadin.model.user;
import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    private Long id;

    private String name;

    private String email;

    private String imageUrl;

    private Boolean emailVerified = false;

    private String tokEmailVerified;

    private String password;

    private String tokResetPassword;

    private AuthProvider provider;

    private String providerId;

    private UserRole role;

    // Setter Getter Field Entiti User
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getTokEmailVerified() {
        return tokEmailVerified;
    }

    public void setTokEmailVerified(String tokEmailVerified) {
        this.tokEmailVerified = tokEmailVerified;
    }

    public String getTokResetPassword() {
        return tokResetPassword;
    }

    public void setTokResetPassword(String tokResetPassword) {
        this.tokResetPassword = tokResetPassword;
    }
    
}
