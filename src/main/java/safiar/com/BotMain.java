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


public class BotMain extends TelegramLongPollingBot {

    Statement stm;
    Boolean edit = false;
    SendMessage message=new SendMessage();

    //Fungsi Chat
    public void kirimPesan(String id, String pesan) {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(pesan);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            if (id=="") {
                javax.swing.JOptionPane.showMessageDialog(null, "Pilih ID");
            } if (pesan=="") {
                javax.swing.JOptionPane.showMessageDialog(null, "Pesan Masih Kosong");
            }else {
                javax.swing.JOptionPane.showMessageDialog(null, "Gagal mengirim pesan");
            }
            e.printStackTrace();
        }
    }


    //Fungsi Broadcsast
    public void sendPesanBroadcast(String pesan){
        for (int i = 0; i < (cbID().size()); i++) {
            try {
                message.setChatId(cbID().get(i));
                message.setText(pesan);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    //get cbID
    public ArrayList<Long> cbID(){
        ArrayList<Long> cbID = new ArrayList<Long>();
        try {
            KoneksiMysql kon = new KoneksiMysql("localhost","root","","bot-tele");
            Connection Con = kon.getConnection();
            Statement stm = Con.createStatement();
            ResultSet RsBrg = stm.executeQuery("SELECT * FROM user");
            while(RsBrg.next()){
                cbID.add(RsBrg.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cbID;
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

    //cek member
    public boolean cekMember(int id){
        boolean hasilMember = false;
        int i = 0;
        while(i < cbID().size()){
            if(id == cbID().get(i)){
                hasilMember = true;
            }
        }
        return hasilMember;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            String user_name = update.getMessage().getChat().getFirstName();
            formAdmin.taHistory.append(user_name + ": " + message_text + "\n");
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
                case "start" -> {
                    String pesan = "BEEBOoo.. 0o0 Halo Nama Saya BotTele_Rahadian-0.1 ðŸ¤–";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                }
                case "daftar" -> {
                    message.setText(daftar);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + daftar + "\n" );
                }
                case "about" -> {
                    String pesan = "Saya adalah bot ðŸ¤– yang dibuat oleh Rahadian Kristiyanto ðŸ‘¨â€?ðŸ’» Untuk Memenuhi Tugas Praktikum Akhir Mata Kuliah Pemrograman Berbasi Objek";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                }
                case "developer" -> {
                    String pesan = "Saya dibuat oleh : " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() + " NIM : A11.2020.12724";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                }
                case "FORM DAFTAR\nsaya setuju mendaftar.\nkirim ulang pesan ini untuk mendaftar" -> {
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
                        formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                        msg.getMessage().getChatId();
                        message.setChatId(msg.getMessage().getChatId());
                        message.setText(pesan);
                    } catch (Exception e) {
                        System.out.println("Pesan gagal dikirim: " + e);
                    }
                }
//                default -> {
//                    System.out.println(update.getMessage() + "Saya tidak mengerti perintah yang anda tulis");
//                    message.setText("Saya tidak mengerti perintah yang anda tulis");
//                }
            }



//      JANGAN DIHAPUS/KOMEN UNTUK NGIRIM PESAN; SETTEXT UNTUK SETUP PESAN
            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Pesan gagal dikirim: " + e);
                System.out.println("Error botClass 2#: " + e);
            }
        }
        }
    }

