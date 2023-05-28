package org.vaadin.views;

import org.json.JSONObject;
import org.vaadin.RequestDto.LoginRequest;
import org.vaadin.api.RequestDtoPOJO;
import org.vaadin.api.RestApiClient;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "login")
@PageTitle(value = "Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private RestApiClient restApiClient;
    private String bearerToken;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        bearerToken = (String) VaadinSession.getCurrent().getAttribute("bearerToken");
        if (bearerToken != null) {
            event.forwardTo(KomunitasView.class);
        }        
    }

    public LoginView() {
        TextField emailField = new TextField("Email");
        emailField.setWidth(20, Unit.PERCENTAGE);
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidth(20, Unit.PERCENTAGE);

        Button loginBtn = new Button("LOGIN");
        loginBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginBtn.setWidth(10, Unit.PERCENTAGE);
        loginBtn.setHeight(5, Unit.PERCENTAGE);
        loginBtn.addClickListener(e -> {
            actionLogin(emailField.getValue(), passwordField.getValue());
        });

        add(emailField, passwordField, loginBtn);
        
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

    }

    private void actionLogin(String emailUser, String passwordUser) {

        this.restApiClient = new RestApiClient();
        RequestDtoPOJO requestDtoPOJO = new RequestDtoPOJO();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(emailUser);
        loginRequest.setPassword(passwordUser);
        requestDtoPOJO.setLoginRequest(loginRequest);
        try {

            String response = restApiClient.sendPostRequest(Parameters.SERVER_URL+"/authentication/login", requestDtoPOJO ,null);
            JSONObject jsonObject = new JSONObject(response);
            
            bearerToken =  jsonObject.getString("accessToken");
            VaadinSession.getCurrent().setAttribute("bearerToken", bearerToken);
            UI.getCurrent().getPage().reload();

        } catch (Exception e) {
            e.printStackTrace();
            Tools.showNotification("Email atau Password Salah");
        }
    }

}
