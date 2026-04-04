import java.util.ArrayList; 

public class Customer extends User { // Customer mewarisi User
    String alamat; // menyimpan alamat customer
    ArrayList<Order> riwayat = new ArrayList<>(); // menyimpan riwayat order customer

    // constructor untuk inisialisasi object Customer
    public Customer(String id, String nama, String noHp, String alamat) {
        super(id, nama, noHp); // memanggil constructor dari class User
        this.alamat = alamat; // mengisi alamat customer
    }

    // override method toString untuk menampilkan data customer
    @Override
    public String toString() {
        return id + " | " + nama + " | " + noHp + " | " + alamat; // format tampilan data
    }
}