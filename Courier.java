import java.util.ArrayList;

public class Courier {
    ArrayList<Order> orders;

    public Courier(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void kirimOrder(int index) {
        orders.get(index).status = StatusLaundry.DIKIRIM;
    }
}