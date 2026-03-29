public class Order {
    String idOrder;
    Customer customer;
    String layanan;
    double berat;
    double harga;
    StatusLaundry status;
    boolean sudahBayar;
    boolean antarJemput;

    public Order(String idOrder, Customer customer, String layanan, double berat, boolean antarJemput) {
        this.idOrder = idOrder;
        this.customer = customer;
        this.layanan = layanan;
        this.berat = berat;
        this.antarJemput = antarJemput;
        this.status = StatusLaundry.DITERIMA;
        this.sudahBayar = false;

        hitungHarga();
    }

    void hitungHarga() {
        switch (layanan.toLowerCase()) {
            case "cuci":
                harga = berat * 5000;
                break;
            case "setrika":
                harga = berat * 4000;
                break;
            default:
                harga = berat * 6000;
        }
    }

    public String toString() {
        return idOrder + " | " + customer.nama + " | " + layanan +
                " | " + berat + "kg | Rp" + harga +
                " | " + status +
                " | Bayar: " + (sudahBayar ? "Lunas" : "Belum");
    }
}