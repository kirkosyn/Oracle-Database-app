package klient;

import com.sun.istack.internal.NotNull;
import connection.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import oracle.sql.DATE;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class KlientDAO {


    public ObservableList<Klient> GetAllKlienci() {
        ObservableList<Klient> klienci = FXCollections.observableArrayList();
        Klient klient = new Klient();
        try {
            ResultSet rs = DatabaseConnect.ExecuteStatement("SELECT * FROM KLIENCI");
            while (rs.next()) {
                klient = new Klient();
                klient = SetFieldsOfClass(rs, klient);

                klienci.add(klient);
            }

            rs.close();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }

        return klienci;
    }

    private Klient SetFieldsOfClass(ResultSet rs, Klient klient) throws SQLException {
        try {
            //  System.out.print(rs.getString(2));
            klient.setId_klienta(rs.getInt(1));
            klient.setImie(rs.getString(2));
            klient.setNazwisko(rs.getString(3));
            klient.setNr_telefonu(rs.getString(4));
            klient.setEmail(rs.getString(5));

            //   klient.setCzy_zarejestrowany(rs.getString(6));
/*Alert a =new Alert(Alert.AlertType.INFORMATION);
a.setTitle(klient.getCzy_zarejestrowany());
a.show();*/
            if (rs.getString(6).equals("t"))
                klient.setCzy_zarejestrowany(new String("tak"));
            else
                klient.setCzy_zarejestrowany("nie");

            try {
                klient.setData_rejestracji(rs.getString(7));

                Date date = rs.getDate(7);
                klient.setRegistrationData(date.toLocalDate());

            } catch (Exception ex) {

            }
            klient.setId_adresu(rs.getInt(8));

        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }

        return klient;
    }

    public void DeleteClient(int id) {
        String cmd = "DELETE FROM KLIENCI WHERE ID_KLIENTA=" + id;
        try {
            DatabaseConnect.ExecuteUpdateStatement(cmd);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
       /* try {
            cmd = "COMMIT";
            DatabaseConnect.ExecuteUpdateStatement(cmd);
            InformationAlert("USUNIETO KLIENTA");

        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }*/
    }

    public void UpdateKlient(Integer id, String imie, String nazwisko,
                             String telefon, String email, String czyZarejestrowany, Date dataRejestracji, int idAdresu) {

        // String cmd="INSERT INTO klienci VALUES('7','Andrzej','Marek','799222111',null,'t','2000-03-11','3')";
        //System.out.println(idAdresu);
        String cmd
                = "UPDATE KLIENCI SET " +
                "IMIE ='" + imie + "'," +
                "NAZWISKO = '" + nazwisko + "'," +
                "NR_TELEFONU = '" + telefon + "'," +
                "EMAIL='" + email + "'," +
                "CZY_ZAREJESTROWANY ='" + czyZarejestrowany + "', " +
                "DATA_REJESTRACJI = DATE '" + dataRejestracji + " '," +
                "ID_ADRESU ='" + idAdresu + "'" + "WHERE ID_KLIENTA = '" + id + " '";

        if (dataRejestracji == null) {
            cmd
                    = "UPDATE KLIENCI SET " +
                    "IMIE ='" + imie + "'," +
                    "NAZWISKO = '" + nazwisko + "'," +
                    "NR_TELEFONU = '" + telefon + "'," +
                    "EMAIL='" + email + "'," +
                    "CZY_ZAREJESTROWANY ='" + czyZarejestrowany + "', " +
                    "DATA_REJESTRACJI =" + null + " ," +
                    "ID_ADRESU ='" + idAdresu + "'" + "WHERE ID_KLIENTA = '" + id + " '";
        }

// "', DATE '"
        //return cmd;
        try {
            DatabaseConnect.ExecuteUpdateStatement(cmd);


        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        try {
            cmd = "COMMIT";
            DatabaseConnect.ExecuteUpdateStatement(cmd);
            InformationAlert("Zaktualizowano dane klienta.");

        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
    }


    public void InsertKlient(Integer id, String imie, String nazwisko,
                             String telefon, String email, String czyZarejestrowany, Date dataRejestracji, int idAdresu) {

        // String cmd="INSERT INTO klienci VALUES('7','Andrzej','Marek','799222111',null,'t','2000-03-11','3')";
//System.out.println(idAdresu);
        String cmd
                = "INSERT INTO KLIENCI VALUES ( '"
                //"(?, ?, ?, DATE ?, ?, ?, ?, 1, 1)";
                + id + "', '" + imie + "', '" + nazwisko + "', '" + telefon + "', '" + email + "', '" + czyZarejestrowany +

                "', DATE '" + dataRejestracji + "','" + idAdresu + "')";
        if (dataRejestracji == null) {
            cmd
                    = "INSERT INTO KLIENCI VALUES ( '"
                    //"(?, ?, ?, DATE ?, ?, ?, ?, 1, 1)";
                    + id + "', '" + imie + "', '" + nazwisko + "', '" + telefon + "', '" + email + "', '" + czyZarejestrowany +

                    "', " + null + ",'" + idAdresu + "')";
        }
// "', DATE '"
        //return cmd;
        try {
            DatabaseConnect.ExecuteUpdateStatement(cmd);


        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        try {
            cmd = "COMMIT";
            DatabaseConnect.ExecuteUpdateStatement(cmd);
            InformationAlert("Klient został dodany.");

        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
    }


    public int MaxIdEntry() {
        String cmd = "SELECT MAX(ID_KLIENTA) FROM KLIENCI";
        int id = 0;
        ResultSet rs = null;

        try {
            rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }

        return id;
    }


    /**
     * Metoda wyszukująca pracownika na podstawie jego nazwiska
     *
     * @param nazwisko nazwisko - kryterium wyszukiwania
     * @return zwraca danego klienta
     */
    public ObservableList<Klient> SearchKlient(String nazwisko) {
        if (nazwisko.isEmpty())
            return GetAllKlienci();

        nazwisko = nazwisko.toLowerCase();
        nazwisko = nazwisko.substring(0, 1).toUpperCase() + nazwisko.substring(1);
        ObservableList<Klient> klienci = null;

        String cmd = "SELECT * FROM KLIENCI WHERE NAZWISKO LIKE \'" + nazwisko + "\'";
        try {

            ResultSet rs = DatabaseConnect.ExecuteStatement(cmd);
            klienci = getKlientFromDatabase(rs);

            rs.close();
            return klienci;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return klienci;
    }

    /**
     * Metoda zwracająca listę pracowników na podstawie wyników zapytania do bazy
     *
     * @param rs wynik zapytania
     * @return lista pracowników
     * @throws SQLException
     */
    private ObservableList<Klient> getKlientFromDatabase(@NotNull ResultSet rs) throws SQLException {
        Klient klient;
        ObservableList<Klient> pracownicy = FXCollections.observableArrayList();

        while (rs.next()) {
            klient = new Klient();
            klient = SetFieldsOfClass(rs, klient);

            pracownicy.add(klient);
        }
        return pracownicy;
    }


    private void InformationAlert(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(information);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.show();
    }
}
