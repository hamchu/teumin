package teumin.network;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private DataType dataType;
    private ArrayList<Object> objects;

    public Data(DataType dataType) {
        this.dataType = dataType;
        objects = new ArrayList<>();
    }

    public DataType getDataType() {
        return dataType;
    }

    public void add(Object object) {
        objects.add(object);
    }

    public <T> T get(int index) {
        return (T) objects.get(index);
    }
}
