package example.simple.random;

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
    private int rand;

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
        rand = (int) (Math.random() * 100);
    }

    public void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        serverSocket.send(outPacket);
    }

    public String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        serverSocket.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    public int readInt() throws IOException {
        return Integer.parseInt(readUTF());
    }

    public void run() {
        System.out.println("Server dang chay...");
        System.out.println("rand: " + rand);
        try {
            while (true) {
                int n = readInt();
                if (n > rand) {
                    write("lon hon so ngau nhien");
                } else if (n < rand) {
                    write("nhỏ hon so ngau nhien");
                } else {
                    write("dung");
                }
            }
        } catch (SocketException ex) {
            System.out.println("Client da dong");
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            ServerUDP server = new ServerUDP();
            server.run();
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
