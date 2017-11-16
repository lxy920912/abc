package org.check;

import org.pacp.*;


import java.sql.*;

/**
 * Created by lixiaoyan on 2017/4/13.
 */
public class MysqlHandle {
    private Connection connection;
    private Statement statement;
    public MysqlHandle(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载mySQL driver");
        }catch (ClassNotFoundException e){
            System.out.println("Cannot find mysql driver!!!");
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/pcap?characterEncoding=utf8&useSSL=true";
        try {
            this.connection = DriverManager.getConnection(url,"root","root");
            this.statement = connection.createStatement();
            System.out.println("connect to mysql succeed");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void closeConnect(){
        try {
            this.connection.close();
            this.statement.close();
            System.out.println("close mysql connection");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public boolean insertData(PcapData pcapData){
        String sqlString = "INSERT INTO pcap_data(receive_s,receive_ms,packet_length,radiotapHeadLength," +
                "macTimeStamp,radFlag,dataRate,channelFlag,ssid_signal,noizeSignal,frameType,ssid," +
                "frame_timestamp,FCS,sequence)";
        String value = " VALUES(";
        value = value + pcapData.getTime_s()+",";
        value = value + pcapData.getTime_ms()+",";
        value = value + pcapData.getpLength()+",";
        RadiotapHeader radiotapHeader = pcapData.getRadiotap().getRadiotapHeader();
        value = value + radiotapHeader.getLength()+",";
        value = value + radiotapHeader.getMacTimeStamp()+",";
        value = value + radiotapHeader.getFlags()+",";
        value = value + radiotapHeader.getDataRate()+",";
        value = value + radiotapHeader.getChanneFlag()+",";
        value = value + radiotapHeader.getSsiSignal()+",";
        value = value + radiotapHeader.getNoizeSignal()+",";

        Frame frame = pcapData.getRadiotap().getFrame();
        value = value + frame.getType()+",";
        value = value + "\""+frame.getSSID()+"\""+",";
        value = value + frame.getTimeStamp()+",0,";//FCS == 1 true
        value = value + frame.getSequence()+")";

//        System.out.println(sqlString);
        try{
            String sql = sqlString + value;
//            System.out.println(sql);
            this.statement.executeUpdate(sql);

        }catch (SQLException e){
            String errSql = " VALUES (";
            errSql = errSql + pcapData.getTime_s()+",";
            errSql = errSql + pcapData.getTime_ms()+",";
            errSql = errSql + pcapData.getpLength()+",";
            errSql = errSql + radiotapHeader.getLength()+",";
            errSql = errSql + radiotapHeader.getMacTimeStamp()+",";
            errSql = errSql + radiotapHeader.getFlags()+",";
            errSql = errSql + radiotapHeader.getDataRate()+",";
            errSql = errSql + radiotapHeader.getChanneFlag()+",";
            errSql= errSql + radiotapHeader.getSsiSignal()+",";
            errSql = errSql + radiotapHeader.getNoizeSignal()+",";
            frame = new Frame();
            errSql  = errSql  + frame.getType()+",";
            errSql  = errSql  + "\""+frame.getSSID()+"\""+",";
            errSql  = errSql + frame.getTimeStamp()+",1,-1)";//FCS == 1 err
            errSql = sqlString + errSql;

            try {
                this.statement.executeUpdate(errSql);
            }catch (SQLException e1){
                System.out.println("insert error data !!!error agin!!!");
                e1.printStackTrace();
                System.out.println(errSql);
                closeConnect();
                System.exit(1);
            }
        }

        return true;
    }
    public ResultSet selectData(String sql){
        ResultSet resultSet = null;
       try {
           resultSet =  statement.executeQuery(sql);
       }catch (Exception ex){
           ex.printStackTrace();
       }
       return  resultSet;
    }
}
//value(1490970291,265110,291,25,1023483893,0,6,0,-41,-95,0,"null",0)
