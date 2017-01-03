package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */

/**
 * kind
 * 1 : RTS
 */
public abstract class Packet {
    private PacketType type;

    public PacketType getType(){
        return this.type;
    }

    public void setType(PacketType type){
        this.type = type;
    }
}
