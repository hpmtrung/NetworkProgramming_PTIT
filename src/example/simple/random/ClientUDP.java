package example.simple.random;

import static example.simple.random.ServerUDP.PORT;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientUDP {

    private DatagramSocket client;
    private byte[] inData;
    private byte[] outData;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;

    public ClientUDP() throws SocketException {
        client = new DatagramSocket();
        inData = new byte[1024];
        outData = new byte[1024];
    }

    public void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, ServerUDP.IP, ServerUDP.PORT);
        client.send(outPacket);
    }

    public String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        client.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    public int readInt() throws IOException {
        return Integer.parseInt(readUTF());
    }

    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhap 1 so (0..100) de doan: ");
            write(sc.nextInt());
            String rep = "";
            while (true) {
                rep = readUTF();
                System.out.println("Ket qua: " + rep);
                if (rep.equals("dung")) {
                    break;
                } else {
                    System.out.println("Nhap 1 so (0..100) de doan: ");
                    write(sc.nextInt());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            ClientUDP client = new ClientUDP();
            client.run();
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
