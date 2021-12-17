package example.accountregister;

import core.db.DataProvider;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MulticlientServerTCP extends Thread {

    public static int PORT = 8888;

    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();

    private boolean listening = true;

    public MulticlientServerTCP() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    @Override
    public void interrupt() {
        listening = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(MulticlientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        listening = true;
    }

    @Override
    public void run() {
        System.out.println("Server đang chạy...");

        while (listening) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Có them client ket noi");

                Client newClient = new Client(newSocket);
                clients.add(newClient);

                newClient.setName("Client " + clients.size());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                String id = newClient.getDis().readUTF();
                                String name = newClient.getDis().readUTF();
                                String pass = newClient.getDis().readUTF();

                                DataProvider.getInstance().executeNonQuery(
                                        String.format("INSERT INTO dbo.THONGTIN (MATK, TENTHONGTIN, MATKHAU) VALUES ('%s', N'%s', '%s')", id, name, pass));

                                ResultSet rs = DataProvider.getInstance().executeQuery("SELECT * FROM dbo.THONGTIN");
                                StringBuilder sb = new StringBuilder();
                                try {
                                    while (rs.next()) {
                                        sb.append(String.format("Mã TK: %s, Tên: %s, Pass: %s\n", rs.getString("MATK"), rs.getString("TENTHONGTIN"), rs.getString("MATKHAU")));
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(MulticlientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                                } finally {
                                    if (rs != null) {
                                        try {
                                            rs.close();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(MulticlientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                newClient.getDos().writeUTF(sb.toString());
                            } catch (SocketException e) {
                                clients.remove(newClient);
                                Thread.currentThread().interrupt();
                            } catch (IOException ex) {
                                Logger.getLogger(MulticlientServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

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

    public static void main(String[] args) {
        MulticlientServerTCP server = new MulticlientServerTCP();
        server.start();
    }
    
}
