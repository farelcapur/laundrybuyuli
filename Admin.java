import java.util.ArrayList;

public class Admin {
    ArrayList<Customer> customers;
    ArrayList<Order> orders;

    public Admin(ArrayList<Customer> customers, ArrayList<Order> orders) {
        this.customers = customers;
        this.orders = orders;
    }

    public void lihatOrder() {
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    public void updateStatus(int index) {
        Order o = orders.get(index);

        if (o.status.ordinal() < StatusLaundry.values().length - 1) {
            o.status = StatusLaundry.values()[o.status.ordinal() + 1];
        } else {
            System.out.println("Status sudah paling akhir!");
        }
    }
}