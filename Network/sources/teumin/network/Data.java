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

    public void addObject(Object object) {
        objects.add(object);
    }

    public Object getObject(int index) {
        return objects.get(index);
    }
}
