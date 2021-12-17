package multithread.example.oneserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP implements Runnable {

    public static final int SERVER_PORT = 8888;

    private static volatile ServerTCP uniqueInstance;

    private ServerSocket serverSocket;
    private List<ClientTCP> clients = new ArrayList<>();

    private int clientInstanceNum = 0;

    private ServerTCP() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server đang chạy.");

            Thread thisThread = new Thread(this);
            thisThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ServerTCP getInstance() {
        if (uniqueInstance == null) {
            synchronized (ServerTCP.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ServerTCP();
                }
            }
        }
        return uniqueInstance;
    }

    public void close() throws IOException {
        if (clientInstanceNum > 0) {
            clientInstanceNum--;
        } else {
            serverSocket.close();
        }

        System.out.println("client num: " + clientInstanceNum);
    }

    @Override
    public void run() {
        while (true) {
            // Cho client đăng ký
            try {
                Socket newClientSocket = serverSocket.accept();
                System.out.println("Có them client ket noi");
                if (newClientSocket != null) {
                    ClientTCP newClient = new ClientTCP(newClientSocket);
                    clients.add(newClient);
                    clientInstanceNum++;
                    String clientName = newClient.getDis().readUTF();
                    newClient.setName(clientName);

                    // Nhận tin và gửi
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (true) {
                                    try {
                                        String msg = newClient.getDis().readUTF();
                                        for (ClientTCP anotherClient : clients) {
                                            if (anotherClient != newClient) {
                                                anotherClient.getDos().writeUTF("\n" + newClient.getName() + ": " + msg);
                                            }
                                        }
                                    } catch (SocketException ex) {
                                        clients.remove(newClient);
//                                        Thread.currentThread().interrupt();
                                        break;
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start();
                }

            } catch (IOException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        ServerTCP.getInstance();
    }

}
