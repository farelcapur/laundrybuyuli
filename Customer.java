import java.util.ArrayList;

public class Customer {
    String id;
    String nama;
    String noHp;
    String alamat;
    ArrayList<Order> riwayat = new ArrayList<>();

    public Customer(String id, String nama, String noHp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public String toString() {
        return id + " | " + nama + " | " + noHp;
    }
}