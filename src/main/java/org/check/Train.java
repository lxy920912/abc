package org.check;


import java.sql.ResultSet;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
public class Train {
    Gaussian gaussian;
    Skew skew;
    public String trainAuthorized(int size,int slid,int timeStart,int timeSize){
        ResultSet resultSet = null;
        int timeEnd = timeStart+timeSize;
        if(timeSize == -1){
            timeEnd = Integer.MAX_VALUE;
        }
        int start = 0;
        int count = 0;
        int total = 1000;
        int k = 0;
        int number = 0;
        String ssid = "TP-LINK_32%";
        String gaussianResult = "{";
        String skewResult = "{";
        try {
           MysqlHandle  mysqlHandle = new MysqlHandle();
            while(count < total){
                String sql = "select * from libpcap_data2 where ssid like \"" +ssid +"\" AND id > "+start+" limit "+size;
                System.out.println(sql);
                resultSet = mysqlHandle.selectData(sql);
                double [] signals = new double[size];
                double [] receiveX =  new double[size];
                double [] timestampY = new double[size];
                int n = 0;
                while (resultSet.next()){
                    signals[n] = Double.valueOf(resultSet.getString("ssid_signal"));
                    if(resultSet.getString("ssid").equals("TP-LINK_32x")){
//                        System.out.print("TP-LINK_32x:\t"+resultSet.getString("ssid_signal"));
                    }
                    receiveX[n] = getReceiveTime(resultSet.getString("receive_s"),resultSet.getString("receive_ms"));
                    timestampY[n]  =  Double.valueOf(resultSet.getString("frame_timestamp"));
                    n++;
                    if(n == slid){
                        start = Integer.valueOf(resultSet.getString("id"));
//                        System.out.print("start:"+start);
                    }
                }

                count = count+ slid;
                Algorithm algorithm = new Algorithm();
                gaussian = algorithm.getGaussain(signals);
                skew = algorithm.getSkew(receiveX,timestampY);
                System.out.println("skew:"+skew.a+","+skew.b);
                number++;
                if(number >= timeStart && number <= timeEnd){
                    gaussianResult =gaussianResult+ "\""+ssid+number + "\":"+ gaussian.getGuassianStr()+",";
                }
                if(number > timeEnd){
                    break;
                }
                if( n < size ){
                    break;
                }
            }
            gaussianResult =gaussianResult+ "\""+ssid+number + "\":"+ gaussian.getGuassianStr()+"";
            gaussianResult = gaussianResult+"}";
            mysqlHandle.closeConnect();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  gaussianResult;
    }
    public double getReceiveTime(String receive_s, String receive_ms){
        double ms = Double.valueOf(receive_ms) / 1000000;
        double s = Double.valueOf(receive_s);
        double receiveTime = s + ms;
        return receiveTime;
    }
    public boolean checkRogueAP(){
        boolean result = false;
        int slid  = 10;
        try {
            MysqlHandle mysqlHandle = new MysqlHandle();
            long start = 0;
            int windowSize = 20;
            double[] signal = new double[windowSize];
            double[] receiveX = new double[windowSize];
            double[] timestampY = new double[windowSize];
            Gaussian lastGaussin = null;
            Skew lastSkew = null;
            int slidSize = 10;
            boolean flag = true;
            while (flag) {
                int count = 0;
                String sql = "select * from libpcap_data2 where ssid like \"TP-LINK_32%\" AND id > " + start + " limit " + windowSize;
                ResultSet resultSet = mysqlHandle.selectData(sql);
                while (resultSet.next()) {
                    signal[count] = Double.valueOf(resultSet.getString("ssid_signal"));
                    receiveX[count] = Double.valueOf(getReceiveTime(resultSet.getString("receive_s"), resultSet.getString("receive_ms")));
                    timestampY[count] = Double.valueOf(resultSet.getString("frame_timestamp"));
                    count++;
                    if (count == slid) {
                        start = Long.valueOf(resultSet.getString("id"));
                        System.out.println("start:"+start);
                    }
                }
                Algorithm algorithm = new Algorithm();
                Gaussian gaussian = algorithm.getGaussain(signal);
                Skew skew = algorithm.getSkew(receiveX, timestampY);
                if (lastGaussin == null || lastSkew == null) {
                    lastGaussin = new Gaussian(gaussian.average,gaussian.sigma);
                    lastSkew = new Skew(skew.a,skew.b);
                } else {
                    System.out.println("average:"+gaussian.average+"sigma:"+gaussian.sigma+"lastSke:"+lastSkew.a+"skew:"+skew.a);
                    if (Math.abs(gaussian.average - lastGaussin.average) < 2 * lastGaussin.sigma && gaussian.sigma < 2) {
                        System.out.println("不存在非法AP..........");
                        lastGaussin = new Gaussian(gaussian.average,gaussian.sigma);
                        lastSkew = new Skew(skew.a,skew.b);
//                        continue;
                    } else if (Math.abs(skew.a - lastSkew.a) < 0.01) {
                        System.out.println("不存在非法AP..........");
                        lastGaussin = new Gaussian(gaussian.average,gaussian.sigma);
                        lastSkew = new Skew(skew.a,skew.b);
//                        continue;
                    } else if (gaussian.sigma > 10) {
                        showRogueAp(resultSet);
                        System.out.println("Rouge Ap exit sigam > 10");
//                        return false;
//                        System.exit(0);
                    } else {
                        double skewP = Math.abs(skew.a - lastSkew.a) * 10;// the presence of RougeAP by skew
                        if (skewP > 0.9) {
                            showRogueAp(resultSet);
                            System.out.println("Rogue Ap exit shew > 0.9");
//                            return false;
//                            System.exit(0);
                        }
                        double signalP1 = 1 - ((2 * lastGaussin.sigma) / Math.abs(lastGaussin.average - gaussian.average));  // the presence of Rogue AP BY average

                        double signalP2 = gaussian.sigma / 10;     //the presence of Rouge AP by sigma
                        double P = skewP * signalP1 * signalP2;
                        if (P > 0.5) {
                            showRogueAp(resultSet);
                            System.out.println("Rouge AP exit  P < 0.5");
//                            return false;
                        }else{
                            System.out.println("不存在非法AP..........");
                            lastGaussin = new Gaussian(gaussian.average,gaussian.sigma);
                            lastSkew = new Skew(skew.a,skew.b);
                       }
                    }
                }
                System.out.println("count:"+count);
                if (count < windowSize) {
                    break;
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    public  void showRogueAp(ResultSet resultSet){

        System.out.println("show Rogue AP .......");
        try{
            resultSet.absolute(0);
            while (resultSet.next()){
                System.out.println("id:"+resultSet.getString("id")+"\tssid:"+ resultSet.getString("ssid"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
