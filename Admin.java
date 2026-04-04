import java.util.ArrayList;

public class Admin extends User {
    ArrayList<Customer> customers;
    ArrayList<Order> orders;

    public Admin(String id, String nama, String noHp,
            ArrayList<Customer> customers, ArrayList<Order> orders) {
        super(id, nama, noHp);
        this.customers = customers;
        this.orders = orders;
    }

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

    public void updateStatus(int index) {
        Order o = orders.get(index);

        if (o.status == StatusLaundry.SELESAI) {
            System.out.println("Laundry sudah selesai!");
            return;
        }

        if (o.status.ordinal() < StatusLaundry.SELESAI.ordinal()) {
            o.status = StatusLaundry.values()[o.status.ordinal() + 1];
            System.out.println("Status: " + o.status);
        }
    }
}