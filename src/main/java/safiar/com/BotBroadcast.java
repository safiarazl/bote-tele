package safiar.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BotBroadcast  extends TelegramLongPollingBot {


    SendMessage message=new SendMessage();
    //kirim pesan ke semua member
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
        //ambil history pesan

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            String user_name = update.getMessage().getChat().getFirstName();
            System.out.println("ini isi nilai user_id: " + user_name);
            System.out.println("ini isi nilai message_text: " + message_text);
            formBroadcast.taHistory.append(user_name + ": " + message_text + "\n");
        }
    }
}
