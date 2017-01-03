package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/2.
 */
public interface IF_SubchannelNotify {
    void beOccupied(SubChannel subChannel);
    void beReleased(SubChannel subChannel);
}
