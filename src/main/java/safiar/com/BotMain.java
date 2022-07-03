package safiar.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.util.ArrayList;


public class BotMain extends TelegramLongPollingBot {

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
        }
    }
}
