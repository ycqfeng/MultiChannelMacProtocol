package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class CSDevice implements IF_SubchannelNotify {
    int indexControlChannel;
    Channel channel;

    public SubChannel getControlChannel(){
        return channel.getSubChannel(this.indexControlChannel);
    }

    public void setIndexControlChannel(int indexControlChannel){
        this.indexControlChannel = indexControlChannel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void beOccupied(SubChannel subChannel) {

    }

    @Override
    public void beReleased(SubChannel subChannel) {

    }
}
