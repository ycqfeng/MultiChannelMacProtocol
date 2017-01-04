package han_multiChannelMacProtocol;

import han_simulatorComponents.EventInterface;
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

    private StateCSDevice state = StateCSDevice.IDLE;

    private Channel channel;
    private StateSubChannel[] stateSubChannels;
    private boolean[] occupySubChannels;

    public CSDevice(Simulator simulator){
        this.simulator = simulator;
        this.simulator.register(this);
    }

    public PrintControl getPrintControl() {
        return printControl;
    }

    public void stateTurnTo(StateCSDevice state){
        this.state = state;
    }

    private void stateTurnTo(StateCSDevice state, double interTime){
        switch (state){
            case IDLE:
                CSDeviceTurnToIDLE turnToIDLE = new CSDeviceTurnToIDLE(this);
                this.simulator.addEvent(interTime, turnToIDLE);
                break;
            case TRANSMITING:
                CSDeviceTurnToTRANSMITING turnToTRANSMITING = new CSDeviceTurnToTRANSMITING(this);
                this.simulator.addEvent(interTime, turnToTRANSMITING);
                break;
            case RECEVING:
                CSDeviceTurnToRECEVING turnToRECEVING = new CSDeviceTurnToRECEVING(this);
                this.simulator.addEvent(interTime,turnToRECEVING);
                break;
            case CONVERSION:
                CSDeviceTurnToCONVERSION turnToCONVERSION = new CSDeviceTurnToCONVERSION(this);
                this.simulator.addEvent(interTime, turnToCONVERSION);
                break;
            default:break;
        }
    }

    public StateCSDevice getState(){
        return this.state;
    }

    public boolean isAttachToSubChannel(int index){
        return this.occupySubChannels[index];
    }

    public void send(Packet packet, int index){
        this.send(packet, index, 0);
    }

    public void send(Packet packet, int index, double interTime){
        this.stateTurnTo(StateCSDevice.TRANSMITING, interTime);
        this.channel.getSubChannel(index).send(packet, interTime);
        double transTime = interTime + this.channel.getSubChannel(index).getTimeTrans(packet);
        this.stateTurnTo(StateCSDevice.IDLE, transTime);
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

class CSDeviceTurnToIDLE implements EventInterface{
    CSDevice csDevice;

    public CSDeviceTurnToIDLE(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run(){
        String str = "";
        str += csDevice.getSimulator().getCurTime()+"s, ";
        str += "设备转换为空闲状态。";
        csDevice.getPrintControl().printlnLogicInfo(csDevice, str);
        csDevice.stateTurnTo(StateCSDevice.IDLE);
    }
}

class CSDeviceTurnToTRANSMITING implements EventInterface{
    CSDevice csDevice;

    public CSDeviceTurnToTRANSMITING(CSDevice csDevice){

        this.csDevice = csDevice;
    }

    @Override
    public void run() {
        String str = "";
        str += csDevice.getSimulator().getCurTime()+"s, ";
        str += "设备转换为发送状态。";
        csDevice.getPrintControl().printlnLogicInfo(csDevice, str);
        csDevice.stateTurnTo(StateCSDevice.TRANSMITING);
    }
}

class CSDeviceTurnToRECEVING implements EventInterface{
    CSDevice csDevice;

    public CSDeviceTurnToRECEVING(CSDevice csDevice) {
        this.csDevice = csDevice;
    }

    @Override
    public void run() {
        String str = "";
        str += csDevice.getSimulator().getCurTime()+"s, ";
        str += "设备转换为接收状态。";
        csDevice.getPrintControl().printlnLogicInfo(csDevice, str);
        csDevice.stateTurnTo(StateCSDevice.RECEVING);
    }
}

class CSDeviceTurnToCONVERSION implements EventInterface{
    CSDevice csDevice;

    public CSDeviceTurnToCONVERSION(CSDevice csDevice) {
        this.csDevice = csDevice;
    }

    @Override
    public void run() {
        String str = "";
        str += csDevice.getSimulator().getCurTime()+"s, ";
        str += "设备转换为转换状态。";
        csDevice.getPrintControl().printlnLogicInfo(csDevice, str);
        csDevice.stateTurnTo(StateCSDevice.CONVERSION);
    }
}
