package example.simple.fibo.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUDP extends Thread {

    public static int PORT = 8888;

    private DatagramSocket serverSocket;
    private byte[] inData;
    private byte[] outData;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;
    private boolean listening = false;

    public ServerUDP() throws SocketException {
        serverSocket = new DatagramSocket(PORT);
        inData = new byte[1024];
        outData = new byte[1024];
    }

    @Override
    public void interrupt() {
        listening = false;
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To changee body of generated methods, choose Tools | Templates.
        listening = true;
    }

    private String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        serverSocket.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    private void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        serverSocket.send(outPacket);
    }

    @Override
    public void run() {
        System.out.println("Server dang chay...");
        try {
            try {
                while (listening) {
                    int n = Integer.parseInt(readUTF());
                    write(fibo(n));
                }
            } catch (SocketException ex) {
                System.out.println("Client ket thuc");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
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
            ServerUDP server = new ServerUDP();
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
