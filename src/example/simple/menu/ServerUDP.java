package example.simple.menu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    public int readInt() throws IOException {
        return Integer.parseInt(readUTF());
    }

    public void run() {
        System.out.println("Server dang chay...");
        String menu = "1.BCNN va UCLN\n2.Cac so le\n3.Thoat";

        try {
            int [] arr = new int[3];
            
            while (true) {
                
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = readInt();
                }
                
                // 2
                Pair res = ucln_bcnn(arr);
                int ucln = res.x;
                int bcnn = res.y;
                //System.out.println("ucln: " + ucln + ", bcnn: " + bcnn);
                
                boolean stop = false;
                while (!stop) {
                    write("cmd_menu");
                    write(menu);
                    
                    int ch = readInt();
                    switch (ch) {
                        case 1:
                            write("cmd_uclnbcnn");
                            write(ucln);
                            write(bcnn);
                            break;
                        case 2:
                            write("cmd_sole");
                            List<Integer> list = new ArrayList<>();
                            for (int e : arr) if (e % 2 != 0) list.add(e);
                            write(list.size());
                            for (int e : list) write(e);
                            break;
                        case 3:
                            write("cmd_dung");
                            stop = true;
                            break;
                        default:
                            write("cmd_loi");
                            write("Nhap khong dung!");
                            break;
                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int ucln(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        while (x != 0 && y != 0) {
            if (x > y) {
                x %= y;
            } else {
                y %= x;
            }
        }
        return x + y;
    }

    private static Pair ucln_bcnn(int[] arr) {
        int ucln = arr[0];
        int bcnn = arr[0];
        for (int i = 0; i < arr.length; i++) {
            ucln = ucln(ucln, arr[i]);
            bcnn = bcnn * arr[i] / ucln(bcnn, arr[i]);
        }
        return new Pair(ucln, bcnn);
    }

    public static class Pair {

        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

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
