package util;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;

public class FileUtil {

    public static void showJFileChooser() {
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // txfDirTo.setText(file.getAbsolutePath());
            
//            String path = selectedFile.getAbsolutePath();
//            byte[] allBytes = Files.readAllBytes(Paths.get(path));
//            String allText = new String(allBytes, StandardCharsets.UTF_8);
//            areaVanBan.setText(allText);
        }
    }
    
    public static String writeFile(File fileFrom, File fileTo) throws IOException {
        StringBuilder sb = new StringBuilder();
        // int lineNth = 0;

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(fileFrom.toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // fileTask(sb, line);
            }
        } catch (IOException ex) {
            return "Xử lý file không thành công!";
        }

        // Ghi ket qua
        try (BufferedWriter writer = Files.newBufferedWriter(fileTo.toPath(), charset)) {
            writer.write(sb.toString());
        } catch (IOException ex) {
            return "Ghi file không thành công!";
        }

        return "Ghi file thành công";
    }
    
}
