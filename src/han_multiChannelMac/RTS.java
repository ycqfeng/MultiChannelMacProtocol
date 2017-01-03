package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class RTS extends Packet {
    static int length = 20*8;
    static int kind = 1;

    public static int getLength() {
        return length;
    }
}
