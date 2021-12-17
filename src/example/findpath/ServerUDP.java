package example.findpath;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUDP {

    private static volatile ServerUDP uniqueInstance;

    private DatagramSocket serverSocket;
    private byte[] inData;
    private byte[] outData;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;

    private static int minSum = Integer.MAX_VALUE;
    
    // Dùng saveVisited cho TH chỉ cần tìm 1 đường đi tối ưu
//    private static boolean[][] saveVisited;
    private static boolean[][] visited;
    
    // Sửa lại
    // Dùng resultPaths cho TH tìm tất cả đường đi tối ưu
    private static List<boolean[][]> resultPaths;

    private ServerUDP() {
        try {
            serverSocket = new DatagramSocket(8888);
            System.out.println("Server đang chạy.");
            inData = new byte[1024];
            outData = new byte[1024];
            inPacket = new DatagramPacket(inData, inData.length);
            outPacket = new DatagramPacket(outData, outData.length);
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ServerUDP getInstance() {
        if (uniqueInstance == null) {
            synchronized (ServerUDP.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ServerUDP();
                }
            }
        }
        return uniqueInstance;
    }

    public void close() {
        serverSocket.close();
    }

    private int readInt() throws IOException {
        serverSocket.receive(inPacket);
        return Integer.parseInt(new String(inPacket.getData(), 0, inPacket.getLength()).trim());
    }

    private String readUTF() throws IOException {
        serverSocket.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength()).trim();
    }

    private void write(String content) throws IOException {
        outData = content.getBytes();
        outPacket.setData(outData);
        outPacket.setLength(outData.length);
        outPacket.setAddress(inPacket.getAddress());
        outPacket.setPort(inPacket.getPort());
        serverSocket.send(outPacket);
    }

    private static void addToResultPaths(boolean[][] pathVisit) {
        int nr = pathVisit.length;
        int nc = pathVisit[0].length;
        boolean[][] added = new boolean[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                added[i][j] = pathVisit[i][j];
            }
        }
        resultPaths.add(added);
    }

    private static void computeAndSave(int sum) {
        if (sum < minSum) {
            minSum = sum;
            resultPaths.clear();
            addToResultPaths(visited);
        } else if (sum == minSum) {
            addToResultPaths(visited);
        }
    }

    private static void trav(int[][] mat, int nr, int nc, int i, int j, int sum) {
        if (i + 1 >= nr || j + 1 >= nc) {
            return;
        }

        if (j + 1 < nc) {
            sum += mat[i][j + 1];
            visited[i][j + 1] = true;
        }
        if (i == nr - 1 && j + 1 == nc - 1) {
            computeAndSave(sum);
        } else {
            trav(mat, nr, nc, i, j + 1, sum);
            visited[i][j + 1] = false;
            sum -= mat[i][j + 1];
        }

        if (i + 1 < nr && j + 1 < nc) {
            sum += mat[i + 1][j + 1];
            visited[i + 1][j + 1] = true;
        }
        if (i + 1 == nr - 1 && j + 1 == nc - 1) {
            computeAndSave(sum);
        } else {
            trav(mat, nr, nc, i + 1, j + 1, sum);
            visited[i + 1][j + 1] = false;
            sum -= mat[i + 1][j + 1];
        }

        if (i + 1 < nr) {
            sum += mat[i + 1][j];
            visited[i + 1][j] = true;
        }
        if (i + 1 == nr - 1 && j == nc - 1) {
            computeAndSave(sum);
        } else {
            trav(mat, nr, nc, i + 1, j, sum);
            visited[i + 1][j] = false;
            sum -= mat[i + 1][j];
        }
    }

    private static void process(int[][] mat) {
        int nr = mat.length;
        int nc = mat[0].length;
        visited = new boolean[nr][nc];
//        saveVisited = new boolean[nr][nc];
        resultPaths = new ArrayList<>();
//        for (int i = 0; i < nr; i++) {
//            for (int j = 0; j < nc; j++) {
//                saveVisited[i][j] = visited[i][j] = false;
//            }
//        }
        minSum = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        visited[0][0] = true;
        trav(mat, nr, nc, i, j, mat[0][0]);
    }

    public void start() throws IOException {
        while (true) {
            String cmd = readUTF();
            System.out.println("CMD tai server: " + cmd);
            switch (cmd) {
                case "login": {
                    String username = readUTF();
                    String pass = readUTF();
                    ResultSet rs = DataProvider.getInstance().executeQuery(String.format("SELECT * FROM dbo.TAIKHOAN WHERE USERNAME = '%s' AND PASS = '%s'", username, pass));
                    try {
                        if (rs.next()) {
                            write("ok");
                        } else {
                            write("");
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case "create_login": {
                    String username = readUTF();
                    String pass = readUTF();
                    int res = DataProvider.getInstance().executeNonQuery(String.format("INSERT dbo.TAIKHOAN (USERNAME, PASS) VALUES ('%s', '%s')", username, pass));
                    if (res > 0) {
                        write("ok");
                    } else {
                        write("");
                    }
                    break;
                }
                case "solve": {
                    String fileDir = readUTF();
                    File file = new File(fileDir);
                    if (!file.exists()) {
                        write("Tập tin chọn không tồn tại hoặc đã bị xóa sau khi chọn");
                        return;
                    }
                    int lineNth = 0;
                    ArrayList<ArrayList<Integer>> list = new ArrayList<>();
                    Charset charset = Charset.forName("US-ASCII");
                    try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            ArrayList<Integer> newRow = new ArrayList<Integer>();
                            String[] strs = line.split("\\s+");
                            for (int i = 0; i < strs.length; i++) {
                                try {
                                    newRow.add(Integer.parseInt(strs[i]));
                                } catch (NumberFormatException ex) {
                                    write(String.format("Xảy ra lỗi do định dạng sai kiểu số ở phần tử tại dòng %d", lineNth));
                                    return;
                                }
                            }
                            lineNth++;
                            list.add(newRow);
                        }
                    } catch (IOException ex) {
                        write(String.format("Xảy ra lỗi khi đọc file tại dòng %d", lineNth));
                        return;
                    }
                    int nr = list.size();
                    int nc = list.get(0).size();
                    // DEBUG
                    for (int i = 0; i < nr; i++) {
                        for (int j = 0; j < nc; j++) {
                            System.out.print(list.get(i).get(j) + ", ");
                        }
                        System.out.println("");
                    }
                    int[][] mat = new int[nr][nc];
                    for (int i = 0; i < nr; i++) {
                        for (int j = 0; j < nc; j++) {
                            mat[i][j] = list.get(i).get(j);
                        }
                    }

                    // PROCESSING
                    process(mat);

                    // PRINT RESULT
                    String msg = "Trọng số đường đi ngắn nhất: " + minSum + "\nCó " + resultPaths.size() + " đường đi thõa mãn:\n";
                    int idxPath = 1;
                    for (boolean[][] mark : resultPaths) {
                        boolean first = true;
                        msg += idxPath + "/ ";
                        for (int i = 0; i < nr; i++) {
                            for (int j = 0; j < nc; j++) {
                                if (mark[i][j]) {
                                    if (!first) {
                                        msg += ", ";
                                    }
                                    msg += ("(" + i + ", " + j + ", " + mat[i][j] + ")");
                                    first = false;
                                }
                            }
                        }
                        msg += "\n";
                        idxPath++;
                    }

                    write(msg);
                    break;
                }
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        int[][] mat = new int[][]{
            {12, 1, 6, 2},
            {18, 22, 3, 5},
            {54, 19, 4, 81},
            {7, 16, 24, 68}

        };
        int nr = mat.length;
        int nc = mat[0].length;
        process(mat);
//        String msg = "Trọng số đường đi ngắn nhất: " + minSum + "\nĐường đi: ";
//        for (int i = 0; i < saveVisited.length; i++) {
//            for (int j = 0; j < saveVisited[0].length; j++) {
//                if (saveVisited[i][j]) {
//                    msg += mat[i][j] + ", ";
//                }
//            }
//        }
//        System.out.println(msg);
    }

}
