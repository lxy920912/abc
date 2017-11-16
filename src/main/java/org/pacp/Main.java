package org.pacp;

import org.dataParser.ChannelLoad;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * Created by lxy_920912 on 07/04/2017.
 */
public class Main {
    public static void main(String[] args){
        InputStream is = Main.class.getResourceAsStream("802_11_7_20.pcap");
        try {
            Pcap pcap= PcapParser.unpack(is);
            is.close();
            byte[] t = pcap.getData().get(0).getContent();
            byte[] data = Arrays.copyOfRange(t,42,t.length);
            ChannelLoad channelLoad = new ChannelLoad();
            channelLoad.buildMap(pcap);
        }catch(IOException exc) {
            System.out.println("Exception encountered: " + exc);
        }

    }
}
