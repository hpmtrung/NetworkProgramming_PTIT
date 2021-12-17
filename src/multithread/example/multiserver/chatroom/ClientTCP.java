package multithread.example.multiserver.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientTCP {

    private String name;
    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientTCP(Socket client) throws IOException {
        this.client = client;
        this.dis = new DataInputStream(this.client.getInputStream());
        this.dos = new DataOutputStream(this.client.getOutputStream());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

}
