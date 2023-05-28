package org.vaadin.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route()
public class MainViews implements BeforeEnterObserver {
    

    @Override
    public void beforeEnter(BeforeEnterEvent arg0) {
        UI.getCurrent().navigate(LoginView.class);
    }

    public MainViews() {
        
    }
    
}
