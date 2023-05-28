package org.vaadin.api;

import org.vaadin.RequestDto.AnggotaRequest;
import org.vaadin.RequestDto.AuthorizationRequest;
import org.vaadin.RequestDto.JadwalRequest;
import org.vaadin.RequestDto.KolamRequest;
import org.vaadin.RequestDto.KomunitasRequest;
import org.vaadin.RequestDto.LoginRequest;
import org.vaadin.RequestDto.SignUpRequest;

public class RequestDtoPOJO {
    
    private KomunitasRequest komunitasRequest;
    private AnggotaRequest anggotaRequest;
    private KolamRequest kolamRequest;
    private JadwalRequest jadwalRequest;
    private AuthorizationRequest authorizationRequest;
    private LoginRequest loginRequest;
    private SignUpRequest signUpRequest;
    public KomunitasRequest getKomunitasRequest() {
        return komunitasRequest;
    }
    public void setKomunitasRequest(KomunitasRequest komunitasRequest) {
        this.komunitasRequest = komunitasRequest;
    }
    public AnggotaRequest getAnggotaRequest() {
        return anggotaRequest;
    }
    public void setAnggotaRequest(AnggotaRequest anggotaRequest) {
        this.anggotaRequest = anggotaRequest;
    }
    public KolamRequest getKolamRequest() {
        return kolamRequest;
    }
    public void setKolamRequest(KolamRequest kolamRequest) {
        this.kolamRequest = kolamRequest;
    }
    public JadwalRequest getJadwalRequest() {
        return jadwalRequest;
    }
    public void setJadwalRequest(JadwalRequest jadwalRequest) {
        this.jadwalRequest = jadwalRequest;
    }
    public AuthorizationRequest getAuthorizationRequest() {
        return authorizationRequest;
    }
    public void setAuthorizationRequest(AuthorizationRequest authorizationRequest) {
        this.authorizationRequest = authorizationRequest;
    }
    public LoginRequest getLoginRequest() {
        return loginRequest;
    }
    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }
    public SignUpRequest getSignUpRequest() {
        return signUpRequest;
    }
    public void setSignUpRequest(SignUpRequest signUpRequest) {
        this.signUpRequest = signUpRequest;
    }

    


}
