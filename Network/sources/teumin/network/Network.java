package teumin.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Network {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Network(Socket socket) throws Exception {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void close() throws Exception {
        ois.close();
        oos.close();
        socket.close();
    }

    public Data read() throws Exception {
        return (Data) ois.readObject();
    }

    public void write(Data data) throws Exception {
        oos.writeObject(data);
    }
}
