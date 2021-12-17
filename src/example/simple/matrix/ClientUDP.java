package example.simple.matrix;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientUDP extends Thread {

    private DatagramSocket client;
    private byte[] inData;
    private byte[] outData;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;

    public ClientUDP() throws IOException {
        client = new DatagramSocket();
        inData = new byte[1024];
        outData = new byte[1024];
    }

    public int readInt() throws IOException {
        return Integer.parseInt(readUTF());
    }

    public String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        client.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    public void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, ServerUDP.IP, ServerUDP.PORT);
        client.send(outPacket);
    }

    @Override
    public void interrupt() {
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            int nr = sc.nextInt();
            int nc = sc.nextInt();
            write(nr);
            write(nc);
            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    write(sc.nextInt());
                }
            }
            System.out.println(readUTF());
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ClientUDP client = new ClientUDP();
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
