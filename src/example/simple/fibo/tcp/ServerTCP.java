package example.simple.fibo.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP extends Thread {

    public static final int PORT = 8888;

    private ServerSocket serverSocket;
    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    private boolean listening = false;

    public ServerTCP() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    @Override
    public void interrupt() {
        listening = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        listening = true;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server dang chay...");
            client = serverSocket.accept();
            System.out.println("Them client ket noi");
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            try {
                while (listening) {
                    int n = dis.readInt();
                    dos.writeInt(fibo(n));
                }
            } catch (SocketException ex) {
                System.out.println("Client da dong");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int fibo(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int f1 = 1, f2 = 1;
        for (int i = 3; i <= n; i++) {
            int t = f1 + f2;
            f1 = f2;
            f2 = t;
        }
        return f2;
    }

    public static void main(String[] args) {
        try {
            ServerTCP server = new ServerTCP();
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
