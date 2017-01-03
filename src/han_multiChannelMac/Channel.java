package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class Channel {
    SubChannel[] subChannels;

    SubChannel getSubChannel(int index){
        return subChannels[index];
    }
}
