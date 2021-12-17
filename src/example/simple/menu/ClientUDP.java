package example.simple.menu;

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
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap 3 so:");
        int[] arr = new int[3];
        try {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = sc.nextInt();
                write(arr[i]);
            }
            boolean stop = false;
            while (!stop) {
                String cmd = readUTF();
                switch (cmd) {
                    case "cmd_menu":
                        System.out.println(readUTF());
                        System.out.println("Nhap lua chọn: ");
                        int ch = sc.nextInt();
                        write(ch);
                        break;
                    case "cmd_uclnbcnn":
                        int ucln = readInt();
                        int bcnn = readInt();
                        System.out.println("Ket qua: ucln la " + ucln + ", bcnn la " + bcnn);
                        break;
                    case "cmd_sole":
                        int n = readInt();
                        boolean first = true;
                        System.out.println("Ket qua cac so le: ");
                        for (int i = 0; i < n; i++) {
                            if (!first) {
                                System.out.println(", ");
                            }
                            System.out.println(readInt());
                        }
                        break;
                    case "cmd_dung":
                        stop = true;
                        System.out.println("Ket thuc chuong trinh");
                        break;
                    case "cmd_loi":
                        // Thong bao loi
                        System.out.println(readUTF());
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ClientUDP clientUDP = new ClientUDP();
            clientUDP.run();
        } catch (SocketException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
