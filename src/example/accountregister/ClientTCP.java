package example.accountregister;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    public ClientTCP() throws IOException {
        client = new Socket("localhost", MulticlientServerTCP.PORT);
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
    }
    
    public void close() {
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void process() {
        System.out.println("TẠO TÀI KHOẢN MỚI:");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String id = "";
            while (true) {
                System.out.println("Nhập mã TK: ");
                id = sc.nextLine();
                if (id.isEmpty()) {
                    System.out.println("Yêu cầu mã TK không để trống");
                    continue;
                }
                if (id.length() > 10) {
                    System.out.println("Yêu cầu mã TK không quá 10 ký tự");
                    continue;
                }
                break;
            }
            String name = "";
            while (true) {
                System.out.println("Nhập tên: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Yêu cầu có thông tin KH");
                    continue;
                }
                break;
            }
            String pass = "";
            while (true) {
                System.out.println("Nhập password: ");
                pass = sc.nextLine();
                if (pass.isEmpty()) {
                    System.out.println("Yêu cầu cần mật khẩu");
                    continue;
                }
                break;
            }
            try {
                dos.writeUTF(id);
                dos.writeUTF(name);
                dos.writeUTF(pass);
                
                System.out.println("DANH SÁCH TK ĐÃ TẠO:");
                System.out.println(dis.readUTF());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
            System.out.println("Tiếp tục tạo TK [y/n]:");
            String ans = sc.nextLine();
            if (ans.toLowerCase().equals("n"))
                break;
        }
    }

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP();
            client.process();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
