package teumin.network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Network {
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Network(Socket socket) throws Exception {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        ois = new ObjectInputStream(is);
    }

    public void close() throws Exception {
        ois.close();
        oos.close();
        is.close();
        os.close();
        socket.close();
    }

    public Data read() throws Exception {
        return (Data)ois.readObject();
    }

    public void write(Data data) throws Exception {
        oos.writeObject(data);
    }
}
