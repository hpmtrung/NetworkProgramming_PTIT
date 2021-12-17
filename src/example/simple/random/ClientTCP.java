package example.simple.random;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientTCP() throws IOException {
        client = new Socket("localhost", ServerTCP.PORT);
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
    }

    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhap 1 so (0..100) de doan: ");
            dos.writeInt(sc.nextInt());
            String rep = "";
            while (true) {
                rep = dis.readUTF();
                System.out.println("Ket qua: " + rep);
                if (rep.equals("dung")) {
                    break;
                } else {
                    System.out.println("Nhap 1 so (0..100) de doan: ");
                    dos.writeInt(sc.nextInt());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP();
            client.run();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
