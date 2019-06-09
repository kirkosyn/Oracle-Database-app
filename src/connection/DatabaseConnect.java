package connection;

import javafx.scene.control.Alert;

import java.sql.*;

public class DatabaseConnect {


    private static Connection connection;

    /**
     * Konstruktor
     */
    public DatabaseConnect() {

    }

    /**
     * Metoda ustanawiająca połączenie z bazą
     *
     * @throws SQLException
     */
    public static void ConnectToDatabase() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "KPAWLUK";
        String pwd = "wbd2019";

        try {
            connection = DriverManager.getConnection(url, user, pwd);
            connection.setAutoCommit(false);

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.toString());
            alert.show();
        }

    }

    /**
     * Metoda rozłączająca połączenie z bazą
     *
     * @throws SQLException
     */
    public static void DisconnectConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Metoda wykonująca zapytanie do bazy
     *
     * @param cmd zapytanie
     * @return zwraca wynik zapytania
     * @throws SQLException
     */
    public static ResultSet ExecuteStatement(String cmd) throws SQLException {
        ResultSet rs = null;
        Statement state = null;

        try {

            state = connection.createStatement();
            rs = state.executeQuery(cmd);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            //   if (rs != null) {
            //rs.close();
            //   }
            //if (state != null) {
            //state.close();
            //   }
            //DisconnectConnection();
        }
        return rs;
    }

    /**
     * Metoda wykonująca zapytanie do bazy
     *
     * @param cmd zapytanie
     * @throws SQLException
     */
    public static void ExecuteUpdateStatement(String cmd) throws SQLException {
        Statement state = null;
        try {
            state = connection.createStatement();

            state.executeUpdate(cmd);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            //if (state != null) {
            //state.close();
        }
        //DisconnectConnection();



    }
    public static void ExecuteRollback() throws SQLException
    {
        Statement state = null;
        try {
            state = connection.createStatement();

            state.executeUpdate("ROLLBACK");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            //if (state != null) {
            //state.close();
        }
    }

    public static void ExecuteCommit() throws SQLException
    {
        Statement state = null;
        try {
            state = connection.createStatement();

            state.executeUpdate("COMMIT");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            //if (state != null) {
            //state.close();
        }
    }
    /*
    public static void ExecutePreparedStatement(String cmd, PreparedStatement pst) throws SQLException {
        PreparedStatement state = pst;
        try {
            state = connection.prepareStatement(cmd);
            //state.clearParameters();
            state.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }*/
}
