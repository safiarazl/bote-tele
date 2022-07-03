/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package safiar.com;

import org.apache.log4j.BasicConfigurator;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import javax.swing.JTextArea;

import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import static java.util.logging.Level.ALL;
import java.util.logging.Logger;

/**
 *
 * @author Safiar
 */
public class formChatBot extends javax.swing.JFrame {

    Connection Con;
    ResultSet RsBrg;
    Statement stm;

    /**
     * Creates new form formChatBot
     */
    public formChatBot() {
        initComponents();
        cekKoneksi();
        open_db();
        pilih_user();
    }

    public void cekKoneksi(){
        try{
            URL url = new URL("https://google.com");
            URLConnection conn= url.openConnection();
            conn.connect();
            lbStstus.setText("Status : Terhubung");
        }catch(Exception e){
            lbStstus.setText("Status : Terputus");
        }
    }

    private void open_db() {
        try{
            KoneksiMysql kon = new KoneksiMysql("localhost","root","","bot-tele");
            Con = kon.getConnection();
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
    }

    //pilih user by id
    private void pilih_user() {
        try {
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            RsBrg = stm.executeQuery("select * from user");
            RsBrg.beforeFirst();
            while (RsBrg.next()) {
                cmbUser.addItem(RsBrg.getString("name"));
            }
            RsBrg.close();
        } catch (SQLException e) {
            System.out.println("Error : " + e);
        }
    }

    //user terpilih
    private void user_terpilih(String xid) {
        try {
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            RsBrg = stm.executeQuery("select * from user where name = '"+xid+"'");
            RsBrg.beforeFirst();
            while (RsBrg.next()) {
                txtUserID.setText(RsBrg.getString("id"));
            }
            RsBrg.close();
        } catch (SQLException e) {
            System.out.println("Error : " + e);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbUser = new javax.swing.JComboBox<>();
        txtUserID = new javax.swing.JLabel();
        txtPesan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lbStstus = new javax.swing.JLabel();
        bKirim = new javax.swing.JButton();
        bBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kirim pesan private");

        jLabel1.setFont(new java.awt.Font("Colonna MT", 1, 24)); // NOI18N
        jLabel1.setText("Bot Chat");

        cmbUser.setFont(new java.awt.Font("Colonna MT", 0, 14)); // NOI18N
        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih User-" }));
        cmbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserActionPerformed(evt);
            }
        });

        txtUserID.setFont(new java.awt.Font("Colonna MT", 0, 14)); // NOI18N
        txtUserID.setText("User ID");

        txtPesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Colonna MT", 0, 14)); // NOI18N
        jLabel3.setText("Pesan");

        lbStstus.setFont(new java.awt.Font("Colonna MT", 0, 14)); // NOI18N
        lbStstus.setText("Status :");

        bKirim.setBackground(new java.awt.Color(0, 255, 0));
        bKirim.setFont(new java.awt.Font("Colonna MT", 0, 12)); // NOI18N
        bKirim.setForeground(new java.awt.Color(0, 0, 0));
        bKirim.setText("Kirim");
        bKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKirimActionPerformed(evt);
            }
        });

        bBatal.setBackground(new java.awt.Color(255, 102, 102));
        bBatal.setFont(new java.awt.Font("Colonna MT", 0, 12)); // NOI18N
        bBatal.setForeground(new java.awt.Color(255, 255, 255));
        bBatal.setText("Batal");
        bBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bBatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bKirim))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(txtPesan, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStstus)
                            .addComponent(txtUserID))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserID)
                    .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbStstus))
                .addGap(1, 1, 1)
                .addComponent(txtPesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bKirim)
                    .addComponent(bBatal))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserActionPerformed
        // TODO add your handling code here:
        user_terpilih(cmbUser.getSelectedItem().toString());
    }//GEN-LAST:event_cmbUserActionPerformed

    private void txtPesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesanActionPerformed

    private void bKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKirimActionPerformed
        BotMain kirim = new BotMain();
        kirim.kirimPesan(txtUserID.getText(), txtPesan.getText());
        formAdmin.taHistory.append(kirim.getBotUsername()+ " : " + txtPesan.getText()+ "\n" );
        txtPesan.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_bKirimActionPerformed

    private void bBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatalActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_bBatalActionPerformed

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
            java.util.logging.Logger.getLogger(formChatBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formChatBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formChatBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formChatBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formChatBot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBatal;
    private javax.swing.JButton bKirim;
    private javax.swing.JComboBox<String> cmbUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbStstus;
    private javax.swing.JTextField txtPesan;
    private javax.swing.JLabel txtUserID;
    // End of variables declaration//GEN-END:variables
}
