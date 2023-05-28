package org.vaadin.views;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.api.RestApiClient;
import org.vaadin.model.kolam.Kolam;
import org.vaadin.model.komunitas.Komunitas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("kolam")
@PageTitle(value = "Management Kolam")
public class KolamView extends HorizontalLayout{

    private String bearerToken;

    private RestApiClient restApiClient;
    
    public KolamView(){

        bearerToken = (String) VaadinSession.getCurrent().getAttribute("bearerToken");
        if (bearerToken == null) {
            UI.getCurrent().navigate(LoginView.class);
            UI.getCurrent().getPage().reload();
        } else {
            this.restApiClient = new RestApiClient();

            add(Tools.generateMenu(), generateContent());
            getStyle().set("padding", "0");
            getStyle().set("margin", "0");
        }
        
    }

    private VerticalLayout generateContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth(85, Unit.VW);
        mainLayout.setHeight(100, Unit.VH);
        
        Label detListKolamLabel = new Label("Daftar Kolam");
        VerticalLayout detailKolLayout = new VerticalLayout();
        detailKolLayout.setWidth(100, Unit.PERCENTAGE);
        detailKolLayout.setHeight(100, Unit.PERCENTAGE);


        Komunitas currKomunitas = (Komunitas) VaadinSession.getCurrent().getAttribute("currentKomunitas");
        if (currKomunitas == null) {
            currKomunitas = getCurrentKomuntias();
            VaadinSession.getCurrent().setAttribute("currentKomunitas", currKomunitas);
        }

        Grid<Kolam> gridKolam = new Grid<Kolam>();
        gridKolam.setItems(getKolamByKomunitas(currKomunitas));
        gridKolam.addColumn(e -> {
            return e.getName();
        }).setHeader("Nama Kolam").setWidth("80%");

        gridKolam.addComponentColumn(e -> {
            Button btnHapus = new Button("Edit");
            Button btnEdit = new Button("Hapus");

            return new HorizontalLayout(btnHapus, btnEdit);
        }).setHeader("Aksi").setWidth("20%");

        detailKolLayout.add(detListKolamLabel, gridKolam);
        mainLayout.add(detailKolLayout);

        return mainLayout;
    }

    private List<Kolam> getKolamByKomunitas(Komunitas komunitas) {
        List<Kolam> result = new ArrayList<Kolam>();
        this.restApiClient = new RestApiClient();
        
        try {
            String response = restApiClient.sendGetRequest(Parameters.SERVER_URL+"/kolam", bearerToken);
            JSONArray jsonArray = new JSONArray(response);

            // Konversi JSONArray menjadi JSONObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", jsonArray);

            // Mengakses nilai properti dalam JSONObject
            JSONArray data = jsonObject.getJSONArray("data");

            // Looping melalui objek JSON dalam JSONArray
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);

                if (komunitas.getId().equals(obj.getJSONObject("komunitas").getLong("id"))) {
                    Kolam tmpKolam = new Kolam();
                    tmpKolam.setId(obj.getLong("id"));
                    tmpKolam.setName(obj.getString("name"));
                    result.add(tmpKolam);
                }
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    private Komunitas getCurrentKomuntias() {
        Komunitas result = new Komunitas();
        List<Komunitas> listKom = new ArrayList<Komunitas>();
        this.restApiClient = new RestApiClient();
        
        try {
            String response = restApiClient.sendGetRequest(Parameters.SERVER_URL+"/komunitas", bearerToken);
            JSONArray jsonArray = new JSONArray(response);

            // Konversi JSONArray menjadi JSONObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", jsonArray);

            // Mengakses nilai properti dalam JSONObject
            JSONArray data = jsonObject.getJSONArray("data");

            // Looping melalui objek JSON dalam JSONArray
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);

                Komunitas tmpKomunitas = new Komunitas();
                tmpKomunitas.setId(obj.getLong("id"));
                tmpKomunitas.setNama(obj.getString("nama"));
                tmpKomunitas.setAlamat(obj.getString("alamat"));
                listKom.add(tmpKomunitas);
            }

            result = listKom.get(0);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    
}
