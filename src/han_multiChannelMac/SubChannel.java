package han_multiChannelMac;

import han_simulatorComponents.EventInterface;

/**
 * Created by ycqfeng on 2017/1/2.
 */
public class SubChannel {
    Channel channel;
    double bandwidth;//MHz
    double bps;
    IF_SubchannelNotify[] notifies;
    StateSubChannel stateSubChannel = StateSubChannel.IDLE;

    //Event
    SubChannelTransmitBegin transmitBegin;
    SubChannelTransmitEnd transmitEnd;
    SubChannelPropagationBegin propagationBegin;
    SubChannelPropagationEnd propagationEnd;

    public SubChannel(){
        this.transmitBegin = new SubChannelTransmitBegin(this);
        this.transmitEnd = new SubChannelTransmitEnd(this);
        this.propagationBegin = new SubChannelPropagationBegin(this);
        this.propagationEnd = new SubChannelPropagationEnd(this);
    }

    public double getTimeTransmit(int bits){
        double s = (double) bits;
        return s/bps;
    }


    public SubChannel(Channel channel){
        this.channel = channel;
    }



    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    void addNotify(IF_SubchannelNotify notify){
        if (notify == null) {
            return;
        }
        if (this.notifies == null){

            this.notifies = new IF_SubchannelNotify[1];
            this.notifies[0] = notify;
        }
        else{
            for (int i = 0 ; i < this.notifies.length ; i++) {
                if (this.notifies[i] == notify) {
                    return;
                }
            }
            IF_SubchannelNotify[] notifies = new IF_SubchannelNotify[this.notifies.length+1];
            System.arraycopy(this.notifies, 0, notifies, 0, this.notifies.length);
            notifies[this.notifies.length] = notify;
            this.notifies = notifies;
        }
        return;
    }

}

class SubChannelTransmitBegin implements EventInterface{
    SubChannel subChannel;
    public SubChannelTransmitBegin(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    @Override
    public void run() {

    }
}

class SubChannelTransmitEnd implements EventInterface{
    SubChannel subChannel;
    public SubChannelTransmitEnd(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    @Override
    public void run() {

    }
}

class SubChannelPropagationBegin implements EventInterface{
    SubChannel subChannel;
    public SubChannelPropagationBegin(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    @Override
    public void run() {

    }
}

class SubChannelPropagationEnd implements EventInterface{
    SubChannel subChannel;
    public SubChannelPropagationEnd(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    @Override
    public void run() {

    }
}