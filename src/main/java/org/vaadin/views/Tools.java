package org.vaadin.views;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Tools {

    public static VerticalLayout generateMenu() {
        VerticalLayout mainLayout = new VerticalLayout();
        
        mainLayout.setWidth(15, Unit.VW);
        mainLayout.setHeight(100, Unit.VH);
        mainLayout.setJustifyContentMode(JustifyContentMode.START);
        mainLayout.setAlignItems(Alignment.CENTER);
        // mainLayout.getStyle().set("background", "red");
        mainLayout.getStyle().set("box-shadow", "6px 0px 12px -6px");
        
        
        List<Class<? extends Component>> listNavTarget = new ArrayList<Class<? extends Component>>();
        listNavTarget.add(KomunitasView.class);
        listNavTarget.add(AnggotaView.class);
        listNavTarget.add(KolamView.class);
        listNavTarget.add(JadwalView.class);

        for (Class<? extends Component> clasTarget : listNavTarget) {
            Button btnNav = new Button();
            btnNav.setText(
                clasTarget.equals(KomunitasView.class) ? "Komunitas" : 
                clasTarget.equals(AnggotaView.class) ? "Anggota" :
                clasTarget.equals(KolamView.class) ? "Kolam" :
                clasTarget.equals(JadwalView.class) ? "Jadwal" :
                "Empty"
                );
            btnNav.addClickListener(e -> {
                UI.getCurrent().navigate(clasTarget);
            });

            // Styling
            btnNav.getStyle().set("width", "60%");
            btnNav.getStyle().set("height", "70px");
            btnNav.getStyle().set("background", "transparent");
            btnNav.getStyle().set("border", "solid black");
            btnNav.getStyle().set("color", "black");
            btnNav.getStyle().set("border-radius", "10px");
            
            mainLayout.add(btnNav);
        }

        return mainLayout;
    }

    public static void showNotification(String string) {
        // When creating a notification using the constructor,
        // the duration is 0-sec by default which means that
        // the notification does not close automatically.
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(string));

        Button closeButton = new Button("X");
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
        notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
    
}
