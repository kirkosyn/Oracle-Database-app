package pracownik;

public class Pracownik {
    private Integer id_pracownika;
    private String imie;
    private String nazwisko;
    private String data_urodzenia;
    private String pesel;
    private String nr_konta_bankowego;
    private String nr_telefonu;
    private Integer id_antykwariatu;
    private Integer id_adresu;

    public Pracownik(Integer id_pracownika, String imie, String nazwisko, String data_urodzenia, String pesel,
                     String nr_konta_bankowego, String nr_telefonu, Integer id_antykwariatu, Integer id_adresu) {
        this.id_pracownika = id_pracownika; //.setValue(id_pracownika);
        this.imie = imie; //.setValue(imie);
        this.nazwisko = nazwisko; //.setValue(nazwisko);
        this.data_urodzenia = data_urodzenia; //.setValue(data_urodzenia);
        this.pesel = pesel; //.setValue(pesel);
        this.nr_konta_bankowego = nr_konta_bankowego; //.setValue(nr_konta_bankowego);
        this.nr_telefonu = nr_telefonu; //.setValue(nr_telefonu);
        this.id_antykwariatu = id_antykwariatu; //.setValue(id_antykwariatu);
        this.id_adresu = id_adresu; //.setValue(id_adresu);
    }

    public Pracownik() {
    }

    /**
     * Ustawienie id pracownika
     *
     * @param id_pracownika id pracownika
     */
    public void setId_pracownika(Integer id_pracownika) {
        this.id_pracownika = id_pracownika; //setValue(id_pracownika);
    }

    /**
     * Ustawienie imienia
     *
     * @param imie imie
     */
    public void setImie(String imie) {
        this.imie = imie; //.setValue(imie);
    }

    /**
     * Ustawienie nazwiska
     *
     * @param nazwisko nazwisko
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko; //.setValue(nazwisko);
    }

    /**
     * Ustawienie daty urodzenia
     *
     * @param data_urodzenia data urodzenia
     */
    public void setData_urodzenia(String data_urodzenia) {
        this.data_urodzenia = data_urodzenia; //.setValue(data_urodzenia);
    }

    /**
     * Ustawienie peselu
     *
     * @param pesel pesel
     */
    public void setPesel(String pesel) {
        this.pesel = pesel; //.setValue(pesel);
    }

    /**
     * Ustawienie nr konta bankowego
     *
     * @param nr_konta_bankowego nr konta bankowego
     */
    public void setNr_konta_bankowego(String nr_konta_bankowego) {
        this.nr_konta_bankowego = nr_konta_bankowego; //.setValue(nr_konta_bankowego);
    }

    /**
     * Ustawienie nr telefonu
     *
     * @param nr_telefonu nr telefonu
     */
    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu; //.setValue(nr_telefonu);
    }

    /**
     * Ustawienie id antykwariatu
     *
     * @param id_antykwariatu id antykwariatu
     */
    public void setId_antykwariatu(Integer id_antykwariatu) {
        this.id_antykwariatu = id_antykwariatu; //.setValue(id_antykwariatu);
    }

    /**
     * Ustawienie id adresu
     *
     * @param id_adresu id adresu
     */
    public void setId_adresu(Integer id_adresu) {
        this.id_adresu = id_adresu; //.setValue(id_adresu);
    }

    /**
     * Pobranie id pracownika
     *
     * @return id pracownika
     */
    public Integer getId_pracownika() {
        return id_pracownika;//.get();
    }

    /**
     * Pobranie imienia
     *
     * @return imie
     */
    public String getImie() {
        return imie;//.get();
    }

    /**
     * Pobranie nazwiska
     *
     * @return nazwisko
     */
    public String getNazwisko() {
        return nazwisko;//.get();
    }

    /**
     * Pobranie daty urodzenia
     *
     * @return data urodzenia
     */
    public String getData_urodzenia() {
        return data_urodzenia;//.get();
    }

    /**
     * Pobranie peselu
     *
     * @return pesel
     */
    public String getPesel() {
        return pesel;//.get();
    }

    /**
     * Pobranie nr konta bankowego
     *
     * @return nr konta bankowego
     */
    public String getNr_konta_bankowego() {
        return nr_konta_bankowego;//.get();
    }

    /**
     * Pobranie nr telefonu
     *
     * @return nr telefonu
     */
    public String getNr_telefonu() {
        return nr_telefonu;//.get();
    }

    /**
     * Pobranie id antykwariatu
     *
     * @return id antykwariatu
     */
    public Integer getId_antykwariatu() {
        return id_antykwariatu;//.get();
    }

    /**
     * Pobranie id adresu
     *
     * @return id adresu
     */
    public Integer getId_adresu() {
        return id_adresu;//.get();
    }


}
