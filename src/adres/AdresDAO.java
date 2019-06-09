package adres;

import connection.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresDAO {
    private ObservableList<Adres> adresy;

    /**
     * Metoda pobierająca dane adresów z bazy
     *
     * @return zwraca listę adresów z bazy
     */
    public ObservableList<Adres> GetAllAdresy() {
        adresy = FXCollections.observableArrayList();
        Adres adres;
        try {
            String cmd = "SELECT * FROM ADRESY";
            ResultSet rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                adres = new Adres();
                adres = SetFieldsOfClass(rs, adres);

                adresy.add(adres);
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }

        return adresy;
    }

    /**
     * Metoda ustawiająca pola danych adresu na podstawie wyników zapytania
     *
     * @param rs    wynik zapytania
     * @param adres obiekt adresu
     * @return adres z wypełnionymi informacjami
     * @throws SQLException
     */
    private Adres SetFieldsOfClass(ResultSet rs, Adres adres) throws SQLException {
        try {
            adres.setId_adresu(rs.getInt(1));
            adres.setMiasto(rs.getString(2));
            adres.setUlica(rs.getString(3));
            adres.setNr_budynku(rs.getString(4));
            adres.setNr_lokalu(rs.getInt(5));
            adres.setKod_pocztowy(rs.getString(6));

        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }

        return adres;
    }

    /*public Adres GetEntryAttributes(int id) throws SQLException {
        String cmd = "SELECT * FROM ADRESY WHERE ID_ADRESU = " + id;
        ResultSet rs = null;
        Adres adres = new Adres();
        try {
            rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                adres = SetFieldsOfClass(rs, adres);
            }
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return adres;
    }*/
}
