package safiar.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.sql.*;
//import java.sql.Statement;

public class botClassTA extends TelegramLongPollingBot {
    
    Connection Con;
    ResultSet RsBrg;
    Statement stm;
    Boolean edit = false;
    private Object[][] dataTable = null;
    SendMessage message=new SendMessage();
    
    public botClassTA() {
       try{
           KoneksiMysql kon = new KoneksiMysql("localhost","root","","bot-tele"); 
           Con = kon.getConnection();
           stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           RsBrg = stm.executeQuery("select * from user");
       } catch (Exception e){
           System.out.println("Error botClass : " + e);
       }
    }
    
    public Object baca_data(){
        try {
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            RsBrg = stm.executeQuery("select * from user");

            ResultSetMetaData meta;
            meta = RsBrg.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            while (RsBrg.next()) {
                baris = RsBrg.getRow();
            }

            dataTable = new Object[baris][col];
            int x = 0;
            RsBrg.beforeFirst();
            while (RsBrg.next()) {
                dataTable[x][0] = RsBrg.getString("nama");
                dataTable[x][1] = RsBrg.getString("id");
                x++;
            }
            return dataTable;
        } catch (SQLException e) {
            System.out.println(e);
            return e;
            
        }
//        return null;
        
    }
    
    public void simpan(String [] data) {
        String nama = data[0], id = data[0];
        try{
            if(edit==true){
                System.out.println("masuk update botClass");
                stm.executeUpdate("update user set nama='"+data[0]+"',id='"+data[1]+"' where id='" + data[1] + "'");
            } else {
                System.out.println("masuk insert botClass");
                String idmsg;
                stm.executeUpdate("INSERT into user VALUES('"+data[0]+"','"+data[1]+"')");
            }
        } catch(SQLException e){
            System.out.println("Error botClass 1#: " + e);
        }
    }
    
    @Override
    public String getBotUsername() {
        return "vallmy_bot";
    }

    @Override
    public String getBotToken() {
        return "5415553203:AAHKr1s1aTT2B06zmUpNLvndrZaO4mRuZa4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        String command;
        String daftar = "FORM DAFTAR\nsaya setuju mendaftar.\nkirim ulang pesan ini untuk mendaftar";
        try {
            command = update.getMessage().getText();
            Update msg = update;
            System.out.println("ini isi nilai msg: " + msg);
            switch (command) {
                case "/start" -> {
                    System.out.println(update.getMessage() + "BEEBOoo.. 0o0 Halo Nama Saya BotTele_Rahadian-0.1 ");
                    message.setText("BEEBOoo.. 0o0 Halo Nama Saya BotTele_Rahadian-0.1 🤖");
                }
                case "/daftar" -> {
                    message.setText(daftar);
                }
                case "/about" -> {
                    System.out.println(update.getMessage()+"Saya adalah bot 🤖 yang dibuat oleh Rahadian Kristiyanto 👨�?💻 Untuk Memenuhi Tugas Praktikum Akhir Mata Kuliah Pemrograman Berbasi Objek");
                    message.setText("Saya adalah bot 🤖 yang dibuat oleh Rahadian Kristiyanto 👨�?💻 Untuk Memenuhi Tugas Praktikum Akhir Mata Kuliah Pemrograman Berbasi Objek");
                }
                case "/developer" -> {
                    System.out.println(update.getMessage() + "Saya dibuat oleh : " + update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName()+" NIM : A11.2020.12724");
                    message.setText("Saya dibuat oleh : " + update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName()+" NIM : A11.2020.12724");
                }
                
            }
//            message.setText(command.substring(0, 11));
//          UNTUK DAFTAR MEMBER
            if(msg.getMessage().getText().equals(daftar)){
                try {
                    String nama = msg.getMessage().getFrom().getFirstName();
                    String id = msg.getMessage().getFrom().getId().toString();
                    String [] data = {
                        nama,
                        id
                    };
                    simpan(data);
                    String pesan = "Terimakasih telah mendaftar ";
                    msg.getMessage().getChatId();
                    message.setChatId(msg.getMessage().getChatId());
                    message.setText(pesan);
                } catch (Exception e) {
                    System.out.println("Pesan gagal dikirim: " + e);
                }
                
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("Error botClass 1#: " + e);
        }
        
//      JANGAN DIHAPUS/KOMEN UNTUK NGIRIM PESAN; SETTEXT UNTUK SETUP PESAN
        message.setChatId(update.getMessage().getChatId());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Error botClass 2#: " + e);
        }
    }
}
