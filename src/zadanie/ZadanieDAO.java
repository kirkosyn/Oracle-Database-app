package zadanie;

import connection.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZadanieDAO {
    private  ObservableList<Zadanie> zadania;


    /**
     * Function getting zadania for pracownik from database
     *
     * @return returns zadania from database
     */
    public   ObservableList<Zadanie> GetPracownikZadania(int id) {

        zadania = FXCollections.observableArrayList();
        Zadanie zadanie;
        try {
           // String cmd = "SELECT * FROM zadania";
            String cmd1="SELECT ID_ZADANIA FROM PRACOWNICY_ZADANIA WHERE ID_PRACOWNIKA=+'"+id+"'";


            ResultSet r1  =  DatabaseConnect.ExecuteStatement(cmd1);


            int numberZadanie;
            while (r1.next())
            {

                numberZadanie=r1.getInt(1);

                String cmd = "SELECT * FROM zadania WHERE ID_ZADANIA='"+numberZadanie+"'";
                ResultSet rs = DatabaseConnect.ExecuteStatement(cmd);

                while (rs.next()) {

                    zadanie = new Zadanie();
                    zadanie = SetFieldsOfClass(rs, zadanie);

                    zadania.add(zadanie);
                }
                rs.close();
            }
            r1.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return zadania;
    }

    /**
     * Metoda ustawiająca pola danych antykwariatu na podstawie wyników zapytania
     *
     * @param rs          wynik zapytania
     * @param zadanie obiekt antykwariatu
     * @return antykwariat z wypełnionymi informacjami
     * @throws SQLException
     */
    private  Zadanie SetFieldsOfClass(ResultSet rs, Zadanie zadanie) throws SQLException {
        try {
            zadanie.setIdZadania(rs.getInt(1));
            zadanie.setRodzajZadania(rs.getString(2));
            zadanie.setDataNadania(rs.getString(3));
            zadanie.setDataRozpoczecia(rs.getString(4));
            zadanie.setDataZakonczenia(rs.getString(5));
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }

        return zadanie;
    }


}
