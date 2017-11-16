package org.pacp;

/**
 * Created by lxy_920912 on 08/04/2017.
 */
public class RadiotapHeader {
    private int version_pad;
    private int length;
    private int presentFlag;
    private long macTimeStamp;
    private int flags;
    private int dataRate;
    private int ChanneFlag;
    private int ssiSignal;
    private int noizeSignal;

    public int getVersion_pad() {
        return version_pad;
    }

    public void setVersion_pad(int version_pad) {
        this.version_pad = version_pad;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPresentFlag() {
        return presentFlag;
    }

    public void setPresentFlag(int presentFlag) {
        this.presentFlag = presentFlag;
    }

    public long getMacTimeStamp() {
        return macTimeStamp;
    }

    public void setMacTimeStamp(long macTimeStamp) {
        this.macTimeStamp = macTimeStamp;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getDataRate() {
        return dataRate;
    }

    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
    }

    public int getChanneFlag() {
        return ChanneFlag;
    }

    public void setChanneFlag(int channeFlag) {
        ChanneFlag = channeFlag;
    }

    public int getSsiSignal() {
        return ssiSignal;
    }

    public void setSsiSignal(int ssiSignal) {
        this.ssiSignal = ssiSignal;
    }

    public int getNoizeSignal() {
        return noizeSignal;
    }

    public void setNoizeSignal(int noizeSignal) {
        this.noizeSignal = noizeSignal;
    }

    @Override
    public String toString(){
        String s = "";
        s = " HeaderLength = "+this.length;
        s = s+ "\n macTimeStamp = "+this.macTimeStamp;
        s = s+"\n dataRate = "+this.dataRate;
        s = s+"\n ssiSignal = "+this.ssiSignal;
        s = s+"\n noizeSignal = "+this.noizeSignal;
        return s;
    }
}
