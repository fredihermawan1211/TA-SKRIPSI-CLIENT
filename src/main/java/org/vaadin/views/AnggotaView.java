package org.vaadin.views;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.api.RestApiClient;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("anggota")
@PageTitle(value = "Management Anggota")
public class AnggotaView extends HorizontalLayout {

    private String bearerToken;
    private RestApiClient restApiClient;
    
    public AnggotaView(){

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
        
        Label detListAnggotaLabel = new Label("Daftar Anggota");
        VerticalLayout detailAnggLayout = new VerticalLayout();
        detailAnggLayout.setWidth(100, Unit.PERCENTAGE);
        detailAnggLayout.setHeight(100, Unit.PERCENTAGE);

        Komunitas currKomunitas = (Komunitas) VaadinSession.getCurrent().getAttribute("currentKomunitas");
        if (currKomunitas == null) {
            currKomunitas = getCurrentKomuntias();
            VaadinSession.getCurrent().setAttribute("currentKomunitas", currKomunitas);
        }

        Grid<Anggota> gridAnggota = new Grid<Anggota>();
        gridAnggota.setItems(getAnggotaByKomunitas(currKomunitas));
        gridAnggota.addColumn(e -> {
            return e.getUser().getName();
        }).setHeader("Nama Anggota");
        gridAnggota.addColumn(e -> {
            return e.getUser().getRole().equals(UserRole.ROLE_EMPLOYEE) ? "Pegawai" : "Pemilik";
        }).setHeader("Jabatan");
        gridAnggota.addColumn(e -> {
            return e.getUser().getEmail();
        }).setHeader("Email");

        gridAnggota.addComponentColumn(e -> {
            Button btnKonfirm = new Button("Konfirmasi");
            Button btnHapus = new Button("Edit");
            Button btnEdit = new Button("Hapus");

            return new HorizontalLayout(btnKonfirm, btnHapus, btnEdit);
        }).setHeader("Aksi");

        detailAnggLayout.add(detListAnggotaLabel, gridAnggota);
        mainLayout.add(detailAnggLayout);

        return mainLayout;
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
