package antykwariat;

public class Antykwariat {
    private String nr_telefonu;
    private String nazwa;
    private String strona_internetowa;
    private Integer id_antykwariatu;
    private String data_powstania;
    private Integer id_adresu;

    public Antykwariat(String nr_telefonu, String nazwa, String strona_internetowa, Integer id_antykwariatu, String data_powstania, Integer id_adresu) {
        this.nr_telefonu = nr_telefonu;
        this.nazwa = nazwa;
        this.strona_internetowa = strona_internetowa;
        this.id_antykwariatu = id_antykwariatu;
        this.data_powstania = data_powstania;
        this.id_adresu = id_adresu;
    }

    public Antykwariat() {
    }

    /**
     * Pobranie nr telefonu
     *
     * @return nr telefonu
     */
    public String getNr_telefonu() {
        return nr_telefonu;
    }

    /**
     * Ustawienie nr telefonu
     *
     * @param nr_telefonu nr telefonu
     */
    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }

    /**
     * Pobranie nazwy
     *
     * @return nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * Ustawienie nazwy
     *
     * @param nazwa nazwa
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * Pobranie strony internetowej
     *
     * @return strona internetowa
     */
    public String getStrona_internetowa() {
        return strona_internetowa;
    }

    /**
     * Ustawienie strony internetowej
     *
     * @param strona_internetowa strona internetowa
     */
    public void setStrona_internetowa(String strona_internetowa) {
        this.strona_internetowa = strona_internetowa;
    }

    /**
     * Pobranie id antykwariatu
     *
     * @return id antykwariatu
     */
    public Integer getId_antykwariatu() {
        return id_antykwariatu;
    }

    /**
     * Ustawienie id antykwariatu
     *
     * @param id_antykwariatu id antykwariatu
     */
    public void setId_antykwariatu(Integer id_antykwariatu) {
        this.id_antykwariatu = id_antykwariatu;
    }

    /**
     * Pobranie daty powstania
     *
     * @return data powstania
     */
    public String getData_powstania() {
        return data_powstania;
    }

    /**
     * Ustawienie daty powstania
     *
     * @param data_powstania data powstania
     */
    public void setData_powstania(String data_powstania) {
        this.data_powstania = data_powstania;
    }

    /**
     * Pobranie id adresu
     *
     * @return id adresu
     */
    public Integer getId_adresu() {
        return id_adresu;
    }

    /**
     * Ustawienie id adresu
     *
     * @param id_adresu id adresu
     */
    public void setId_adresu(Integer id_adresu) {
        this.id_adresu = id_adresu;
    }

}
