import java.util.ArrayList;

public class Customer extends User {
    String alamat;
    ArrayList<Order> riwayat = new ArrayList<>();

    public Customer(String id, String nama, String noHp, String alamat) {
        super(id, nama, noHp); // 🔥 ambil dari parent
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return id + " | " + nama + " | " + noHp + " | " + alamat;
    }
}