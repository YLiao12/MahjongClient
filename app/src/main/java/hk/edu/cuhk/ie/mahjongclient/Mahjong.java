package hk.edu.cuhk.ie.mahjongclient;

public class Mahjong {
    private int id;
    private String name;

    public Mahjong(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
