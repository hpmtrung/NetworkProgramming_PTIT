package example.simple.fibo.udp;

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

    private static InetAddress SERVER_IP;

    public ClientUDP() throws IOException {
        client = new DatagramSocket();
        inData = new byte[1024];
        outData = new byte[1024];
        SERVER_IP = InetAddress.getByName("localhost");
    }

    private String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        client.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    private void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, SERVER_IP, ServerUDP.PORT);
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
            System.out.println("Nhap so n: ");
            int n = sc.nextInt();
            write(n);
            int res = Integer.parseInt(readUTF());
            System.out.println("Ket qua fibo(n): " + res);
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
