package example.simple.matrix;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUDP extends Thread {

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

    public ServerUDP() throws IOException {
        serverSocket = new DatagramSocket(8888);
        inData = new byte[1024];
        outData = new byte[1024];
    }
    
    public int readInt() throws IOException {
        return Integer.parseInt(readUTF());
    }

    public String readUTF() throws IOException {
        inPacket = new DatagramPacket(inData, inData.length);
        serverSocket.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    public void write(Object o) throws IOException {
        outData = String.valueOf(o).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        serverSocket.send(outPacket);
    }

    @Override
    public void interrupt() {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    @Override
    public synchronized void start() {
        System.out.println("Server dang chay...");
        while (true) {
            try {
                int nr = readInt();
                int nc = readInt();
                int[][] mat = new int[nr][nc];
                for (int i = 0; i < nr; i++) {
                    for (int j = 0; j < nc; j++) {
                        mat[i][j] = readInt();
                    }
                }
                write(solve(mat));
            } catch (IOException ex) {
                Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String solve(int[][] mat) {
        int nr = mat.length;
        int nc = mat[0].length;
        int mx = 0, mn = Integer.MAX_VALUE;
        int sumPrime = 0;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (mat[i][j] < mn) {
                    mn = mat[i][j];
                } else if (mat[i][j] > mx) {
                    mx = mat[i][j];
                }
                if (isPrime(mat[i][j]))
                    sumPrime += mat[i][j];
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Số lớn nhất là: ").append(mx).append(" vị trí ");
        boolean first = true;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (mat[i][j] == mx) {
                    if (!first) {
                        sb.append(", ");
                    }
                    sb.append(String.format("(%d, %d)", i, j));
                    first = false;
                }
            }
        }
        sb.append("\n");
        sb.append("Số nhỏ nhất là: ").append(mn).append(" vị trí ");
        first = true;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (mat[i][j] == mn) {
                    if (!first) {
                        sb.append(", ");
                    }
                    sb.append(String.format("(%d, %d)", i, j));
                    first = false;
                }
            }
        }
        sb.append("\n");
        sb.append("Tổng số nguyên tố: ").append(sumPrime);
        return sb.toString();
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
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
