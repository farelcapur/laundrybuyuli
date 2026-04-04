import java.util.ArrayList;
import java.util.Scanner;

public class MainLaundry {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<LaundryService> services = new ArrayList<>();

        services.add(new LaundryService("SRV1", "Cuci Basah", 5000, 2));
        services.add(new LaundryService("SRV2", "Cuci Kering", 6000, 2));
        services.add(new LaundryService("SRV3", "Setrika", 4000, 1));
        services.add(new LaundryService("SRV4", "Cuci + Setrika", 7000, 2));
        services.add(new LaundryService("SRV5", "Karpet", 8000, 3));
        services.add(new LaundryService("SRV6", "Bed Cover", 10000, 3));

        Admin admin = new Admin("ADM1", "Admin Utama", "08123456789", customers, orders);
        Courier kurir = new Courier("CR1", "Kurir 1", "08129876543", orders);
        MenuPembeli.loadDataDariFile(orders, services, customers);

        int menu = -1; // penting!

        do {
            System.out.println("\n=== ADMIN LAUNDRY BU YULI ===");
            System.out.println("1. Lihat Order");
            System.out.println("2. Lihat Customer");
            System.out.println("3. Update Status");
            System.out.println("4. Kirim Laundry");
            // System.out.println("5. Konfirmasi Pembayaran");
            System.out.println("0. Keluar");

            System.out.print("Pilih: ");

            // 🔒 validasi input
            if (!sc.hasNextInt()) {
                System.out.println("Input harus berupa angka!");
                sc.next(); // buang input salah
                continue;
            }

            menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {

                case 1:
                    // Lihat Order dari file
                    lihatOrderDariFile();
                    break;

                case 2:
                    // Lihat Customer dari file
                    lihatCustomerDariFile();
                    break;

                case 3:
                    // Update Status
                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order di sistem!");
                        break;
                    }
                    lihatOrderDariFile(); // Tampilkan data terbaru dari notepad
                    System.out.print("Masukkan ID Order yang akan diupdate (Contoh: ORD1): ");
                    String idUpdate = sc.nextLine().trim();

                    Order orderUpdate = null;
                    for (Order o : orders) {
                        if (o.idOrder.equalsIgnoreCase(idUpdate)) {
                            orderUpdate = o;
                            break;
                        }
                    }

                    if (orderUpdate != null) {
                        // cari indexnya di ArrayList untuk dikirim ke method admin.updateStatus
                        int idx = orders.indexOf(orderUpdate);
                        admin.updateStatus(idx);

                        // SIMPAN PERUBAHAN KE NOTEPAD
                        updateFileOrder(orders);
                        System.out.println("Status Berhasil Diperbarui!");
                    } else {
                        System.out.println("ID Order tidak ditemukan!");
                    }
                    break;
                case 4:
                    // Kirim Laundry
                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order di sistem!");
                        break;
                    }

                    lihatOrderDariFile();
                    System.out.print("Masukkan ID Order yang akan dikirim: ");
                    String idKirim = sc.nextLine().trim();

                    Order orderKirim = null;
                    for (Order o : orders) {
                        if (o.idOrder.equalsIgnoreCase(idKirim)) {
                            orderKirim = o;
                            break;
                        }
                    }

                    if (orderKirim != null) {
                        int idx = orders.indexOf(orderKirim);

                        boolean berhasil = kurir.kirimOrder(idx);

                        if (berhasil) {
                            // Hanya simpan ke file dan munculkan pesan sukses jika laundry MEMANG sudah
                            // selesai
                            updateFileOrder(orders);
                            System.out.println("Konfirmasi Pengiriman Berhasil!");
                        }

                    } else {
                        System.out.println("ID Order tidak ditemukan!");
                    }
                    break;

                // case 5:
                // // Konfirmasi Pembayaran
                // if (orders.isEmpty()) {
                // System.out.println("Belum ada order di sistem!");
                // break;
                // }

                // admin.lihatOrder();
                // System.out.print("Index: ");

                // if (!sc.hasNextInt()) {
                // System.out.println("Input harus angka!");
                // sc.next();
                // break;
                // }

                // int b = sc.nextInt();
                // sc.nextLine();

                // if (b < 0 || b >= orders.size()) {
                // System.out.println("Index tidak valid!");
                // break;
                // }

                // orders.get(b).sudahBayar = true;
                // System.out.println("Pembayaran dikonfirmasi!");
                // break;
            }

        } while (menu != 0);
    }

    static void lihatCustomerDariFile() {
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader("customers.txt"));

            String line;
            System.out.println("\n=== DATA CUSTOMER ===");

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                System.out.println(
                        "ID: " + data[0] +
                                " | Nama: " + data[1] +
                                " | HP: " + data[2] +
                                " | Alamat: " + data[3]);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Gagal membaca file customer!");
        }
    }

    static void lihatOrderDariFile() {
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader("orders.txt"));

            String line;
            System.out.println("\n=== DATA ORDER ===");

            // --- TAMBAHKAN JUDUL KOLOM DI SINI ---
            // Menggunakan printf agar jarak antar kolom konsisten (angka setelah %
            // menentukan lebar kolom)
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-7s | %-12s | %-15s | %-7s | %-10s | %-12s | %-12s | %-10s\n",
                    "ID", "Customer", "Layanan", "Berat", "Harga", "Status", "Pembayaran", "Antar");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                // --- SESUAIKAN ISI LIST DENGAN FORMAT JUDUL ---
                System.out.printf("%-7s | %-12s | %-15s | %-5skg | Rp%-8s | %-12s | %-12s | %-10s\n",
                        data[0], // ID
                        data[1], // Customer
                        data[2], // Layanan
                        data[3], // Berat
                        data[4], // Harga
                        data[5], // Status
                        data[6], // Bayar
                        data[7] // Antar
                );
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------");

            br.close();

        } catch (Exception e) {
            System.out.println("Gagal membaca file order atau file belum ada!");
        }
    }

    static void updateFileOrder(ArrayList<Order> orders) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", false); // false = menimpa file lama
            for (Order o : orders) {
                String statusBayarTeks = o.sudahBayar ? "Sudah Bayar" : "Belum Bayar";
                String statusAntarTeks = o.antarJemput ? "Diantar" : "Diambil";

                fw.write(o.idOrder + "|" +
                        o.customer.nama + "|" +
                        o.service.namaLayanan + "|" +
                        o.berat + "|" +
                        o.harga + "|" +
                        o.status + "|" +
                        statusBayarTeks + "|" +
                        statusAntarTeks + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal memperbarui file database!");
        }
    }
}