package han_multiChannelMacProtocol;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public interface InterfaceSubChannel {
    default void subChannelStateToIdle(SubChannel subChannel){}
    default void subChannelStateToTransmit(SubChannel subChannel){}
    default void subChannelStateToPropagation(SubChannel subChannel){}
    default void subChannelCollision(SubChannel subChannel){}
}
