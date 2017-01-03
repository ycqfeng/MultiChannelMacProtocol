package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class RTS extends Packet {
    static int length = 20*8;

    public RTS(){
        super.setType(PacketType.RTS);
    }
    public static int getLengthBits() {
        return length;
    }
}
