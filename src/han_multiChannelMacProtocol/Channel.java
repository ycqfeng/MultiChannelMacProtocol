package han_multiChannelMacProtocol;

import han_simulatorComponents.Simulator;
import han_simulatorComponents.SimulatorInterface;
import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class Channel implements SimulatorInterface, InterfacePrintControlRegisterInstance {
    private PrintControl printControl;
    private Simulator simulator;

    private SubChannel[] subChannels;

    public Channel(Simulator simulator){
        this.simulator = simulator;
    }

    public void setNumOfSubChannels(int numOfSubChannels){
        if (numOfSubChannels < 0){
            String str = "";
            str += this.getClass().getName()+":";
            str += "参数错误";
            this.printControl.printlnErrorInfo(this, str);
        }
        this.subChannels = new SubChannel[numOfSubChannels];
        for (int i = 0 ; i < numOfSubChannels ; i++){
            this.subChannels[i] = new SubChannel(this.simulator, this, this.printControl);
        }
    }

    public SubChannel getSubChannel(int index){
        return this.subChannels[index];
    }

    public void send(Packet packet, int index, double interTime){
        SubChannel subChannel = this.getSubChannel(index);
        subChannel.send(packet,interTime);
    }

    public void send(Packet packet, int index){
        this.getSubChannel(index).send(packet, 0);
    }

    public void setALLBps(double bps){
        if (this.subChannels == null){
            this.printControl.printlnErrorInfo(this, "信道数为0");
            return;
        }
        for (int i = 0 ; i < this.subChannels.length ; i++){
            this.subChannels[i].setBps(bps);
        }
    }

    @Override
    public void simulatorStart() {
        String str = "";
        str += "时间："+this.simulator.getCurTime()+"秒，";
        str += "信道准备。";
        this.printControl.printlnLogicInfo(this, str);
    }

    public void setPrintControl(PrintControl printControl) {
        this.printControl = printControl;
        this.printControl.register(this);
    }
}
