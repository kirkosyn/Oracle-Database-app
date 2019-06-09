package zadanie;

public class Zadanie {

private  Integer idZadania;
private String rodzajZadania;
private String dataNadania;
private String dataRozpoczecia;
private  String dataZakonczenia;

public Zadanie()
{

}

public Zadanie(int idZadania,String rodzajZadania,String dataNadania,String dataRozpoczecia, String dataZakonczenia)
{
    this.idZadania=idZadania;
    this.rodzajZadania=rodzajZadania;
    this.dataNadania=dataNadania;
    this.dataRozpoczecia=dataRozpoczecia;
    this.dataZakonczenia=dataZakonczenia;
}

    public Integer getIdZadania() {
        return idZadania;
    }

    public void setIdZadania(Integer idZadania) {
        this.idZadania = idZadania;
    }

    public String getRodzajZadania() {
        return rodzajZadania;
    }

    public void setRodzajZadania(String rodzajZadania) {
        this.rodzajZadania = rodzajZadania;
    }

    public String getDataNadania() {
        return dataNadania;
    }

    public void setDataNadania(String dataNadania) {
        this.dataNadania = dataNadania;
    }

    public String getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    public void setDataRozpoczecia(String dataRozpoczecia) {
        this.dataRozpoczecia = dataRozpoczecia;
    }

    public String getDataZakonczenia() {
        return dataZakonczenia;
    }

    public void setDataZakonczenia(String dataZakonczenia) {
        this.dataZakonczenia = dataZakonczenia;
    }


}



