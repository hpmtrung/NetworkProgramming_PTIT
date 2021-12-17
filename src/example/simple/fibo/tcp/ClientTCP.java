package example.simple.fibo.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP extends Thread {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientTCP() throws IOException {
        client = new Socket("localhost", ServerTCP.PORT);
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void interrupt() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhap so n: ");
            int n = sc.nextInt();
            dos.writeInt(n);
            int res = dis.readInt();
            System.out.println("Ket qua fibo(n): " + res);
        } catch (SocketException ex) {
            System.out.println("Server da dong");
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP();
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
