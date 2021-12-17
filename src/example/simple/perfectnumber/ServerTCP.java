package example.simple.perfectnumber;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

    public static int PORT = 8888;

    private ServerSocket serverSocket;
    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ServerTCP() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server đang chay...");
        client = serverSocket.accept();
        System.out.println("Client ket noi...");
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
    }

    public void run() {
        try {
            while (true) {
                int n = dis.readInt();
                boolean found = false;
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (int i = 1; i <= n; i++) {
                    if (isPerfect(i)) {
                        if (!first) {
                            sb.append("#");
                        }
                        sb.append(i);
                        found = true;
                    }
                }
                if (!found) {
                    dos.writeUTF("khong co");
                } else {
                    dos.writeUTF(sb.toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Client da dong");
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean isPerfect(int n) {
        if (n < 1) {
            return false;
        }
        int sum = 0;
        for (int i = 1; i <= n / 2; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        return sum == n;
    }

    public static void main(String[] args) {
        try {
            ServerTCP serverTCP = new ServerTCP();
            serverTCP.run();
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
