package han_multiChannelMac;

import han_simulatorComponents.EventInterface;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class CSDevice implements IF_SubchannelNotify {
    int indexControlChannel;
    Channel channel;
    StateCSDevice state = StateCSDevice.IDLE;

    //Event
    CSDeviceTransmitBegin transmitBegin;
    CSDeviceTransmitEnd transmitEnd;
    CSDevicePropagationBegin propagationBegin;
    CSDevicePropagationEnd propagationEnd;

    public CSDevice(){
        this.transmitBegin = new CSDeviceTransmitBegin(this);
        this.transmitEnd = new CSDeviceTransmitEnd(this);
        this.propagationBegin = new CSDevicePropagationBegin(this);
        this.propagationEnd = new CSDevicePropagationEnd(this);
    }

    public void sendRTS(){
        RTS rts = new RTS();

    }

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
class CSDeviceTransmitBegin implements EventInterface{
    CSDevice csDevice;
    public CSDeviceTransmitBegin(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run() {

    }
}

class CSDeviceTransmitEnd implements EventInterface{
    CSDevice csDevice;
    public CSDeviceTransmitEnd(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run() {

    }
}

class CSDevicePropagationBegin implements EventInterface{
    CSDevice csDevice;
    public CSDevicePropagationBegin(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run() {

    }
}

class CSDevicePropagationEnd implements EventInterface{
    CSDevice csDevice;
    public CSDevicePropagationEnd(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run() {

    }
}