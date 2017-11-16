package org.check;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
public class SrcMac {
    public boolean checkSrcMac(String srcMac, String desMac){
        if(desMac.equals(srcMac)){
            return  true;
        }
        return  false;
    }
}
