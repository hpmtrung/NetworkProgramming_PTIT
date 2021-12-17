package multithread.example.multiserver.exam;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

    private static final int QUEST_NUM = 20;
    private static final int MIN_NUM = 1;

    private static volatile ServerTCP uniqueInstance;

    private ServerSocket serverSocket;
    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Question[] questionsData;
    private String[] savedAnswers;

    private ServerTCP() {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Server đang chạy.");
            client = serverSocket.accept();
            System.out.println("Server đang cho client ket noi");
            // Khai bao doi tuong in, out de lay va dua du lieu vao stream
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
            Nếu kết nối nhiều client thì lấy dữ liệu lúc có client kết nối và 2 biến
            questionsData và savedAnswers là biến cục bộ, không để toàn cục
        */
        questionsData = new Question[QUEST_NUM];
        savedAnswers = new String[QUEST_NUM];
        
        ResultSet rs = DataProvider.getInstance().executeQuery("SELECT TOP " + QUEST_NUM + " * from dbo.BODE order by newid()");

        try {
            int i = 0;
            while (rs.next()) {
                Question q = new Question(rs);
                questionsData[i++] = q;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
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
        serverSocket.close();
    }

    public void start() throws IOException {
        while (true) {
            String cmd = dis.readUTF();
//            System.out.println("Server nhan cmd: " + cmd);
            switch (cmd) {
                case "get_ques": {
                    int questionId = dis.readInt();
                    Question q = questionsData[questionId];
                    dos.writeUTF(q.getContent());
                    dos.writeUTF(q.getOptionA());
                    dos.writeUTF(q.getOptionB());
                    dos.writeUTF(q.getOptionC());
                    dos.writeUTF(q.getOptionD());
                    String ans = savedAnswers[questionId];
                    dos.writeUTF(ans == null ? "" : ans);
                    break;
                }
                case "commit_ques": {
                    int questionId = dis.readInt();
                    String ans = dis.readUTF();
                    savedAnswers[questionId] = ans;
//                    System.out.println("Luu ket qua cau " + questionId + " la: " + ans);
                    break;
                }
                case "commit_all": {
                    int mark = 0;
                    for (int i = 0; i < QUEST_NUM; i++) {
                        if (savedAnswers[i] != null && savedAnswers[i].equals(questionsData[i].getAnswer())) {
                            mark += 10;
                        }
                    }
                    dos.writeInt(mark);
//                    System.out.println("========Xem ket qua========");
//                    for (int i = 0; i < QUEST_NUM; i++) {
//                        String ans = savedAnswers[i] == null ? "" : savedAnswers[i];
//                        System.out.println(String.format("Question %d: (%s, %s) => %s", i+1, questionsData[i].getAnswer(), ans, questionsData[i].getAnswer().equals(ans)));
//                    }
                    
                    String studentId = dis.readUTF();
                    String query = String.format("INSERT dbo.BANGDIEM (MASV,LAN,NGAYTHI,DIEM,BAITHI) VALUES (N'%s', 1, GETDATE(), %d, N'Olympia')", studentId, mark);
                    DataProvider.getInstance().executeNonQuery(query);
                    return;
                }
            }
        }
    }

}
