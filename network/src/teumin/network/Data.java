package teumin.network;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private ArrayList<Object> objects;

    public Data() {
        objects = new ArrayList<>();
    }

    public void add(Object object) {
        objects.add(object);
    }

    public <T> T get(int index) {
        return (T) objects.get(index);
    }
}
