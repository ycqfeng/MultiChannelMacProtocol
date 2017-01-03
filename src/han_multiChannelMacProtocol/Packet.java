package han_multiChannelMacProtocol;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class Packet {
    private PacketType type;
    private static int uidBase = 0;
    private int lengthBits;
    private int uid;

    public Packet(){
        this.type = PacketType.PACKET;
        this.uid = Packet.uidBase++;
    }

    public PacketType getType(){
        return this.type;
    }

    public int getUid(){
        return this.uid;
    }

    public int getLengthBits(){
        return this.lengthBits;
    }

    public void setLengthBits(int length){
        this.lengthBits = length;
    }
}
