package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */

/**
 * kind
 * 1 : RTS
 */
public abstract class Packet {
    private int kind;
    public void setKind(int kind){
        this.kind = kind;
    }

    public int getKind(){
        return this.kind;
    }
}
