package org.vaadin.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.api.RestApiClient;
import org.vaadin.model.jadwal.Jadwal;
import org.vaadin.model.kolam.Kolam;
import org.vaadin.model.komunitas.Anggota;
import org.vaadin.model.komunitas.Komunitas;
import org.vaadin.model.user.User;

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

@Route("jadwal")
@PageTitle(value = "Management Jadwal")
public class JadwalView extends HorizontalLayout{

    private String bearerToken;

    private RestApiClient restApiClient;
    
    public JadwalView(){
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
        
        Label detListJadwalLabel = new Label("Daftar Jadwal");
        VerticalLayout detailKolLayout = new VerticalLayout();
        detailKolLayout.setWidth(100, Unit.PERCENTAGE);
        detailKolLayout.setHeight(100, Unit.PERCENTAGE);


        Komunitas currKomunitas = (Komunitas) VaadinSession.getCurrent().getAttribute("currentKomunitas");
        if (currKomunitas == null) {
            currKomunitas = getCurrentKomuntias();
            VaadinSession.getCurrent().setAttribute("currentKomunitas", currKomunitas);
        }

        Grid<Jadwal> gridJadwal = new Grid<Jadwal>();
        gridJadwal.setItems(getJadwalByKomunitas(currKomunitas));
        gridJadwal.addColumn(e -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
            String result = "";
            try {
                result = dateFormat.format(e.getDateToDo());
            } catch (Exception err) {
                err.printStackTrace();
            }
            return result;
        }).setHeader("Tanggal Pelaksanaan");

        gridJadwal.addColumn(e -> {
            return e.getAnggota().getUser().getName();
        }).setHeader("Penanggung Jawab");

        gridJadwal.addColumn(e -> {
            return e.getKolam().getName();
        }).setHeader("Kolam");

        gridJadwal.addComponentColumn(e -> {
            Button btnHapus = new Button("Edit");
            Button btnEdit = new Button("Hapus");

            return new HorizontalLayout(btnHapus, btnEdit);
        }).setHeader("Aksi");

        detailKolLayout.add(detListJadwalLabel, gridJadwal);
        mainLayout.add(detailKolLayout);

        return mainLayout;
    }

    private List<Jadwal> getJadwalByKomunitas(Komunitas komunitas) {
        List<Jadwal> result = new ArrayList<Jadwal>();
        this.restApiClient = new RestApiClient();
        
        try {
            String response = restApiClient.sendGetRequest(Parameters.SERVER_URL+"/jadwal", bearerToken);
            JSONArray jsonArray = new JSONArray(response);

            // Konversi JSONArray menjadi JSONObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", jsonArray);

            // Mengakses nilai properti dalam JSONObject
            JSONArray data = jsonObject.getJSONArray("data");

            // Looping melalui objek JSON dalam JSONArray
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);

                if (komunitas.getId().equals(obj.getJSONObject("kolam").getJSONObject("komunitas").getLong("id"))) {
                    Jadwal tmpJadwal = new Jadwal();
                    String strDateToDo = obj.getString("dateToDo");
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDateToDo); 
                    tmpJadwal.setDateToDo(date);
                    
                    Anggota tmpAnggota = new Anggota();
                    tmpAnggota.setId(obj.getJSONObject("anggota").getLong("id"));
                    User tmpUser = new User();
                    tmpUser.setId(obj.getJSONObject("anggota").getJSONObject("user").getLong("id"));
                    tmpUser.setName(obj.getJSONObject("anggota").getJSONObject("user").getString("name"));
                    tmpAnggota.setUser(tmpUser);

                    tmpJadwal.setAnggota(tmpAnggota);

                    Kolam tmpKolam = new Kolam();
                    tmpKolam.setId(obj.getJSONObject("kolam").getLong("id"));
                    tmpKolam.setName(obj.getJSONObject("kolam").getString("name"));
                    tmpJadwal.setKolam(tmpKolam);

                    result.add(tmpJadwal);
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
