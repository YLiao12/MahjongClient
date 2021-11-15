package hk.edu.cuhk.ie.mahjongclient;

public class Table {
    private int tableId;
    private String tableName;

    public int getPlayersNum() {
        return playersNum;
    }

    public void setPlayersNum(int playersNum) {
        this.playersNum = playersNum;
    }

    private int playersNum;

    public Table(int tableId, String tableName, int playersNum) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.playersNum = playersNum;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
