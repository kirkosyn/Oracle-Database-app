package antykwariat;

import connection.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AntykwariatDAO {
    private ObservableList<Antykwariat> antykwariaty;

    /**
     * Metoda pobierająca dane antykwariatów z bazy
     *
     * @return zwraca listę antykwariatów z bazy
     */
    public ObservableList<Antykwariat> GetAllAntykwariaty() {
        antykwariaty = FXCollections.observableArrayList();
        Antykwariat antykwariat;
        try {
            String cmd = "SELECT * FROM ANTYKWARIATY";
            ResultSet rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                antykwariat = new Antykwariat();
                antykwariat = SetFieldsOfClass(rs, antykwariat);

                antykwariaty.add(antykwariat);
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }

        return antykwariaty;
    }

    /**
     * Metoda ustawiająca pola danych antykwariatu na podstawie wyników zapytania
     *
     * @param rs          wynik zapytania
     * @param antykwariat obiekt antykwariatu
     * @return antykwariat z wypełnionymi informacjami
     * @throws SQLException
     */
    private Antykwariat SetFieldsOfClass(ResultSet rs, Antykwariat antykwariat) throws SQLException {
        try {
            antykwariat.setId_antykwariatu(rs.getInt(1));
            antykwariat.setNazwa(rs.getString(2));
            antykwariat.setNr_telefonu(rs.getString(3));
            antykwariat.setStrona_internetowa(rs.getString(4));
            antykwariat.setData_powstania(rs.getDate(5).toString());
            antykwariat.setId_adresu(rs.getInt(6));
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }

        return antykwariat;
    }

    /*public Antykwariat GetEntryAttributes(int id) throws SQLException
    {
        String cmd = "SELECT * FROM ANTYKWARIATY WHERE ID_ANTYKWARIATU = " + id;
        ResultSet rs = null;
        Antykwariat antykwariat = new Antykwariat();
        try {
            rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                antykwariat = SetFieldsOfClass(rs, antykwariat);
            }
        }
        catch (SQLException ex)
        {
            System.out.print(ex.toString());
        }
        return antykwariat;
    }*/
}
