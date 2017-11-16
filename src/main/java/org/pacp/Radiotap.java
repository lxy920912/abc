package org.pacp;

/**
 * Created by lxy_920912 on 09/04/2017.
 */
public class Radiotap {
    private RadiotapHeader radiotapHeader;
    private Frame frame;

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public RadiotapHeader getRadiotapHeader() {
        return radiotapHeader;
    }

    public void setRadiotapHeader(org.pacp.RadiotapHeader radiotapHeader) {
        this.radiotapHeader = radiotapHeader;
    }

    public void unpackRadio(byte[] content){
        radiotapHeader = new RadiotapHeader();
        int[] len = {2,2,4,8,1,1,2,2,1,1,1};

        byte[] buffer_2 = new byte[2];
        byte buffer_1;
        byte[] buffer_8 = new byte[8];
        //start = 2;
        for(int i = 0;i < 2;i++){
            buffer_2[i] = content[2+i];
        }
        ByteTool.reverseByteArray(buffer_2);
        radiotapHeader.setLength(ByteTool.byteArrayToShort(buffer_2,0));
        //start = 8;
        //len = 8;
        for(int i = 0;i < 8;i++){
            buffer_8[i] = content[8+i];
        }
        ByteTool.reverseByteArray(buffer_8);
        radiotapHeader.setMacTimeStamp(ByteTool.byteArrayToLong(buffer_8,0));
        buffer_1 = content[17];
        radiotapHeader.setDataRate(ByteTool.byteToInt(buffer_1)/2);
        radiotapHeader.setSsiSignal((int)content[22]);
        radiotapHeader.setNoizeSignal((int)content[23]);
        frame = new Frame();
        if(radiotapHeader.getLength() != 25){
            frame.setType(0);
        }else{
            byte type =content[25];
            if(type == -128){
                frame.setType(1);
                if(content.length < 48){
                    return;
                }
                for(int i = 0;i < 2;i++){
                    buffer_2[i] = content[47+i];
                }
                ByteTool.reverseByteArray(buffer_2);
                int sequence = ByteTool.byteArrayToShort(buffer_2,0)/16;

                frame.setSequence(sequence);
                //timesatp start:49
                if(content.length < 63){
                    return;
                }

                for(int i = 0;i < 8;i++){
                    buffer_8[i] = content[49+i];
                }
                ByteTool.reverseByteArray(buffer_8);
                long timeStamp = ByteTool.byteArrayToLong(buffer_8,0);
                if(timeStamp < 0){
                    return;
                }
                frame.setTimeStamp(timeStamp);
                int ssidLen = (int)content[62] & 0xFF;
                if(content.length < 63+ssidLen){
                    return;
                }

                if(content.length < (63+ssidLen)){
                    return;
                }
                byte[] ssid = new byte[ssidLen];
                for(int i = 0;i < ssidLen;i++){
                    ssid[i] = content[63+i];
                }
                frame.setSSID(new String(ssid));
            }
        }
    }

}
