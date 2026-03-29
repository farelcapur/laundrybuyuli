import java.util.ArrayList;
import java.util.Scanner;

public class MainLaundry {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        Admin admin = new Admin(customers, orders);
        Courier kurir = new Courier(orders);

        int menu;

        do {
            System.out.println("\n=== LAUNDRY BU YULI ===");
            System.out.println("1. Tambah Customer");
            System.out.println("2. Buat Order");
            System.out.println("3. Lihat Order");
            System.out.println("4. Update Status (Admin)");
            System.out.println("5. Kirim Laundry (Kurir)");
            System.out.println("6. Bayar");
            System.out.println("0. Exit");
            System.out.print("Pilih: ");
            menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {

                case 1:
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Nama: ");
                    String nama = sc.nextLine();
                    System.out.print("No HP: ");
                    String hp = sc.nextLine();
                    System.out.print("Alamat: ");
                    String alamat = sc.nextLine();

                    customers.add(new Customer(id, nama, hp, alamat));
                    break;

                case 2:

                    // 🔥 tampilkan daftar customer dulu
                    if (customers.isEmpty()) {
                        System.out.println("Belum ada customer!");
                        break;
                    }

                    for (int i = 0; i < customers.size(); i++) {
                        System.out.println(i + ". " + customers.get(i));
                    }

                    // 🔽 baru input setelah lihat list
                    System.out.print("Pilih Customer index: ");
                    int c = sc.nextInt();
                    sc.nextLine();

                    // 🔒 validasi biar tidak error
                    if (c < 0 || c >= customers.size()) {
                        System.out.println("Index tidak valid!");
                        break;
                    }

                    System.out.print("Layanan: ");
                    String layanan = sc.nextLine();

                    System.out.print("Berat: ");
                    double berat = sc.nextDouble();

                    sc.nextLine();
                    System.out.print("Antar Jemput (y/n): ");
                    String input = sc.nextLine();

                    boolean antar = input.equalsIgnoreCase("y") || input.equalsIgnoreCase("ya");

                    Order o = new Order("ORD" + (orders.size() + 1), customers.get(c), layanan, berat, antar);
                    orders.add(o);
                    break;

                case 3:
                    admin.lihatOrder();
                    break;

                case 4:

                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order!");
                        break;
                    }

                    // 🔥 tampilkan daftar order
                    System.out.println("=== DAFTAR ORDER ===");
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println(i + ". " + orders.get(i));
                    }

                    // 🔽 input index
                    System.out.print("Pilih index order: ");
                    int i = sc.nextInt();

                    // 🔒 validasi
                    if (i < 0 || i >= orders.size()) {
                        System.out.println("Index tidak valid!");
                        break;
                    }

                    // 🔄 update status
                    admin.updateStatus(i);

                    System.out.println("Status berhasil diupdate!");
                    break;

                case 5:
                    System.out.print("Index order: ");
                    int k = sc.nextInt();
                    kurir.kirimOrder(k);
                    break;

                case 6:
                    System.out.print("Index order: ");
                    int b = sc.nextInt();
                    orders.get(b).sudahBayar = true;
                    break;
            }

        } while (menu != 0);
    }
}