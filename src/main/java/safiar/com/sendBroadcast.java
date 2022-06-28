/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package safiar.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Safiar
 */
public class sendBroadcast extends TelegramLongPollingBot{
    Connection Con;
    ResultSet RsBrg;
    Statement stm;
    static String pesanBroadcast;
    SendMessage message = new SendMessage();

    public void sendPesanBroadcast(String pesan){
        for (int i = 0; i < (cbID().size()-1); i++) {
            try {
                message.setChatId(cbID().get(i));
                message.setText(formBroadcast.taPesan.getText());
                execute(message);
            } catch (TelegramApiException e) {
                formBroadcast.taPesan.setText(String.valueOf(e));
                e.printStackTrace();
            }
        }
    }
    @Override
    public String getBotToken() {
        return "5415553203:AAHKr1s1aTT2B06zmUpNLvndrZaO4mRuZa4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String command;
        command = update.getMessage().getText();
//        System.out.println("ini ISINILAI" + update.getChatMember().getChat().getFirstName());
        System.out.println("ini isi nilai command: " + command);
                //lopping kirim by id chat
    }

    @Override
    public String getBotUsername() {
        return "vallmy_bot";
    }

    public sendBroadcast() {
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
            System.out.println(id);
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

    public void main(String[] args) {
        System.out.println(this.cbID());
    }

}
