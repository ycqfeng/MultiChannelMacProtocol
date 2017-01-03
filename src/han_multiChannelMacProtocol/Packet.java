package han_multiChannelMacProtocol;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class Packet {
    private int lengthBits;

    public int getLengthBits(){
        return this.lengthBits;
    }

    public void setLengthBits(int length){
        this.lengthBits = length;
    }
}
