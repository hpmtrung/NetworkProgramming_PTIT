package core.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ServerUDP {

    public static int PORT = 8888;

    private DatagramSocket serverSocket;
    private byte[] inData;
    private byte[] outData;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;

    public ServerUDP() {
        try {
            serverSocket = new DatagramSocket(PORT);
            System.out.println("Server đang chạy.");
            inData = new byte[1024];
            outData = new byte[1024];
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        serverSocket.close();
    }

    private int readInt() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        serverSocket.receive(inPacket);
        return Integer.parseInt(new String(inPacket.getData(), 0, inPacket.getLength()).trim());
    }

    private String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        serverSocket.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    private void write(String content) throws IOException {
        outData = content.getBytes();
        outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        serverSocket.send(outPacket);
    }

    public void start() throws IOException {
        while (true) {
            int flag = readInt();
            switch (flag) {
                case 1:
                    //write(getResultProblem1());
                    break;
                case 2:
                    //write(getResultProblem2());
                    break;
                default:
                    break;
            }
        }
    }

}
