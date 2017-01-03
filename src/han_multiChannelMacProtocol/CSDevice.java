package han_multiChannelMacProtocol;

import han_simulatorComponents.Simulator;
import han_simulatorComponents.SimulatorInterface;
import printControlComponents.InterfacePrintControlRegisterInstance;
import printControlComponents.PrintControl;

/**
 * Created by ycqfeng on 2017/1/3.
 */
public class CSDevice implements SimulatorInterface, InterfacePrintControlRegisterInstance,
        InterfaceSubChannel{
    private Simulator simulator;
    private PrintControl printControl;

    private Channel channel;
    private StateSubChannel[] stateSubChannels;
    private boolean[] occupySubChannels;

    public CSDevice(Simulator simulator){
        this.simulator = simulator;
        this.simulator.register(this);
    }

    public boolean isAttachToSubChannel(int index){
        return this.occupySubChannels[index];
    }

    public void send(Packet packet, int index){
        this.channel.getSubChannel(index).send(packet);
    }

    public void send(Packet packet, int index, double interTime){
        this.channel.getSubChannel(index).send(packet, interTime);
    }

    public void setOccupySubChannel(int index, boolean occupy){
        this.occupySubChannels[index] = occupy;
    }

    public void setChannel(Channel channel){
        this.channel = channel;
        int numSubChannels = this.channel.getSumSubChannels();
        stateSubChannels = new StateSubChannel[numSubChannels];
        occupySubChannels = new boolean[numSubChannels];
        for (int i = 0 ; i < numSubChannels ; i++){
            stateSubChannels[i] = StateSubChannel.IDLE;
            occupySubChannels[i] = false;
        }
        channel.attach(this);
    }

    public Simulator getSimulator(){
        return this.simulator;
    }

    public void setPrintControl(PrintControl printControl){
        this.printControl = printControl;
        this.printControl.register(this);
    }

    @Override
    public void subChannelStateToIdle(SubChannel subChannel) {
        int index = subChannel.getIndex();
        this.stateSubChannels[index] = StateSubChannel.IDLE;
        this.printControl.printlnLogicInfo(this, "子信道状态转换为空闲");
    }

    @Override
    public void subChannelStateToTransmit(SubChannel subChannel) {
        int index = subChannel.getIndex();
        this.stateSubChannels[index] = StateSubChannel.TRANSMITTING;
        this.printControl.printlnLogicInfo(this, "子信道状态转换为发送");
    }

    @Override
    public void subChannelStateToPropagation(SubChannel subChannel) {
        int index = subChannel.getIndex();
        this.stateSubChannels[index] = StateSubChannel.PROPAGATION;
        this.printControl.printlnLogicInfo(this, "子信道状态转换为传播");
    }
}
