package safiar.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotChat extends TelegramLongPollingBot {

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
            formChatBot.txtChat.append(user_name + ": " + message_text + "\n");
        }
    }
}
