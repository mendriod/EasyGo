package mendroid.easygo.model;

/**
 * Created by user on 08-Apr-17.
 */

public class City {

    public City(int xid, String text) {
        this.xid = xid;
        this.text = text;
    }

    public int getXid() {
        return xid;
    }

    public String getText() {
        return text;
    }

    int xid;
    String text;

    @Override
    public String toString() {
        return text.toString();
    }
}
