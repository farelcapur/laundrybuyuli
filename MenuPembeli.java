import java.util.ArrayList;
import java.util.Scanner;

public class MenuPembeli {

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    public static void jalankan(Scanner sc,
            ArrayList<Customer> customers,
            ArrayList<Order> orders,
            ArrayList<LaundryService> services) {

        int pilih = -1;

        do {
            System.out.println("\n=== MENU PEMBELI ===");
            System.out.println("1. Pesan Laundry");
            System.out.println("2. Konfirmasi Pembayaran");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            if (!sc.hasNextInt()) {
                System.out.println("Harus angka!");
                sc.next();
                continue;
            }

            pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {

                case 1:
                    // INPUT CUSTOMER
                    int nextNum = customers.isEmpty() ? 1 : customers.size() + 1;
                    String id = "CUST" + nextNum;

                    System.out.println("ID Customer: " + id);

                    String nama;
                    do {
                        System.out.print("Nama (max 10 huruf): ");
                        nama = sc.nextLine();

                        if (!nama.matches("[a-zA-Z ]+")) {
                            System.out.println("Nama hanya huruf!");
                        } else if (nama.length() > 10) {
                            System.out.println("Maksimal 10 karakter!");
                        }

                    } while (!nama.matches("[a-zA-Z ]+") || nama.length() > 10);

                    String hp;
                    do {
                        System.out.print("No HP (max 13 digit): ");
                        hp = sc.nextLine();

                        if (!hp.matches("[0-9]+")) {
                            System.out.println("Hanya angka!");
                        } else if (hp.length() > 13) {
                            System.out.println("Maksimal 13 digit!");
                        }

                    } while (!hp.matches("[0-9]+") || hp.length() > 13);

                    String alamat;
                    do {
                        System.out.print("Alamat (huruf saja): ");
                        alamat = sc.nextLine();

                        if (!alamat.matches("[a-zA-Z ]+")) {
                            System.out.println("Hanya huruf!");
                        }

                    } while (!alamat.matches("[a-zA-Z ]+"));

                    Customer cust = new Customer(id, nama, hp, alamat);
                    customers.add(cust);
                    simpanCustomer(cust); // simpan ke file

                    // PILIH LAYANAN
                    System.out.println("\n--- DAFTAR LAYANAN ---");
                    for (LaundryService ls : services) {
                        System.out.println(ls.idService + " - " + ls.namaLayanan + " (Rp" + ls.hargaPerKg + "/kg)");
                    }
                    LaundryService layananDipilih = null;
                    do {
                        System.out.print("Masukkan Kode Layanan (contoh: SRV1): ");
                        String kodeInput = sc.nextLine().trim();

                        for (LaundryService ls : services) {
                            // equalsIgnoreCase agar tidak peduli besar/kecil huruf
                            if (ls.idService.equalsIgnoreCase(kodeInput)) {
                                layananDipilih = ls;
                                break;
                            }
                        }

                        if (layananDipilih == null) {
                            System.out.println("Kode layanan tidak ditemukan! Silakan coba lagi.");
                        }
                    } while (layananDipilih == null);
                    // BERAT
                    double berat = -1;
                    do {
                        System.out.print("Berat (kg): ");

                        if (!sc.hasNextDouble()) {
                            System.out.println("Harus angka!");
                            sc.next();
                            continue;
                        }

                        berat = sc.nextDouble();
                        sc.nextLine();

                        if (berat <= 0) {
                            System.out.println("Harus > 0!");
                        }

                    } while (berat <= 0);

                    // ANTAR JEMPUT
                    String input;
                    boolean antar;

                    do {
                        System.out.print("Antar Jemput (y/n): ");
                        input = sc.nextLine();

                        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("ya")) {
                            antar = true;
                            break;
                        } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                            antar = false;
                            break;
                        } else {
                            System.out.println("Input salah!");
                        }

                    } while (true);

                    if (antar) {
                        System.out.println("Akan dikirim kurir");
                    } else {
                        System.out.println("Ambil sendiri");
                    }

                    // BUAT ORDER
                    Order o = new Order("ORD" + (orders.size() + 1),
                            cust,
                            layananDipilih,
                            berat,
                            antar);

                    orders.add(o);
                    simpanOrder(o); // simpan ke file

                    System.out.println("Order berhasil dibuat!");
                    break;
                case 2:
                    if (orders.isEmpty()) {
                        System.out.println("Tidak ada data order ditemukan di file!");
                        break;
                    }

                    System.out.println("\n--- DAFTAR SEMUA ORDER ---");
                    for (Order ord : orders) {
                        System.out.printf("ID: %-7s | Nama: %-10s | Total: Rp%-8s | [%s]\n",
                                ord.idOrder, ord.customer.nama, ord.harga,
                                (ord.sudahBayar ? "Sudah Bayar" : "Belum Bayar"));
                    }

                    Order orderKetemu = null;
                    do {
                        System.out.print("\nMasukkan ID Order yang ingin dibayar (atau ketik 'batal'): ");
                        String idInput = sc.nextLine().trim();

                        if (idInput.equalsIgnoreCase("batal"))
                            break;

                        for (Order ord : orders) {
                            if (ord.idOrder.equalsIgnoreCase(idInput)) {
                                orderKetemu = ord;
                                break;
                            }
                        }

                        if (orderKetemu == null) {
                            System.out.println("ID Order tidak ditemukan!");
                        } else if (orderKetemu.sudahBayar) {
                            System.out.println("Order ini sudah lunas.");
                            orderKetemu = null; // reset agar loop lagi atau keluar
                        }
                    } while (orderKetemu == null);

                    if (orderKetemu != null) {
                        orderKetemu.sudahBayar = true;
                        updateFileOrder(orders); // Simpan ke orders.txt
                        System.out.println("Pembayaran ID " + orderKetemu.idOrder + " Berhasil Dikonfirmasi!");
                    }
                    break;
            }

        } while (pilih != 0);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<LaundryService> services = new ArrayList<>();

        // isi layanan
        services.add(new LaundryService("SRV1", "Cuci Basah", 5000, 2));
        services.add(new LaundryService("SRV2", "Cuci Kering", 6000, 2));
        services.add(new LaundryService("SRV3", "Setrika", 4000, 1));
        services.add(new LaundryService("SRV4", "Cuci + Setrika", 7000, 2));
        services.add(new LaundryService("SRV5", "Karpet", 8000, 3));
        services.add(new LaundryService("SRV6", "Bed Cover", 10000, 3));

        // jalankan menu pembeli
        MenuPembeli.loadCustomerDariFile(customers);
        MenuPembeli.loadDataDariFile(orders, services, customers);
        MenuPembeli.jalankan(sc, customers, orders, services);
    }

    static void simpanCustomer(Customer c) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("customers.txt", true);
            fw.write(c.id + "|" + c.nama + "|" + c.noHp + "|" + c.alamat + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal simpan customer!");
        }
    }

    static void simpanOrder(Order o) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", true);

            // Mengonversi boolean menjadi kata-kata yang diinginkan
            String statusBayarTeks = o.sudahBayar ? "Sudah Bayar" : "Belum Bayar";
            String statusAntarTeks = o.antarJemput ? "Diantar" : "Diambil";

            // Menulis ke file dengan format baru
            fw.write(o.idOrder + "|" +
                    o.customer.nama + "|" +
                    o.service.namaLayanan + "|" +
                    o.berat + "|" +
                    o.harga + "|" +
                    o.status + "|" +
                    statusBayarTeks + "|" + // Menggunakan teks hasil konversi
                    statusAntarTeks + "\n"); // Menggunakan teks hasil konversi

            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal simpan order!");
        }
    }

    static void updateFileOrder(ArrayList<Order> orders) {
        try {
            // FileWriter tanpa 'true' agar menimpa file lama dengan data terbaru
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", false);
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
            System.out.println("Gagal memperbarui file order!");
        }
    }

    public static void loadDataDariFile(ArrayList<Order> orders, ArrayList<LaundryService> services,
            ArrayList<Customer> customers) {
        try {
            java.io.File file = new java.io.File("orders.txt");
            if (!file.exists())
                return; // Jika file belum ada, abaikan

            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 8)
                    continue;

                // Buat objek pendukung (Dummy/Sederhana karena kita hanya butuh Nama & Layanan)
                Customer tempCust = new Customer("CUST_TEMP", data[1], "", "");
                LaundryService tempServ = new LaundryService("", data[2], 0, 0);

                // Buat objek Order berdasarkan data file
                Order o = new Order(data[0], tempCust, tempServ, Double.parseDouble(data[3]), false);
                o.harga = Double.parseDouble(data[4]);
                o.status = StatusLaundry.valueOf(data[5]);
                o.sudahBayar = data[6].equalsIgnoreCase("Sudah Bayar");
                o.antarJemput = data[7].equalsIgnoreCase("Diantar");

                orders.add(o);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Gagal memuat data lama: " + e.getMessage());
        }
    }

    public static void loadCustomerDariFile(ArrayList<Customer> customers) {
        try {
            java.io.File file = new java.io.File("customers.txt");
            if (!file.exists())
                return;

            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            customers.clear(); // Pastikan kosong sebelum diisi data file

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 4) {
                    // Tambahkan customer lama ke dalam list memori
                    customers.add(new Customer(data[0], data[1], data[2], data[3]));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Gagal memuat data customer: " + e.getMessage());
        }
    }
}