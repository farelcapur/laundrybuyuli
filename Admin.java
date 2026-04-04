import java.util.ArrayList;

public class Admin extends User {// Admin mewarisi User
    ArrayList<Customer> customers;
    ArrayList<Order> orders;

    // constructor Admin
    public Admin(String id, String nama, String noHp,
            ArrayList<Customer> customers, ArrayList<Order> orders) {
        super(id, nama, noHp); // memanggil constructor User
        this.customers = customers; // inisialisasi data customer
        this.orders = orders; // inisialisasi data order
    }

    // method untuk melihat daftar order
    public void lihatOrder() {
        if (orders.isEmpty()) {
            System.out.println("Belum ada order!");
            return;
        }

        System.out.println("=== DAFTAR ORDER ===");

        for (int i = 0; i < orders.size(); i++) {
            System.out.println(i + ". " + orders.get(i));
        }
    }

    // method untuk update status laundry
    public void updateStatus(int index) {
        Order o = orders.get(index);

        if (o.status == StatusLaundry.SELESAI) {
            System.out.println("Laundry sudah selesai!");
            return;
        }
        // menaikkan status ke tahap berikutnya
        if (o.status.ordinal() < StatusLaundry.SELESAI.ordinal()) {
            o.status = StatusLaundry.values()[o.status.ordinal() + 1];
            System.out.println("Status: " + o.status);
        }
    }
}