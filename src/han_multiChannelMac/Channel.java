package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class Channel {
    SubChannel[] subChannels;

    public void attachDevice(CSDevice csDevice){
        for (int i = 0 ; i < this.subChannels.length ; i++){
            this.subChannels[i].addNotify(csDevice);
        }
    }

    public void setNumberOfChannels(int number){
        this.subChannels = new SubChannel[number];
        for (int i = 0 ; i < number ; i++){
            this.subChannels[i] = new SubChannel(this);
        }
    }

    public void setBandWidthALL(double bandWidth){
        for (int i = 0 ; i < this.subChannels.length ; i++){
            this.subChannels[i].setBandwidth(bandWidth);
        }
    }

    public void setBandWidth(int index, double bandWidth){
        this.getSubChannel(index).setBandwidth(bandWidth);
    }

    SubChannel getSubChannel(int index){
        return subChannels[index];
    }
}
