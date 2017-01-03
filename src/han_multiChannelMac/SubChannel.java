package han_multiChannelMac;

/**
 * Created by ycqfeng on 2017/1/2.
 */
public class SubChannel {
    IF_SubchannelNotify[] notifies;

    void addNotify(IF_SubchannelNotify notify){
        if (notify == null){
            return;
        }
        if (this.notifies == null){
            this.notifies = new IF_SubchannelNotify[1];
            this.notifies[0] = notify;
        }
        else{
            IF_SubchannelNotify[] notifies = new IF_SubchannelNotify[this.notifies.length+1];
            System.arraycopy(this.notifies, 0, notifies, 0, this.notifies.length);
            notifies[this.notifies.length] = notify;
            this.notifies = notifies;
        }
        return;
    }

}
