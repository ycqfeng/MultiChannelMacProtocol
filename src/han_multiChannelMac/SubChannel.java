package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/2.
 */
public class SubChannel {
    Channel channel;
    double bandwidth;//MHz
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

class SubChannelTransmitBegin{
    SubChannel subChannel;
    public SubChannelTransmitBegin(SubChannel subChannel){
        this.subChannel = subChannel;
    }
}

class SubChannelTransmitEnd{
    SubChannel subChannel;
    public SubChannelTransmitEnd(SubChannel subChannel){
        this.subChannel = subChannel;
    }
}

class SubChannelPropagationBegin{
    SubChannel subChannel;
    public SubChannelPropagationBegin(SubChannel subChannel){
        this.subChannel = subChannel;
    }
}

class SubChannelPropagationEnd{
    SubChannel subChannel;
    public SubChannelPropagationEnd(SubChannel subChannel){
        this.subChannel = subChannel;
    }
}