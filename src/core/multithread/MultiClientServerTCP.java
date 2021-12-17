package core.multithread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MultiClientServerTCP extends Thread {

    public static final int PORT = 8888;

    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();

    private boolean listening = true;

    public MultiClientServerTCP() throws IOException {
        serverSocket.close();
    }

    @Override
    public void interrupt() {
        listening = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiClientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        listening = true;
    }

    @Override
    public void run() {
        while (true) {
            // Cho client đăng ký
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Có themclient ket noi"); 
                
                Client newClient = new Client(newSocket);
                clients.add(newClient);

                // set client unique name
                newClient.setName("Client " + clients.size());

                // Nhận tin và gửi
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                try {
                                    task(newClient);
                                } catch (SocketException ex) {
                                    clients.remove(newClient);
                                    Thread.currentThread().isInterrupted();
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(MultiClientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            } catch (IOException ex) {
                Logger.getLogger(MultiClientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
    Ex:
    String msg = newClient.getDis().readUTF();
    for (ClientTCP anotherClient : clients) {
        if (anotherClient != newClient) {
            anotherClient.getDos().writeUTF("\n" + newClient.getName() + ": " + msg);
        }
    }
     */
    public abstract void task(Client client) throws SocketException, IOException;

    public static class Client {

        private String name;
        private Socket client;
        private DataInputStream dis;
        private DataOutputStream dos;

        public Client(Socket client) throws IOException {
            this.client = client;
            this.dis = new DataInputStream(client.getInputStream());
            this.dos = new DataOutputStream(client.getOutputStream());
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Socket getClient() {
            return client;
        }

        public DataInputStream getDis() {
            return dis;
        }

        public DataOutputStream getDos() {
            return dos;
        }

    }
    
}
