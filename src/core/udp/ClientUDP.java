package core.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {

    private static DatagramSocket client;
    private static byte[] inData;
    private static byte[] outData;
    private static DatagramPacket inPacket;
    private static DatagramPacket outPacket;

    private static InetAddress SERVER_IP;

    public ClientUDP() throws IOException {
        client = new DatagramSocket();
        client.setSoTimeout(4000);
        inData = new byte[1024];
        outData = new byte[1024];
        SERVER_IP = InetAddress.getByName("localhost");
    }

    public static void send(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, SERVER_IP, ServerUDP.PORT);
        client.send(outPacket);
    }

    public static String receive() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        client.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength());
    }
}
