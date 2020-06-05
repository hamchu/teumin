package teumin.server.account;

public class Account {
    private String id;
    private int type;

    public Account() {
        id = null;
        type = -1;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }
}
