package biodata;

/**
 *
 * @author Ilham
 */

public class BiodataTable {

    private int id;
    private String nama;
    private String jenisKelamin;
    private String nomorHP;
    private String alamat;

    public BiodataTable(int id, String nama, String jenisKelamin, String nomorHP, String alamat) {
    }

    public BiodataTable() {

    }


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}

