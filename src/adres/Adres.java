package adres;

public class Adres {
    private Integer id_adresu;
    private String miasto;
    private String ulica;
    private String nr_budynku;
    private Integer nr_lokalu;
    private String kod_pocztowy;

    public Adres(Integer id_adresu, String miasto, String ulica, String nr_budynku, Integer nr_lokalu, String kod_pocztowy) {
        this.id_adresu = id_adresu;
        this.miasto = miasto;
        this.ulica = ulica;
        this.nr_budynku = nr_budynku;
        this.nr_lokalu = nr_lokalu;
        this.kod_pocztowy = kod_pocztowy;
    }

    public Adres() {
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

    /**
     * Pobranie miasta
     *
     * @return miasto
     */
    public String getMiasto() {
        return miasto;
    }

    /**
     * Ustawienie miasta
     *
     * @param miasto miasto
     */
    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    /**
     * Pobranie ulicy
     *
     * @return ulica
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * Ustawienie ulicy
     *
     * @param ulica ulica
     */
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    /**
     * Pobranie nr budynku
     *
     * @return nr budynku
     */
    public String getNr_budynku() {
        return nr_budynku;
    }

    /**
     * Ustawienie nr budynku
     *
     * @param nr_budynku nr budynku
     */
    public void setNr_budynku(String nr_budynku) {
        this.nr_budynku = nr_budynku;
    }

    /**
     * Pobranie nr lokalu
     *
     * @return nr lokalu
     */
    public Integer getNr_lokalu() {
        return nr_lokalu;
    }

    /**
     * Ustawienie nr lokalu
     *
     * @param nr_lokalu nr lokalu
     */
    public void setNr_lokalu(Integer nr_lokalu) {
        this.nr_lokalu = nr_lokalu;
    }

    /**
     * Pobranie kodu pocztowego
     *
     * @return kod pocztowy
     */
    public String getKod_pocztowy() {
        return kod_pocztowy;
    }

    /**
     * Ustawienie kodu pocztowego
     *
     * @param kod_pocztowy kod pocztowy
     */
    public void setKod_pocztowy(String kod_pocztowy) {
        this.kod_pocztowy = kod_pocztowy;
    }
}
