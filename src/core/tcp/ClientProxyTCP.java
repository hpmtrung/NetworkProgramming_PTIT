package core.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Proxy cho gọi hàm static hỗ trợ 1 client gồm nhiều bài toán con
 * @author DELL
 */
public class ClientProxyTCP {

    private static volatile ClientProxyTCP instance;
    
    private static Socket client;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    public static ClientProxyTCP getInstance() {
        if (instance == null) {
            synchronized (ClientProxyTCP.class) {
                if (instance == null) try {
                    instance = new ClientProxyTCP();
                } catch (IOException ex) {
                    Logger.getLogger(ClientProxyTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return instance;
    }
    
    private ClientProxyTCP() throws IOException {
        client = new Socket("localhost", 8888);
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
    }

    public static void close() throws IOException {
        client.close();
    }

    public static DataInputStream getDis() {
        return dis;
    }

    public static DataOutputStream getDos() {
        return dos;
    }
    
}
