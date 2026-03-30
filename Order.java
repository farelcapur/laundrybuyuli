public class Order {
    String idOrder;
    Customer customer;
    LaundryService service;
    double berat;
    double harga;
    StatusLaundry status;
    boolean sudahBayar;
    boolean antarJemput;

    public Order(String idOrder, Customer customer, LaundryService service, double berat, boolean antarJemput) {
        this.idOrder = idOrder;
        this.customer = customer;
        this.service = service;
        this.berat = berat;
        this.antarJemput = antarJemput;
        this.status = StatusLaundry.DITERIMA;
        this.sudahBayar = false;

        // ambil harga dari service
        this.harga = service.hitungHarga(berat);
    }

    @Override
    public String toString() {
        return idOrder + " | " + customer.nama + " | " + service.namaLayanan +
                " | " + berat + "kg | Rp" + harga +
                " | " + status +
                " | Antar: " + (antarJemput ? "Diantar" : "Diambil") +
                " | Bayar: " + (sudahBayar ? "Sudah Bayar" : "Belum Bayar");
    }
}