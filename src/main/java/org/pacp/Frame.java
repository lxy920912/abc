package org.pacp;

/**
 * Created by lxy_920912 on 09/04/2017.
 */
public class Frame {
    int type;
    String SSID;
    long timeStamp;
    int sequence;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        if(timeStamp < 0){
            this.timeStamp = 0;
            return;
        }

        this.timeStamp = Math.abs(timeStamp);
    }
}
