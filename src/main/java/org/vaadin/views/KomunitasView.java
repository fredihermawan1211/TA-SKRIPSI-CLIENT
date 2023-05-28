package org.vaadin.views;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.api.RestApiClient;
import org.vaadin.model.kolam.Kolam;
import org.vaadin.model.komunitas.Anggota;
import org.vaadin.model.komunitas.Komunitas;
import org.vaadin.model.komunitas.StatusAnggota;
import org.vaadin.model.user.User;
import org.vaadin.model.user.UserRole;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("komunitas")
@PageTitle(value = "Management Komunitas")
public class KomunitasView extends HorizontalLayout {

    private String bearerToken;

    private RestApiClient restApiClient;
    private Komunitas currKomunitas;
    
    public KomunitasView(){
        bearerToken = (String) VaadinSession.getCurrent().getAttribute("bearerToken");
        if (bearerToken == null) {
            UI.getCurrent().navigate(LoginView.class);
            UI.getCurrent().getPage().reload();
        } else {
            this.restApiClient = new RestApiClient();
            currKomunitas = (Komunitas) VaadinSession.getCurrent().getAttribute("currentKomunitas");
            if (currKomunitas == null) {
                currKomunitas = getCurrentKomuntias();
                VaadinSession.getCurrent().setAttribute("currentKomunitas", currKomunitas);
            }
            
            add(Tools.generateMenu(), generateContent(currKomunitas));
        }        
        getStyle().set("padding", "0");
        getStyle().set("margin", "0");
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

    private VerticalLayout generateContent(Komunitas komunitas) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth(85, Unit.VW);
        mainLayout.setHeight(100, Unit.VH);
        
        Label detKomLabel = new Label("Detail Komunitas");
        HorizontalLayout detailKomLayout = new HorizontalLayout();
        detailKomLayout.setWidth(100, Unit.PERCENTAGE);

        TextField nameKomField = new TextField();
        nameKomField.setReadOnly(true);
        nameKomField.setValue(komunitas.getNama());
        nameKomField.setWidth(30, Unit.PERCENTAGE);

        TextField alamatKomField = new TextField();
        alamatKomField.setReadOnly(true);
        alamatKomField.setValue(komunitas.getAlamat());
        alamatKomField.setWidth(60, Unit.PERCENTAGE);
        
        Button saveBtn = new Button();
        saveBtn.setText("Simpan");
        saveBtn.setWidth(10, Unit.PERCENTAGE);

        detailKomLayout.add(nameKomField, alamatKomField, saveBtn);

        VerticalLayout pasDetLayout = new VerticalLayout();
        pasDetLayout.setHeight(20, Unit.PERCENTAGE);
        pasDetLayout.add(detKomLabel, detailKomLayout);

        mainLayout.add(pasDetLayout);



        Label detListAnggotaLabel = new Label("Daftar Anggota");
        VerticalLayout detailAnggLayout = new VerticalLayout();
        detailAnggLayout.setWidth(100, Unit.PERCENTAGE);
        detailAnggLayout.setHeight(40, Unit.PERCENTAGE);

        Grid<Anggota> gridAnggota = new Grid<Anggota>();
        gridAnggota.setItems(getAnggotaByKomunitas(komunitas));
        gridAnggota.addColumn(e -> {
            return e.getUser().getName();
        }).setHeader("Nama Anggota");
        gridAnggota.addColumn(e -> {
            return e.getUser().getRole().equals(UserRole.ROLE_EMPLOYEE) ? "Pegawai" : "Pemilik";
        }).setHeader("Jabatan");
        gridAnggota.addColumn(e -> {
            return e.getUser().getEmail();
        }).setHeader("Email");

        detailAnggLayout.add(detListAnggotaLabel, gridAnggota);
        mainLayout.add(detailAnggLayout);



        Label detListKolamLabel = new Label("Daftar Kolam");
        VerticalLayout detailKolLayout = new VerticalLayout();
        detailKolLayout.setWidth(100, Unit.PERCENTAGE);
        detailKolLayout.setHeight(40, Unit.PERCENTAGE);

        Grid<Kolam> gridKolam = new Grid<Kolam>();
        gridKolam.setItems(getKolamByKomunitas(komunitas));
        gridKolam.addColumn(e -> {
            return e.getName();
        }).setHeader("Nama Kolam");

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

    private List<Anggota> getAnggotaByKomunitas(Komunitas komunitas) {
        List<Anggota> result = new ArrayList<Anggota>();
        this.restApiClient = new RestApiClient();
        
        try {
            String response = restApiClient.sendGetRequest(Parameters.SERVER_URL+"/anggota/find/komunitas/"+komunitas.getId(), bearerToken);
            JSONArray jsonArray = new JSONArray(response);

            // Konversi JSONArray menjadi JSONObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", jsonArray);

            // Mengakses nilai properti dalam JSONObject
            JSONArray data = jsonObject.getJSONArray("data");

            // Looping melalui objek JSON dalam JSONArray
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                Anggota tmpAnggota = new Anggota();
                tmpAnggota.setId(obj.getLong("id"));

                String statusAnggota = obj.getString("statusAnggota");
                StatusAnggota rStatusAnggota = 
                statusAnggota.equals(StatusAnggota.DITOLAK.name()) ? StatusAnggota.DITOLAK : 
                statusAnggota.equals(StatusAnggota.PENDING.name()) ? StatusAnggota.PENDING :
                StatusAnggota.RESMI;

                tmpAnggota.setStatusAnggota(rStatusAnggota);
                tmpAnggota.setKomunitas(komunitas);

                User tmpUser = new User();
                tmpUser.setId(obj.getJSONObject("user").getLong("id"));
                String userRole = obj.getJSONObject("user").getString("role");
                UserRole rUserRole = userRole.equals(UserRole.ROLE_EMPLOYEE.name()) ? UserRole.ROLE_EMPLOYEE : UserRole.ROLE_OWNER ;
                tmpUser.setRole(rUserRole);
                tmpUser.setEmail(obj.getJSONObject("user").getString("email"));
                tmpUser.setName(obj.getJSONObject("user").getString("name"));
                tmpAnggota.setUser(tmpUser);
                result.add(tmpAnggota);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    
}
