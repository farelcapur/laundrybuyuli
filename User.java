public class User {
    protected String id;
    protected String nama;
    protected String noHp;

    public User(String id, String nama, String noHp) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
    }

    public void tampilInfo() {
        System.out.println(id + " | " + nama + " | " + noHp);
    }
}