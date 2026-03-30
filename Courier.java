import java.util.ArrayList;

public class Courier {
    ArrayList<Order> orders;

    public Courier(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public boolean kirimOrder(int index) {
        Order o = orders.get(index);

        // cek pembayaran
        if (!o.sudahBayar) {
            System.out.println("Laundry belum dibayar! Harap lakukan pembayaran terlebih dahulu.");
            return false;
        }

        if (!o.antarJemput) {
            System.out.println("Order ini tidak menggunakan layanan antar jemput.");
            return false;
        }
        // ❗ cek apakah sudah selesai
        if (o.status != StatusLaundry.SELESAI) {
            System.out.println("Laundry belum selesai! Tidak bisa dikirim.");
            return false;
        }

        // baru boleh dikirim
        o.status = StatusLaundry.DIANTAR;
        System.out.println("Laundry sedang diantar oleh kurir...");
        return true;

    }
}