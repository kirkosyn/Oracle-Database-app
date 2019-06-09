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
import klient.Klient;
import klient.KlientDAO;
import login.ScreenController;
import pracownik.Pracownik;
import pracownik.PracownikController;
import pracownik.PracownikDAO;
import zadanie.Zadanie;
import zadanie.ZadanieDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
    private boolean isDeletingPracownik;

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
     * Metoda inicjalizująca kontroler
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

        textDataRejestracji.setValue(null);

        DisplayClient();
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
        adresy.forEach(adres -> textAdres2.getItems().add(adres.getMiasto()));
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
     *
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
     *
     * @param imie         imię pracownika
     * @param nazwisko     nazwisko pracownika
     * @param data_urodzin data urodzin pracownika
     * @param pesel        pesel pracownika
     * @param telefon      telefon pracownika
     * @param bank         numer bankowy pracownika
     * @param id_ant       antykwariat
     * @param id_addr      adres
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
     *
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
     *
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
     *
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
        if (ConfirmationAlert("Czy chcesz cofnąć?")) {
            try {
                DatabaseConnect.ExecuteUpdateStatement("ROLLBACK");
            } catch (SQLException ex) {
                ShowAlert(ex.toString());
            }
            CancelActions();
            DisableFields();
            RefreshTable();
        }
    }

    /**
     * Metoda dezaktywująca akcje
     */
    private void CancelActions() {
        isUpdatingPracownik = false;
        isAddingPracownik = false;
        //isDeletingPracownik = false;
    }

    /**
     * Metoda usuwająca pracownika z bazy
     *
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
            isDeletingPracownik = true;

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
     *
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
        if (isDeletingPracownik)
        {
            if (ConfirmationAlert("Czy chcesz definitywnie usunąć pracownika?")) {
                DatabaseConnect.ExecuteUpdateStatement(cmd);
                DisableFields();
                CancelActions();
                isDeletingPracownik = false;
            }
        }
       else if (!entries_correct) {
            cmd = "ROLLBACK";
            DatabaseConnect.ExecuteUpdateStatement(cmd);
        } else {
            DatabaseConnect.ExecuteUpdateStatement(cmd);
            DisableFields();
            CancelActions();
            InformationAlert("Akcja zakończona sukcesem.");
        }
        RefreshTable();
    }

    /**
     * Właściwa metoda uaktualniająca dane pracownika w bazie
     *
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

    public void LogOut() {
        ScreenController.Activate("login", "Baza Danych Antykwariatów", 310, 230);
    }

    /**
     * Metoda wyświetlająca alerty
     *
     * @param ex
     */
    private void ShowAlert(Object ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(ex.toString());
        alert.show();
    }


    /*****************************************/

    @FXML
    private TableView<Klient> tableKlienci;

    /*
    columns responsible for displaying Klient
     */
    @FXML
    private TableColumn<Klient, Integer> columnId;
    @FXML
    private TableColumn<Klient, String> columnImie;
    @FXML
    private TableColumn<Klient, String> columnNazwisko;
    @FXML
    private TableColumn<Klient, String> columnTelefon;
    @FXML
    private TableColumn<Klient, String> columnEmail;
    @FXML
    private TableColumn<Klient, String> columnAdres;
    @FXML
    private TableColumn<Klient, String> columnZarejestrowany;
    @FXML
    private TableColumn<Klient, String> columnDataRejestracji;


    /**************************************************************
     columns responsible for getting info about client
     **************************************************************/
    @FXML
    private TextField textImie2;
    @FXML
    private TextField textNazwisko2;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textTelefon2;
    @FXML
    private DatePicker textDataRejestracji;
    @FXML
    private ComboBox<String> textAdres2;
    @FXML
    RadioButton textCzyZarejestrwany;


    @FXML
    private TextField textSearch2;

    /***********************************************************
     buttons responsible for selecting action for client
     *************************************************************/
    @FXML
    private Button buttonDodaj;
    @FXML
    private Button buttonZaktualizuj;
    @FXML
    private Button buttonUsun;

    /*

     */
    @FXML
    private Button buttonZaakceptuj;
    @FXML
    private Button buttonAnuluj;

    @FXML
    private Button szukajButton;

    @FXML
    TextField szukajTextField;

    @FXML
    private Label imieLabel;

    @FXML
    private Label nazwiskoLabel;

    @FXML
    private Label telefonLabel;

    @FXML
    private Label urodzinyLabel;


    public static int id = 1;

    private String imie2;
    private String nazwisko2;
    private String numerTelefonu;
    private String email;
    private String czyZarejestrowany;
    private Date dataRejestracji;
    private Integer idAdresu;
    private String information;
    private int idKlienta;


    /***************************************************************************************
     * Observable lists of particular sets of elements
     ***************************************************************************************/

    private ObservableList<Klient> klienci = FXCollections.observableArrayList();


    PracownikController.Mode mode;

    /**
     * method for logging out of PRACOWNIK access to database
     */
    public void Logout() {
        ScreenController.Activate("login", "Baza Danych Antykwariatów", 310, 230);
    }


    /**
     * clears what is cells for adding or updating client
     */
    private void ClearCells() {
        textCzyZarejestrwany.setSelected(false);
        textDataRejestracji.setValue(null);
        textAdres2.getSelectionModel().clearSelection();
        textImie2.clear();
        textNazwisko2.clear();
        textTelefon2.clear();
        textEmail.clear();

    }

    /**
     * function used when we don't want change database
     * for Anuluj Button
     */
    public void AnulujPressed() throws SQLException {
        ClearCells();
        ChangePaneAddClientActivity(true);
        if (mode == PracownikController.Mode.DELETE) {
            if (ConfirmationAlert("Czy chcesz cofnąć?")) {
                DatabaseConnect.ExecuteRollback();
                DisplayClient();
            }
        }
    }

    /**
     * displays Clients on TableView
     */
    public void DisplayClient() {

        klienci = new KlientDAO().GetAllKlienci();

        columnId.setCellValueFactory(new PropertyValueFactory<Klient, Integer>("id_klienta"));
        columnImie.setCellValueFactory(new PropertyValueFactory<Klient, String>("imie"));
        columnNazwisko.setCellValueFactory(new PropertyValueFactory<Klient, String>("nazwisko"));
        columnTelefon.setCellValueFactory(new PropertyValueFactory<Klient, String>("nr_telefonu"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Klient, String>("email"));
        columnZarejestrowany.setCellValueFactory(new PropertyValueFactory<Klient, String>("czy_zarejestrowany"));
        columnDataRejestracji.setCellValueFactory(new PropertyValueFactory<Klient, String>("data_rejestracji"));
        columnAdres.setCellValueFactory(new PropertyValueFactory<Klient, String>("id_adresu"));

        tableKlienci.setItems(klienci);
        tableKlienci.getSortOrder().add(columnId);
    }

    /**
     * setting mode for UPDATE Klienci
     */
    public void ActivateAddClient() {
        ClearCells();
        mode = PracownikController.Mode.INSERT;
        ChangePaneAddClientActivity(false);
    }

    /**
     * setting mode for UPDATE Klienci
     */
    public void ActivateUpdateClient() {
        ClearCells();
        mode = PracownikController.Mode.UPDATE;
        ChangePaneAddClientActivity(false);

        Klient klient;
        try {
            klient = tableKlienci.getSelectionModel().getSelectedItem();
            idKlienta = klient.getId_klienta();

            FillAddPane(klient);
        } catch (Exception ex) {
            if (ex.equals("NullPointerException")) ;
            InformationAlert("Wybierz klienta do zaktualizowania.");
        }

    }

    /**
     * function used when we accept changes in data bases
     * it is responsible for making calls to KLIENTDAO class where are functions responsible for editing data base
     *
     * @throws Exception
     */
    public void ZaakceptujActions() throws Exception {

        if (mode == mode.INSERT) {
            boolean parametersOK = WalidataAddClient();

            if (!parametersOK) {
                Alert info = new Alert(Alert.AlertType.ERROR);
                info.setContentText(information);
                info.show();

            } else {
                ClearCells();
                idKlienta = new KlientDAO().MaxIdEntry();
                ChangePaneAddClientActivity(true);
                new KlientDAO().InsertKlient(idKlienta, imie2, nazwisko2, numerTelefonu, email, czyZarejestrowany, dataRejestracji, idAdresu);
            }
        } else if (mode == mode.UPDATE) {
            boolean parametersOK = WalidataAddClient();

            if (!parametersOK) {
                Alert info = new Alert(Alert.AlertType.ERROR);
                info.setContentText(information);
                info.show();
            } else {
                ClearCells();
                ChangePaneAddClientActivity(true);
                new KlientDAO().UpdateKlient(idKlienta, imie2, nazwisko2, numerTelefonu, email, czyZarejestrowany, dataRejestracji, idAdresu);
            }

        } else if (mode == mode.DELETE) {
            if (ConfirmationAlert("Czy chcesz definitywnie usunąć klienta?")) {
                DatabaseConnect.ExecuteCommit();
                buttonZaakceptuj.setDisable(true);
                buttonAnuluj.setDisable(true);
            }

        }
        DisplayClient();


    }

    /**
     * checking if inputs for UPDATE or INSERT new client to database are correct
     * displaying information about user mistakes
     *
     * @return true if data in  paneClientAdd are correct, false otherwise
     */
    private boolean WalidataAddClient() {
        imie2 = textImie2.getText();
        nazwisko2 = textNazwisko2.getText();
        information = "";
        numerTelefonu = textTelefon2.getText();

        if (!(imie2.length() == 0))
            imie2 = imie2.substring(0, 1).toUpperCase() + imie2.substring(1);
        if (!(nazwisko2.length() == 0))
            nazwisko2 = nazwisko2.substring(0, 1).toUpperCase() + nazwisko2.substring(1);


        dataRejestracji = null;
        if (textDataRejestracji.getValue() != null)
            dataRejestracji = Date.valueOf(textDataRejestracji.getValue());


        if (textCzyZarejestrwany.isSelected())
            czyZarejestrowany = "t";
        else
            czyZarejestrowany = "n";


        email = textEmail.getText();
        if (email == null)
            email = "";

        idAdresu = -1;
        for (Adres adr : adresy) {
            if (adr.getMiasto().equals(textAdres2.getSelectionModel().getSelectedItem())) {
                idAdresu = adr.getId_adresu();
                break;
            }
        }
        try {
            if (idAdresu.equals(-1)) {
                information = "Zaznacz adres.";
                return false;
            }

            if (((!email.contains("@")) && (!email.equals("")))) {
                information = "Niepoprawny email.";
                return false;
            }

            if (imie2.equals("") || nazwisko2.equals("")) {
                information = "Nie podano danych pracownika.";
                return false;
            }
            if (Pattern.matches(".*\\d.*", imie2) || Pattern.matches(".*\\d.*", nazwisko2)) //imie.matches(".*\\d.*")
            {
                information = "Jedno z pól zawiera błędne dane.";
                return false;
            }
            if (numerTelefonu.equals("")) {
                information = "Nie podano numeru telefonu.";
                return false;
            }
            if (imie2.length() > 20 || nazwisko2.length() > 20 || numerTelefonu.length() > 9) {
                information = "Za długie dane.";
                return false;
            }
            if (czyZarejestrowany.equals("t")) {
                try {
                    dataRejestracji = Date.valueOf(textDataRejestracji.getValue());
                } catch (Exception ex) {
                    Alert info = new Alert(Alert.AlertType.ERROR);
                    info.setContentText("Podaj datę rejestracji.");
                    info.show();
                    return false;
                }
                dataRejestracji = Date.valueOf(textDataRejestracji.getValue());
            } else {
                dataRejestracji = null;
            }
        } catch (Exception ex) {

        }

        return true;
    }

    /**
     * function is used for button USUN
     * this function is responsible for removing client
     */
    public void DeleteClient() {
        int id;
        mode = PracownikController.Mode.DELETE;
        //  if (ConfirmationAlert("Czy chcesz usunąć klienta?")) {
        try {
            id = tableKlienci.getSelectionModel().getSelectedItem().getId_klienta();
            new KlientDAO().DeleteClient(id);
            DisplayClient();
            buttonAnuluj.setDisable(false);
            buttonZaakceptuj.setDisable(false);

        } catch (Exception ex) {
            if (ex.equals("NullPointerException")) ;
            InformationAlert("Nie wybrano klienta!");
        }
        // }

    }

    public void SearchClient() {
        String szukaj;
        szukaj = szukajTextField.getText();

        klienci = new KlientDAO().SearchKlient(szukaj);
        tableKlienci.setItems(klienci);
    }

    /**
     * function for filling panel with KLIENT data.
     * Used for UPDATE KLIENT
     *
     * @param klient object from which we put values into panel for adding new Klient
     */
    private void FillAddPane(Klient klient) {
        textEmail.setText(klient.getEmail());
        textImie2.setText(klient.getImie());
        textTelefon2.setText(klient.getNr_telefonu());
        textNazwisko2.setText(klient.getNazwisko());

        for (Adres adres : adresy) {
            if (adres.getId_adresu().equals(klient.getId_adresu())) {
                textAdres2.setValue(adres.getMiasto());
                break;
            }

        }
        if (klient.getCzy_zarejestrowany().equals("tak"))
            textCzyZarejestrwany.setSelected(true);
        else
            textCzyZarejestrwany.setSelected(false);

        textDataRejestracji.setValue(klient.getRegistrationData());

    }

    /**
     * function is used for setting status of panel for client controls for adding new client
     *
     * @param status false- panel is active, true- panel is discative
     */
    private void ChangePaneAddClientActivity(boolean status) {
        textEmail.setDisable(status);

        textImie2.setDisable(status);
        textTelefon2.setDisable(status);
        textNazwisko2.setDisable(status);
        textAdres2.setDisable(status);

        textCzyZarejestrwany.setDisable(status);
        textDataRejestracji.setDisable(status);

        buttonAnuluj.setDisable(status);
        buttonZaakceptuj.setDisable(status);
    }


    private void InformationAlert(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(information);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.show();
    }

    /**
     * function is used to confirm user actions
     *
     * @param header question to user if he is sure of his action
     * @return confirms if action is going to be done or not
     */
    private boolean ConfirmationAlert(String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Czy jesteś pewien?");
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return false;

    }


}