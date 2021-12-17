package example.simple.perfectnumber;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUDP {

    public static final int PORT = 8888;
    public static InetAddress IP;

    private DatagramSocket serverSocket;
    private byte[] inData;
    private byte[] outData;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;

    static {
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerUDP() throws SocketException {
        serverSocket = new DatagramSocket(PORT);
        inData = new byte[1024];
        outData = new byte[1024];
    }

    public void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        serverSocket.send(outPacket);
    }
    
    public String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        serverSocket.receive(inPacket);
        return new String(inData, 0, inData.length).trim();
    }
    
    public int readInt() throws IOException {
        return Integer.parseInt(readUTF());
    }
    
    public void run() {
        System.out.println("Server dang chay...");
        try {
            while (true) {
                int n = readInt();
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
                    write("khong co");
                } else {
                    write(sb.toString());
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
            ServerUDP server = new ServerUDP();
            server.run();
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
