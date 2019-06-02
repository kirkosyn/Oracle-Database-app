package klient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Klient {
    private int id_klienta;
    private String imie;
    private String nazwisko;
    private String nr_telefonu;
    private String email;
    private String czy_zarejestrowany;
    private String data_rejestracji;
    private int id_adresu;

    public Klient(int id_klienta, String imie, String nazwisko, String nr_telefonu, String email, String czy_zarejestrowany, String data_rejestracji, int id_adresu) {
        this.id_klienta = id_klienta;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
        this.email = email;
        this.czy_zarejestrowany = czy_zarejestrowany;
        this.data_rejestracji = data_rejestracji;
        this.id_adresu = id_adresu;
    }

    public Klient(){}

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setData_rejestracji(String data_rejestracji) {
        this.data_rejestracji = data_rejestracji;
    }

    public void setCzy_zarejestrowany(String czy_zarejestrowany) {
        this.czy_zarejestrowany = czy_zarejestrowany;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }

    public void setId_adresu(int id_adresu) {
        this.id_adresu = id_adresu;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getData_rejestracji() {
        return data_rejestracji;
    }

    public String getCzy_zarejestrowany() {
        return czy_zarejestrowany;
    }

    public String getEmail() {
        return email;
    }

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public int getId_adresu() {
        return id_adresu;
    }

    public ObservableList<Klient> GetAllKlienci(Connection conn) {
        ObservableList<Klient> klienci = FXCollections.observableArrayList();
        Klient klient = new Klient();
        try
        {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT * FROM KLIENCI");
            while(rs.next())
            {
                klient.setId_klienta(rs.getInt("Id_Klienta"));
                klient.setImie(rs.getString("Imie"));
                klient.setNazwisko(rs.getString("Nazwisko"));
                klient.setData_rejestracji(rs.getDate("Data_Urodzenia").toString());
                klient.setNr_telefonu(rs.getString("Nr_Telefonu"));
                klient.setId_adresu(rs.getInt("Id_Adresu"));
                klient.setCzy_zarejestrowany(rs.getString("Czy_Zarejestrowany"));
                klient.setEmail(rs.getString("Email"));

                klienci.add(klient);
            }

            rs.close();
            state.close();
        }
        catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }

        return klienci;
    }
}
