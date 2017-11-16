package org.dataParser;

import org.pacp.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by lxy_920912 on 10/04/2017.
 */
public class ChannelLoad {
    Map<String,Long> channelLoad;
    public ChannelLoad(){
        this.channelLoad = new HashMap<String,Long>();
    }
    public void getMap(PcapData pcapData){
        String key = pcapData.getTime_s()+"."+(pcapData.getTime_ms()/100000);
        if(channelLoad.containsKey(key)){
            long value = channelLoad.get(key);
            value++;
            channelLoad.put(key,value);
        }else{
            long value = 1;
            channelLoad.put(key,value);
        }
    }
    public void buildMap(Pcap pcap){
        List<PcapData> list = pcap.getData();
        for(int i = 0;i < list.size();i++){
            getMap(list.get(i));
        }
        List<Map.Entry<String,Long>> info = new ArrayList<Map.Entry<String,Long>>(channelLoad.entrySet());
        Collections.sort(info, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                String key = o1.getKey();
                String[] key1s = key.split(Pattern.quote("."));
                key = o2.getKey();
                String[]key2s = key.split(Pattern.quote("."));
//                int val = Integer.parseInt(keyls[0])
//                int val = Integer.parseUnsignedInt(key1s[0]) - Integer.parseUnsignedInt(key2s[0]);
                  int val = Integer.parseInt(key1s[0]) - Integer.parseInt(key2s[0]);

                if(val > 0){
                    return 1;
                }else if(val < 0){
                    return -1;
                }else{
                    val = Integer.parseInt(key1s[1])-Integer.parseInt(key2s[1]);
                    if(val < 0){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            }
        });
        String s = "{";
        int size = info.size();
        int size_1 = size-1;
        for(int i = 0;i < size-1;i++){
            Map.Entry<String,Long> map = info.get(i);
            s = s+"\""+map.getKey()+"\":"+map.getValue()+",";
        }
        Map.Entry<String,Long> map = info.get(size_1);
        s = s+"\""+map.getKey()+"\":"+map.getValue()+",";
        s = s+"}";
        try {
            File file = new File("view/static/data/data.json");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(s);
            bw.close();
        }catch (IOException e){
            System.out.print(e);
        }

    }

}
