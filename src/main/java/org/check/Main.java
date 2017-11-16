package org.check;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
public class Main {
    public static void main(String[] args) {
        Train train = new Train();
        int size = 30;
        int slid = 10;
//        train.trainAuthorized(size,slid,0,-1);
        if(!train.checkRogueAP()){
            System.out.println("存在非法AP");
        }else {
            System.out.println("不存在非法AP");
        }
    }
}
