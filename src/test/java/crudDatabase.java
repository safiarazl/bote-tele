/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.*;

import safiar.com.KoneksiMysql;
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
    crudDatabase(){
        open_db();
        baca_data();
    }
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
            baca_data();
//            formMember.baca_data();
        } catch(SQLException e){
//            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
    }
    
//    public

    public long cbID(){
        try{
            stm=Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_FORWARD_ONLY);
            ResultSet rs=stm.executeQuery("select id from user");
            rs.beforeFirst();

            while(rs.next()){
//                System.out.println(rs.getString(1));
                return rs.getLong(1);
            }
            rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        return 0;
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
                dataTable[x][0] = RsBrg.getString("name");
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

    public static void main(String[] args) {
        crudDatabase crud = new crudDatabase();
        crud.baca_data();
    }
}
