import java.util.ArrayList;

public class Courier extends User {
    ArrayList<Order> orders;

    public Courier(String id, String nama, String noHp,
            ArrayList<Order> orders) {
        super(id, nama, noHp);
        this.orders = orders;
    }

    public boolean kirimOrder(int index) {
        Order o = orders.get(index);

        if (!o.sudahBayar) {
            System.out.println("Belum bayar!");
            return false;
        }

        if (!o.antarJemput) {
            System.out.println("Tidak pakai antar jemput!");
            return false;
        }

        if (o.status != StatusLaundry.SELESAI) {
            System.out.println("Belum selesai!");
            return false;
        }

        o.status = StatusLaundry.DIANTAR;
        System.out.println("Sedang diantar...");
        return true;
    }
}