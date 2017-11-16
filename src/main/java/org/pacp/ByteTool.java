package org.pacp;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by lxy_920912 on 09/04/2017.
 */
public class ByteTool {
    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
    public static long byteArrayToLong(byte[]b,int offset){
        long value = 0;
        for (int i = 0; i < 8; i++) {
            int shift = (8 - 1 - i) * 8;
            long temp = b[i+offset] & 0x00000000000000FF;
            temp = temp<<shift;
            value = value+temp;
        }
        return value;
    }

    public static short byteArrayToShort(byte[] b, int offset) {
        short value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (2 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    /**
     * 反转数组
     * @param arr
     */
    public static void reverseByteArray(byte[] arr){
        byte temp;
        int n = arr.length;
        for(int i=0; i<n/2; i++){
            temp = arr[i];
            arr[i] = arr[n-1-i];
            arr[n-1-i] = temp;
        }
    }
}
