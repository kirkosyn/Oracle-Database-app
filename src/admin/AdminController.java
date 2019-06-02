package admin;

import adres.Adres;
import adres.AdresDAO;
import antykwariat.Antykwariat;
import antykwariat.AntykwariatDAO;
import connection.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pracownik.Pracownik;
import pracownik.PracownikDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class AdminController {
    @FXML
    private TableView<Pracownik> tablePracownicy;

    @FXML
    private TableColumn<Pracownik, Integer> columnIdPracownika;
    @FXML
    private TableColumn<Pracownik, String> columnImiePracownika;
    @FXML
    private TableColumn<Pracownik, String> columnNazwiskoPracownika;
    @FXML
    private TableColumn<Pracownik, String> columnDataUrPracownika;
    @FXML
    private TableColumn<Pracownik, String> columnPeselPracownika;
    @FXML
    private TableColumn<Pracownik, String> columnNrKontaPracownika;
    @FXML
    private TableColumn<Pracownik, String> columnNrTelPracownika;
    @FXML
    private TableColumn<Pracownik, Integer> columnIdAntykwariatu;
    @FXML
    private TableColumn<Pracownik, Integer> columnIdAdresu;

    @FXML
    private TextField textImie;
    @FXML
    private TextField textNazwisko;
    @FXML
    private TextField textPesel;
    @FXML
    private TextField textBank;
    @FXML
    private TextField textTelefon;
    @FXML
    private TextField textSearch;

    @FXML
    private DatePicker textUrodzenie;
    @FXML
    private ComboBox<String> textAntykwariat;
    @FXML
    private ComboBox<String> textAdres;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button commitButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton;

    private String bank;
    private String imie;
    private String nazwisko;
    private String pesel;
    private String telefon;
    private Date data_urodzin;
    private int id_ant;
    private int id_addr;

    private boolean isAddingPracownik;
    private boolean isUpdatingPracownik;

    private ObservableList<Pracownik> pracownicy = FXCollections.observableArrayList();
    private ObservableList<Antykwariat> antykwariaty = FXCollections.observableArrayList();
    private ObservableList<Adres> adresy = FXCollections.observableArrayList();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Konstruktor
     */
    public AdminController() {
    }

    /**
    Metoda inicjalizująca kontroler
     */
    @FXML
    private void initialize() {
        try {
            DatabaseConnect.ConnectToDatabase();


        } catch (SQLException ex) {
            ShowAlert(ex.toString());
        }

        SetTableWithData();
        SetComboBoxes();
    }

    /**
     * Metoda czyszcząca tabelę GUI i wstawiająca dane po wykonanej akcji
     */
    private void RefreshTable() {
        pracownicy.clear();
        SetTableWithData();
    }

    /**
     * Metoda wypełniająca komponenty Combobox
     * - adresami (miasto)
     * - antykwariatami (nazwa)
     */
    private void SetComboBoxes() {
        tablePracownicy.setItems(pracownicy);
        tablePracownicy.getSortOrder().add(columnIdPracownika);

        antykwariaty.forEach(antykwariat -> textAntykwariat.getItems().add(antykwariat.getNazwa()));
        adresy.forEach(adres -> textAdres.getItems().add(adres.getMiasto()));
    }

    /**
     * Metoda uzupełniająca tablicę GUI danymi z bazy
     */
    private void SetTableWithData() {

        pracownicy = new PracownikDAO().GetAllPracownicy();
        antykwariaty = new AntykwariatDAO().GetAllAntykwariaty();
        adresy = new AdresDAO().GetAllAdresy();

        columnIdPracownika.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
        columnImiePracownika.setCellValueFactory(new PropertyValueFactory<>("imie"));
        columnNazwiskoPracownika.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        columnDataUrPracownika.setCellValueFactory(new PropertyValueFactory<>("data_urodzenia"));
        columnPeselPracownika.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        columnNrKontaPracownika.setCellValueFactory(new PropertyValueFactory<>("nr_konta_bankowego"));
        columnNrTelPracownika.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        columnIdAntykwariatu.setCellValueFactory(new PropertyValueFactory<>("id_antykwariatu"));
        columnIdAdresu.setCellValueFactory(new PropertyValueFactory<>("id_adresu"));

        tablePracownicy.setItems(pracownicy);
        tablePracownicy.getSortOrder().add(columnIdPracownika);
    }

    /**
     * Metoda wypełniająca komponenty GUI
     * - obszary, które modyfikuje użytkownik
     * @param pracownik obiekt pracownika, z którego pobierane są informacje
     */
    private void SetTextFields(Pracownik pracownik) {
        textAdres.getSelectionModel().select(pracownik.getId_adresu() - 1);
        textAntykwariat.getSelectionModel().select(pracownik.getId_antykwariatu() - 1);
        textBank.setText(pracownik.getNr_konta_bankowego());
        textImie.setText(pracownik.getImie());
        textNazwisko.setText(pracownik.getNazwisko());
        textPesel.setText(pracownik.getPesel());
        textTelefon.setText(pracownik.getNr_telefonu());
        LocalDate localDate = LocalDate.parse(pracownik.getData_urodzenia(), formatter);
        textUrodzenie.setValue(localDate);
    }

    /**
     * Metoda aktywująca komponenty GUI
     */
    private void EnableFields() {
        textAdres.setDisable(false);
        textAntykwariat.setDisable(false);
        textBank.setDisable(false);
        textImie.setDisable(false);
        textNazwisko.setDisable(false);
        textPesel.setDisable(false);
        textTelefon.setDisable(false);
        textUrodzenie.setDisable(false);

        cancelButton.setDisable(false);
        commitButton.setDisable(false);
    }

    /**
     * Metoda dezaktywująca komponenty GUI
     */
    private void DisableFields() {
        ClearFields();

        textAdres.setDisable(true);
        textAntykwariat.setDisable(true);
        textBank.setDisable(true);
        textImie.setDisable(true);
        textNazwisko.setDisable(true);
        textPesel.setDisable(true);
        textTelefon.setDisable(true);
        textUrodzenie.setDisable(true);

        cancelButton.setDisable(true);
        commitButton.setDisable(true);
    }

    /**
     * Metoda czyszcząca komponenty GUI
     */
    private void ClearFields() {
        textUrodzenie.getEditor().clear();
        textAdres.getSelectionModel().clearSelection();
        textAntykwariat.getSelectionModel().clearSelection();
        textBank.clear();
        textImie.clear();
        textTelefon.clear();
        textPesel.clear();
        textNazwisko.clear();

    }

    /**
     * Metoda wyszukująca wpis w bazie
     */
    public void SearchEntry() {
        ObservableList<Pracownik> new_pracownicy = new PracownikDAO().SearchPracownik(textSearch.getText());
        pracownicy.clear();
        tablePracownicy.setItems(new_pracownicy);
        tablePracownicy.getSortOrder().add(columnIdPracownika);
    }

    /**
     * Metoda sprawdzająca wpisane dane do komponentów GUI
     * @param imie imię pracownika
     * @param nazwisko nazwisko pracownika
     * @param data_urodzin data urodzin pracownika
     * @param pesel pesel pracownika
     * @param telefon telefon pracownika
     * @param bank numer bankowy pracownika
     * @param id_ant antykwariat
     * @param id_addr adres
     * @return zwraca prawdę lub fałsz - czy wpisane dane są poprawne zgodnie z przyjętymi kryteriami
     */
    private boolean CheckEntries(String imie, String nazwisko, Date data_urodzin, String pesel, String telefon,
                                String bank, int id_ant, int id_addr) {
        if (pesel == null)
            pesel = "";
        if (id_addr == -1 || id_ant == -1)
            return false;
        if (imie.isEmpty() || nazwisko.isEmpty() || data_urodzin.toString().isEmpty() || telefon.isEmpty() ||
                bank.isEmpty())
            return false;
        if (Pattern.matches(".*\\d.*", imie) || Pattern.matches(".*\\d.*", nazwisko)) //imie.matches(".*\\d.*")
            return false;
        if (Pattern.matches(".*[a-zA-Z]+.*+", pesel) || Pattern.matches(".*[a-zA-Z]+.*+", telefon) ||
                Pattern.matches(".*[a-zA-Z]+.*", bank))
            return false;
        if (imie.length() > 20 || nazwisko.length() > 20 || pesel.length() > 11 || telefon.length() > 9 || bank.length() > 8)
            return false;
        return true;
    }

    /**
     * Metoda pobierająca dane z komponentów GUI
     * @return zwraca prawdę lub fałsz - czy wpisane dane są poprawne i można wykonać na nich akcję
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     */
    private boolean GetEntries() throws NullPointerException, IndexOutOfBoundsException {
        try {
            bank = textBank.getText();
            imie = textImie.getText();
            imie = imie.substring(0, 1).toUpperCase() + imie.substring(1);
            nazwisko = textNazwisko.getText();
            nazwisko = nazwisko.substring(0, 1).toUpperCase() + nazwisko.substring(1);
            pesel = textPesel.getText();
            telefon = textTelefon.getText();
            data_urodzin = Date.valueOf(textUrodzenie.getValue());
            id_ant = textAntykwariat.getSelectionModel().getSelectedIndex();
            id_addr = textAdres.getSelectionModel().getSelectedIndex();

            if (!CheckEntries(imie, nazwisko, data_urodzin, pesel, telefon, bank, id_ant, id_addr)) {
                ShowAlert("Jedno z pól zawiera nieprawidłowe dane!");
                return false;
            }
        } catch (NullPointerException ex) {
            ShowAlert("Jedno z pól zawiera nieprawidłowe dane!");
            return false;
        } catch (IndexOutOfBoundsException ex) {
            ShowAlert("Jedno z pól zawiera nieprawidłowe dane!");
            return false;
        }
        return true;
    }

    /**
     * Metoda uaktywniająca akcję dodawania pracownika do bazy danych
     */
    public void InsertPracownikAction() {
        EnableFields();
        ClearFields();
        CancelActions();
        isAddingPracownik = true;
    }

    /**
     * Metoda uaktywniająca akcję zaktualizowania pracownika
     * @throws NullPointerException
     */
    public void UpdatePracownikAction() throws NullPointerException {
        DisableFields();
        CancelActions();
        Pracownik pracownik;
        try {
            pracownik = tablePracownicy.getSelectionModel().getSelectedItem();
            SetTextFields(pracownik);

            textBank.setDisable(false);
            textNazwisko.setDisable(false);
            textTelefon.setDisable(false);
            isUpdatingPracownik = true;

            cancelButton.setDisable(false);
            commitButton.setDisable(false);
        } catch (NullPointerException ex) {
            ShowAlert("Nie zaznaczyłeś wiersza!");
            commitButton.setDisable(true);
            cancelButton.setDisable(true);
        }
    }

    /**
     * Metoda dodająca pracownika do bazy danych
     * @return zwraca prawdę lub fałsz - czy dodanie zakończyło się sukcesem
     * @throws SQLException
     * @throws IndexOutOfBoundsException
     */
    private boolean InsertPracownik() throws SQLException, IndexOutOfBoundsException {
        if (!GetEntries())
            return false;
        int id = new PracownikDAO().MaxIdEntry();

        new PracownikDAO().InsertPracownik(id, imie, nazwisko, data_urodzin, pesel, telefon, bank,
                id_ant + 1, id_addr + 1);
        return true;
    }

    /**
     * Metoda przerywająca akcję
     */
    public void CancelEntries() {
        try {
            DatabaseConnect.ExecuteUpdateStatement("ROLLBACK");
        } catch (SQLException ex) {
            ShowAlert(ex.toString());
        }
        CancelActions();
        DisableFields();
        RefreshTable();
    }

    /**
     * Metoda dezaktywująca akcje
     */
    private void CancelActions()
    {
        isUpdatingPracownik = false;
        isAddingPracownik = false;
    }

    /**
     * Metoda usuwająca pracownika z bazy
     * @throws NullPointerException
     */
    public void DeleteEntry() throws NullPointerException {
        Pracownik pracownik = tablePracownicy.getSelectionModel().getSelectedItem();
        DisableFields();
        int id;
        try {
            id = pracownik.getId_pracownika();
            new PracownikDAO().DeletePracownik(id);
            commitButton.setDisable(false);
            cancelButton.setDisable(false);
        } catch (SQLException ex) {
            ShowAlert(ex.toString());
        } catch (NullPointerException ex) {
            ShowAlert("Nie zaznaczyłeś wiersza!");
            commitButton.setDisable(true);
            cancelButton.setDisable(true);
        }
        CancelActions();
        RefreshTable();
    }

    /**
     * Metoda zatwierdzająca wykonaną akcję
     * @throws SQLException
     */
    public void CommitEntry() throws SQLException {
        String cmd = "COMMIT";
        boolean entries_correct = true;
        if (isAddingPracownik) {
            entries_correct = InsertPracownik();
        }
        if (isUpdatingPracownik) {
            entries_correct = UpdateEntry();
        }
        if (!entries_correct) {
            cmd = "ROLLBACK";
            DatabaseConnect.ExecuteUpdateStatement(cmd);
        } else {
            DatabaseConnect.ExecuteUpdateStatement(cmd);
            DisableFields();
            CancelActions();
        }
        RefreshTable();
    }

    /**
     * Właściwa metoda uaktualniająca dane pracownika w bazie
     * @return
     * @throws SQLException
     * @throws NullPointerException
     */
    private boolean UpdateEntry() throws SQLException, NullPointerException {
        Pracownik pracownik = tablePracownicy.getSelectionModel().getSelectedItem();
        int id;
        if (!GetEntries())
            return false;
        try {
            id = pracownik.getId_pracownika();
            new PracownikDAO().UpdatePracownik(id, nazwisko, telefon, bank);
        } catch (NullPointerException ex) {
            ShowAlert("Nie zaznaczyłeś wiersza!");
            commitButton.setDisable(true);
            cancelButton.setDisable(true);
        }

        return true;
    }

    /**
     * Metoda wyświetlająca alerty
     * @param ex
     */
    private void ShowAlert(Object ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(ex.toString());
        alert.show();
    }
}
