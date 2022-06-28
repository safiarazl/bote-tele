package safiar.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import java.sql.Statement;

public class botClassTA extends TelegramLongPollingBot {

    Connection Con;
    ResultSet RsBrg;
    Statement stm;
    Boolean edit = false;

    private Object[][] dataTable = null;
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
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        SendMessage message=new SendMessage();
        SendMessage message1=new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        String daftar = "FORM DAFTAR\nsaya setuju mendaftar.\nkirim ulang pesan ini untuk mendaftar";
        Update msg = update;
        System.out.println("ini isi nilai msg: " + msg);

        //______________________________________________Button Menu_________________________________________//
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("start");
        row.add("daftar");
        row.add("about");
        row.add("developer");
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        message.setReplyMarkup(replyKeyboardMarkup);
        //______________________________________________Button Menu_________________________________________//

        //______________________________________________Command_________________________________________//
        String command;
        command = update.getMessage().getText();
//        System.out.println("ini ISINILAI" + update.getChatMember().getChat().getFirstName());
        System.out.println("ini isi nilai command: " + command);
        switch (command) {
            case "dian" -> {
                //lopping kirim by id chat
                for (int i = 0; i < 3; i++) {
                    try {
                        message1.setChatId(cbID().get(i));
                        message1.setText("Hai, ini dia yang dian");
                        execute(message1);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "start" -> {
                System.out.println(update.getMessage() + "BEEBOoo.. 0o0 Halo Nama Saya BotTele_Rahadian-0.1 ");
                message.setText("BEEBOoo.. 0o0 Halo Nama Saya BotTele_Rahadian-0.1 🤖");
            }
            case "daftar" -> {
                message.setText(daftar);
            }
            case "about" -> {
                System.out.println(update.getMessage() + "Saya adalah bot 🤖 yang dibuat oleh Rahadian Kristiyanto 👨�?💻 Untuk Memenuhi Tugas Praktikum Akhir Mata Kuliah Pemrograman Berbasi Objek");
                message.setText("Saya adalah bot 🤖 yang dibuat oleh Rahadian Kristiyanto 👨�?💻 Untuk Memenuhi Tugas Praktikum Akhir Mata Kuliah Pemrograman Berbasi Objek");
            }
            case "developer" -> {
                System.out.println(update.getMessage() + "Saya dibuat oleh : " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() + " NIM : A11.2020.12724");
                message.setText("Saya dibuat oleh : " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() + " NIM : A11.2020.12724");
            }
            default -> {
                System.out.println(update.getMessage() + "Saya tidak mengerti perintah yang anda tulis");
                message.setText("Saya tidak mengerti perintah yang anda tulis");
            }
        }

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
//                msg.getChatMember();
                msg.getMessage().getChatId();
                message.setChatId(msg.getMessage().getChatId());
                message.setText(pesan);
            } catch (Exception e) {
                System.out.println("Pesan gagal dikirim: " + e);
            }
        }


//      JANGAN DIHAPUS/KOMEN UNTUK NGIRIM PESAN; SETTEXT UNTUK SETUP PESAN
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Pesan gagal dikirim: " + e);
            System.out.println("Error botClass 2#: " + e);
        }
    }


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

    public ArrayList<Long> cbID(){
        try{
            stm=Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_FORWARD_ONLY);
            ResultSet rs=stm.executeQuery("select id from user");
            rs.beforeFirst();
            //get array of id
            ArrayList<Long> id = new ArrayList<>();
            while(rs.next()){
                id.add(rs.getLong("id"));
            }
            return id;
//            while(rs.next()){
////                System.out.println(rs.getString(1));
//                return rs.getLong(1);
//            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        return null;
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
        String nama = data[0], id = data[1];
        try{
            if(edit==true){
                System.out.println("masuk update botClass");
                stm.executeUpdate("update user set nama='"+nama+"',id='"+id+"' where id='" + id + "'");
            } else {
                System.out.println("masuk insert botClass");
                stm.executeUpdate("INSERT into user VALUES('"+nama+"','"+id+"')");
            }
        } catch(SQLException e){
            System.out.println("Error botClass 1#: " + e);
        }
    }

}
