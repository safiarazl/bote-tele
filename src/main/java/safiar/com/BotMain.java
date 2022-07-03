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
    ResultSet RsBrg;
    private Object[][] dataTable = null;
    SendMessage message=new SendMessage();
    KoneksiMysql kon = new KoneksiMysql("bot-tele");
    Connection Con = kon.getConnection();



    public BotMain(){
        System.out.println("ini isi baca data " + cbID());
        System.out.println("ini isi baca data " + cekMember(1096439751));
//        String [] data = {
//                "Safiar",
//                "931580932"
//        };
//        simpan(data);
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
            System.out.println("masuk insert botClass");
            String query = "INSERT into user VALUES('"+nama+"','"+id+"')";
            stm = Con.createStatement();
            stm.executeUpdate(query);
            baca_data();

        } catch(SQLException e){
            System.out.println("Error botClass 1#: " + e);
        }
    }

    //cek member
    public boolean cekMember(long id){
        final boolean[] hasilMember = {false};
        int i = 0;
        ArrayList p = cbID();
        p.forEach(o -> {
            if(o.equals(id)){
                System.out.println("proses: " + o + "///" + id);
                hasilMember[0] = true;
            }
        });
        return hasilMember[0];
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
        String daftar = "FORM DAFTAR\nsaya setuju mendaftar.\nkirim ulang pesan ini untuk mendaftar";
        //______________________________________________Button Menu_________________________________________//
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
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
        String command;
        command = update.getMessage().getText().toLowerCase();
        message.setChatId(update.getMessage().getChatId());
        String message_text = update.getMessage().getText();
        String user_name = update.getMessage().getChat().getFirstName();
        formAdmin.taHistory.append(user_name + ": " + message_text + "\n");
        if (update.hasMessage() && update.getMessage().hasText() && cekMember(update.getMessage().getFrom().getId())) {
            SendMessage message=new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            //______________________________________________Command_________________________________________//
            System.out.println("ini isi nilai command: " + command);
            switch (command) {
                case "start" -> {
                    String pesan = "HALO SAYA BOT UNTUK TUGAS AKHIR PBO";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername() + " : " + pesan + "\n");
                }
                case "about" -> {
                    String pesan = "Saya dibuat untuk:\nmemenuhi kebutuhan TA PBO\nuntuk kontak developer bisa klik perintah developer";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                }
                case "developer" -> {
                    String pesan = "Kontak developer\n\nSaya dibuat oleh :\nSafiar Azalia Amanullah\nA11.2020.12715";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                }
                case "daftar" -> {
                    String pesan = "Anda sudah terdaftar menjadi member kami";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                }
                default -> {
                    String pesan = "Saya tidak paham maksud anda";
                    System.out.println(update.getMessage() + pesan);
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername() + " : " + pesan + "\n");
                }
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Pesan gagal dikirim: " + e);
                System.out.println("Error botClass 2#: " + e);
            }
        } else {
            switch (command) {
                case "daftar" -> {
                    message.setText(daftar);
                    formAdmin.taHistory.append(getBotUsername()+ " : " + daftar + "\n" );
                }
                case "FORM DAFTAR\nsaya setuju mendaftar.\nkirim ulang pesan ini untuk mendaftar" -> {
                        String nama = update.getMessage().getFrom().getFirstName();
                        String id = update.getMessage().getFrom().getId().toString();
                        String [] data = {
                                nama,
                                id
                        };
                        simpan(data);
                        String pesan = "Terimakasih telah mendaftar ";
                        formAdmin.taHistory.append(getBotUsername()+ " : " + pesan + "\n" );
                        message.setText(pesan);
                }
                default -> {
                    String pesan = "daftar dulu";
                    message.setText(pesan);
                    formAdmin.taHistory.append(getBotUsername() + " : " + pesan + "\n");
                }
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Pesan gagal dikirim: " + e);
                System.out.println("Error botClass 2#: " + e);
            }
        }
        }
    }

