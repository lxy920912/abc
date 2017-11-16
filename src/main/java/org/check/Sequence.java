package org.check;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
public class Sequence {
    public boolean checkSequence(int pre,int current){
       if(current - pre > 0 && current - pre < 10){
           return  true;
       }else if(0xffff - pre < 10 && current < 10){
           return  true;
       }else {
           return false;
       }
    }
}
