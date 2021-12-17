package multithread.example.multiserver.exam;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class ExamForm extends javax.swing.JFrame {

    private static final int QUEST_NUM = 20;
    private static final int MIN_NUM = 1;

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    private ButtonGroup btnGroup;
    private JRadioButton[] rbtOption;

    public ExamForm() throws IOException {
        initComponents();
        client = new Socket("localhost", 8888);
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
        setLocationRelativeTo(null);
        load();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txfTimeRemain = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstQuestions = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaContent = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        rbtOptionA = new javax.swing.JRadioButton();
        rbtOptionC = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaOptionA = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaOptionC = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txaOptionB = new javax.swing.JTextArea();
        rbtOptionB = new javax.swing.JRadioButton();
        rbtOptionD = new javax.swing.JRadioButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        txaOptionD = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txfStudentId = new javax.swing.JTextField();
        txfStudentName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Thời gian còn lại:");

        txfTimeRemain.setText("jTextField1");

        lstQuestions.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lstQuestions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Câu 1", "Câu 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstQuestions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lstQuestionsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(lstQuestions);

        jLabel2.setText("Câu hỏi:");

        jLabel3.setText("Đề bài:");

        txaContent.setEditable(false);
        txaContent.setColumns(20);
        txaContent.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaContent.setRows(5);
        jScrollPane2.setViewportView(txaContent);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Câu trả lời"));

        rbtOptionA.setText("A");

        rbtOptionC.setText("C");

        txaOptionA.setEditable(false);
        txaOptionA.setColumns(20);
        txaOptionA.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaOptionA.setRows(5);
        jScrollPane3.setViewportView(txaOptionA);

        txaOptionC.setEditable(false);
        txaOptionC.setColumns(20);
        txaOptionC.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaOptionC.setRows(5);
        jScrollPane4.setViewportView(txaOptionC);

        txaOptionB.setEditable(false);
        txaOptionB.setColumns(20);
        txaOptionB.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaOptionB.setRows(5);
        jScrollPane5.setViewportView(txaOptionB);

        rbtOptionB.setText("B");

        rbtOptionD.setText("D");

        txaOptionD.setEditable(false);
        txaOptionD.setColumns(20);
        txaOptionD.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaOptionD.setRows(5);
        jScrollPane6.setViewportView(txaOptionD);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOptionA)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOptionC)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOptionB)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOptionD)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtOptionB)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbtOptionD)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtOptionA)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbtOptionC)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())))))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("BẮT ĐẦU THI");

        jLabel5.setText("Mã SV:");

        txfStudentId.setEditable(false);
        txfStudentId.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfStudentId.setText("jTextField1");

        txfStudentName.setEditable(false);
        txfStudentName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfStudentName.setText("jTextField1");

        jLabel6.setText("Họ tên:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(445, 445, 445))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txfTimeRemain, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfStudentName)
                            .addComponent(txfStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txfStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txfStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txfTimeRemain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadQuestionListView() {
        String[] questions = new String[QUEST_NUM];
        for (int i = 0; i < QUEST_NUM; i++) {
            questions[i] = "Câu " + (i + 1);
        }
        lstQuestions.setListData(questions);
        lstQuestions.setSelectedIndex(0);
    }

    private void loadTimeCountDown() {
        Instant firstTime = Instant.now();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Instant secondTime = Instant.now();
                    long timeElapsedSecond = Duration.between(firstTime, secondTime).getSeconds();
                    long remainTime = MIN_NUM * 60 - timeElapsedSecond;
                    long remainMin = remainTime / 60;
                    long remainSec = remainTime % 60;
                    txfTimeRemain.setText(String.format("%02d:%02d", remainMin, remainSec));
                    if (remainMin == 0 && remainSec == 0) {
                        JOptionPane.showMessageDialog(null, "Đã hết giờ làm bài");
                        Thread.currentThread().isInterrupted();
                        commitExam();
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    private void load() {
        loadQuestionListView();

        btnGroup = new ButtonGroup();

        rbtOption = new JRadioButton[]{rbtOptionA, rbtOptionB, rbtOptionC, rbtOptionD};
        for (JRadioButton rbt : rbtOption) {
            btnGroup.add(rbt);
            rbt.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String ans = ((JRadioButton) e.getSource()).getText();
                    try {
                        dos.writeUTF("commit_ques");
                        dos.writeInt(lstQuestions.getSelectedIndex());
                        dos.writeUTF(ans);
                    } catch (IOException ex) {
                        Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        try {
            showQuestionByIndex(0);
        } catch (IOException ex) {
            Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTimeCountDown();
    }

    private void showQuestionByIndex(int idx) throws IOException {
        dos.writeUTF("get_ques");
        dos.writeInt(idx);
        txaContent.setText(dis.readUTF());
        txaOptionA.setText(dis.readUTF());
        txaOptionB.setText(dis.readUTF());
        txaOptionC.setText(dis.readUTF());
        txaOptionD.setText(dis.readUTF());
        String oldAnswer = dis.readUTF();
        btnGroup.clearSelection();
        if (!oldAnswer.isEmpty()) {
            for (JRadioButton rbt : rbtOption) {
                if (rbt.getText().equals(oldAnswer)) {
                    btnGroup.setSelected(rbt.getModel(), true);
                }
            }
        }
    }

    private void commitExam() {
        try {
            ExamResultForm dialogExamResult = new ExamResultForm();
            dos.writeUTF("commit_all");
            dos.writeUTF(txfStudentId.getText());
            int mark = dis.readInt();
            dialogExamResult.showResult(txfStudentId.getText(), txfStudentName.getText(), mark);
            dialogExamResult.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            ServerTCP.getInstance().close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStudentInfo(String studentId, String studentName) {
        txfStudentId.setText(studentId);
        txfStudentName.setText(studentName);
    }

    private void lstQuestionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstQuestionsMousePressed
        int ind = lstQuestions.getSelectedIndex();
        try {
            showQuestionByIndex(ind);
        } catch (IOException ex) {
            Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lstQuestionsMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ExamForm().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ExamForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JList<String> lstQuestions;
    private javax.swing.JRadioButton rbtOptionA;
    private javax.swing.JRadioButton rbtOptionB;
    private javax.swing.JRadioButton rbtOptionC;
    private javax.swing.JRadioButton rbtOptionD;
    private javax.swing.JTextArea txaContent;
    private javax.swing.JTextArea txaOptionA;
    private javax.swing.JTextArea txaOptionB;
    private javax.swing.JTextArea txaOptionC;
    private javax.swing.JTextArea txaOptionD;
    private javax.swing.JTextField txfStudentId;
    private javax.swing.JTextField txfStudentName;
    private javax.swing.JTextField txfTimeRemain;
    // End of variables declaration//GEN-END:variables
}
