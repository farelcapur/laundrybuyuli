public class LaundryService {
    
    String idService;
    String namaLayanan;
    double hargaPerKg;
    int estimasiHari;

    // Constructor
    public LaundryService(String idService, String namaLayanan, double hargaPerKg, int estimasiHari) {
        this.idService = idService;
        this.namaLayanan = namaLayanan;
        this.hargaPerKg = hargaPerKg;
        this.estimasiHari = estimasiHari;
    }

    // Method hitung harga
    public double hitungHarga(double berat) {
        return berat * hargaPerKg;
    }

    // Method tampilkan data
    public String toString() {
        return idService + " | " + namaLayanan + 
               " | Rp" + hargaPerKg + "/kg" +
               " | Estimasi: " + estimasiHari + " hari";
    }
}