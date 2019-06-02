package pracownik;

import connection.DatabaseConnect;
import com.sun.istack.internal.NotNull;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class PracownikDAO {

    private ObservableList<Pracownik> pracownicy;

    /**
     * Konstruktor
     */
    public PracownikDAO() {
    }

    /**
     * Metoda pobierająca dane pracowników z bazy
     *
     * @return zwraca listę pracowników z bazy
     */
    public ObservableList<Pracownik> GetAllPracownicy() {
        pracownicy = FXCollections.observableArrayList();
        Pracownik pracownik;
        try {
            String cmd = "SELECT * FROM PRACOWNICY";
            ResultSet rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                pracownik = new Pracownik();
                pracownik = SetFieldsOfClass(rs, pracownik);

                //showParameters(pracownik);

                pracownicy.add(pracownik);
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }

        return pracownicy;
    }

    /**
     * Metoda wyszukująca pracownika na podstawie jego nazwiska
     *
     * @param nazwisko nazwisko - kryterium wyszukiwania
     * @return zwraca danego pracownika
     */
    public ObservableList<Pracownik> SearchPracownik(String nazwisko) {
        if (nazwisko.isEmpty())
            return GetAllPracownicy();

        nazwisko = nazwisko.toLowerCase();
        nazwisko = nazwisko.substring(0, 1).toUpperCase() + nazwisko.substring(1);
        ObservableList<Pracownik> pracownicy = null;

        String cmd = "SELECT * FROM PRACOWNICY WHERE NAZWISKO LIKE \'" + nazwisko + "\'";
        try {

            ResultSet rs = DatabaseConnect.ExecuteStatement(cmd);
            pracownicy = getPracownikFromDatabase(rs);

            rs.close();
            return pracownicy;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return pracownicy;
    }

    /**
     * Metoda zwracająca listę pracowników na podstawie wyników zapytania do bazy
     *
     * @param rs wynik zapytania
     * @return lista pracowników
     * @throws SQLException
     */
    private ObservableList<Pracownik> getPracownikFromDatabase(@NotNull ResultSet rs) throws SQLException {
        Pracownik pracownik;
        ObservableList<Pracownik> pracownicy = FXCollections.observableArrayList();

        while (rs.next()) {
            pracownik = new Pracownik();
            pracownik = SetFieldsOfClass(rs, pracownik);

            pracownicy.add(pracownik);
        }
        return pracownicy;
    }

    /**
     * Metoda wprowadzająca dane pracownika do bazy
     *
     * @param id           id
     * @param imie         imie
     * @param nazwisko     nazwisko
     * @param data_urodzin data urodzin
     * @param pesel        pesel
     * @param telefon      numer telefonu
     * @param bank         numer konta bankowego
     * @param id_ant       antykwariat
     * @param id_adr       adres
     */
    public void InsertPracownik(int id, String imie, String nazwisko, Date data_urodzin, String pesel,
                                String telefon, String bank, int id_ant, int id_adr) {
        String cmd = "INSERT INTO PRACOWNICY\n" +
                "VALUES\n" +
                //"(?, ?, ?, DATE ?, ?, ?, ?, 1, 1)";
                "(" + id + ", '" + imie + "', '" + nazwisko + "', DATE '" + data_urodzin + "','" + pesel +
                "','" + bank + "','" + telefon + "', " + id_ant + ", " + id_adr + ")";

        //return cmd;
        try {
            DatabaseConnect.ExecuteUpdateStatement(cmd);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
    }

    /**
     * Metoda aktualizująca dane pracownika
     *
     * @param id       id
     * @param nazwisko nazwisko
     * @param telefon  numer telefonu
     * @param bank     numer konta bankowego
     */
    public void UpdatePracownik(int id, String nazwisko, String telefon, String bank) {
        String cmd = "UPDATE PRACOWNICY\n" +
                "      SET NAZWISKO = '" + nazwisko + "',\n" +
                "    NR_TELEFONU = '" + telefon + "',\n" +
                "   NR_KONTA_BANKOWEGO = '" + bank + "'\n" +
                "    WHERE ID_PRACOWNIKA = " + id;
        /*String cmd = "UPDATE PRACOWNICY\n" +
                "      SET NAZWISKO = ? " +
                "   AND NR_TELEFONU = ? " +
                "   AND NR_KONTA_BANKOWEGO = ? " +
                "    WHERE ID_PRACOWNIKA = ?";
        PreparedStatement preparedStatement = null;
        preparedStatement.setString(1, nazwisko);
        preparedStatement.setString(2, telefon);
        preparedStatement.setString(3, bank);*/

        try {
            DatabaseConnect.ExecuteUpdateStatement(cmd);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
    }

    /**
     * Metoda usuwająca pracownika z bazy
     *
     * @param id id
     * @throws SQLException
     */
    public void DeletePracownik(int id) throws SQLException {
        String cmd = "DELETE FROM PRACOWNICY " +
                "WHERE ID_PRACOWNIKA = " + id;
        try {
            DatabaseConnect.ExecuteUpdateStatement(cmd);
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
    }

    /**
     * Metoda wyszukująca max indeks w tablicy
     *
     * @return max indeks
     */
    public int MaxIdEntry() {
        String cmd = "SELECT MAX(ID_PRACOWNIKA) FROM PRACOWNICY";
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
     * Metoda ustawiająca pola danych pracownika na podstawie wyników zapytania
     *
     * @param rs        wynik zapytania
     * @param pracownik obiekt pracownika
     * @return pracownik z wypełnionymi informacjami
     */
    private Pracownik SetFieldsOfClass(ResultSet rs, Pracownik pracownik) {
        try {
            pracownik.setId_pracownika(rs.getInt(1));
            pracownik.setImie(rs.getString(2));
            pracownik.setNazwisko(rs.getString(3));
            pracownik.setData_urodzenia(rs.getDate(4).toString());
            pracownik.setPesel(rs.getString(5));
            pracownik.setNr_konta_bankowego(rs.getString(6));
            pracownik.setNr_telefonu(rs.getString(7));
            pracownik.setId_antykwariatu(rs.getInt(8));
            pracownik.setId_adresu(rs.getInt(9));
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }

        return pracownik;
    }

    /*public void showParameters(Pracownik pracownik)
    {
        System.out.println(String.valueOf(pracownik.getId_pracownika() + " " + pracownik.getImie() + " " +
                pracownik.getNazwisko() + " " + String.valueOf(pracownik.getData_urodzenia()) + " " +
                pracownik.getPesel() + " " + pracownik.getNr_konta_bankowego() + " " +
                pracownik.getNr_telefonu() + " " + String.valueOf(pracownik.getId_antykwariatu()) + " " +
                String.valueOf(pracownik.getId_adresu())));
    }*/

    /*public Pracownik GetEntryAttributes(int id) {
        String cmd = "SELECT * FROM PRACOWNICY WHERE ID_PRACOWNIKA = " + id;
        ResultSet rs = null;
        Pracownik pracownik = new Pracownik();
        try {
            rs = DatabaseConnect.ExecuteStatement(cmd);
            while (rs.next()) {
                pracownik = SetFieldsOfClass(rs, pracownik);
            }
        } catch (SQLException ex) {
            System.out.print(ex.toString());
        }
        return pracownik;
    }*/
}

