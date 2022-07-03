/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package safiar.com;

import java.sql.*;
//import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Safiar
 */
public class crudDatabase {
    Connection Con;
    ResultSet RsBrg;
    Statement stm;
    Boolean edit = false;
    private Object[][] dataTable = null;
    
    /**
     *
     */
    public void open_db() {
       try{
           KoneksiMysql kon = new KoneksiMysql("localhost","root","","bot-tele"); 
           Con = kon.getConnection();
       } catch (Exception e){
           System.out.println("Error : " + e);
       }
    }
    
    public void simpan(String [] data) {
//        System.out.println("hasil void simpan: " + data[0] + " dan " + data[1]);
        String nama = data[0], id = data[0];
        try{
            if(edit==true){
                System.out.println("masuk update");
                stm.executeUpdate("update user set nama='"+data[0]+"',id='"+data[1]+"' where id='" + data[1] + "'");
            } else {
                System.out.println("masuk insert");
//                stm.executeQuery("INSERT into user VALUES('"+data[0]+"','"+data[1]+"')");
                stm.executeUpdate("INSERT into user VALUES('"+data[0]+"','"+data[1]+"')");
            }
//            baca_data();
//            formMember.baca_data();
        } catch(SQLException e){
//            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
    }

}
