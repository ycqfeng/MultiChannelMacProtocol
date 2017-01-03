package han_multiChannelMacProtocol;

import han_simulatorComponents.Simulator;
import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class SubChannel implements InterfacePrintControlRegisterInstance {
    private Simulator simulator;
    private PrintControl printControl;

    private double bps;

    private Channel channel;

    public SubChannel(Simulator simulator, Channel channel, PrintControl printControl){
        this.simulator = simulator;
        this.channel = channel;
        this.printControl = printControl;
    }

    public double getTimeTrans(Packet packet){
        return packet.getLengthBits()/this.bps;
    }

    public void setBps(double bps){
        this.bps = bps;
    }

    public double getBps(){
        return bps;
    }
}
