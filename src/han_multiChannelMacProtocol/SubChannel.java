package han_multiChannelMacProtocol;

import han_simulatorComponents.Event;
import han_simulatorComponents.EventInterface;
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
    private StateSubChannel state;
    private int numTrans;

    private Channel channel;
    private static int indexBase = 0;
    private int index;

    public SubChannel(Simulator simulator, Channel channel, PrintControl printControl){
        this.simulator = simulator;
        this.channel = channel;
        this.printControl = printControl;
        printControl.register(this);
        this.state = StateSubChannel.IDLE;
        this.numTrans = 0;
        this.index = SubChannel.indexBase++;
    }

    public void send(Packet packet, double interTime){
        if (packet.getType() == PacketType.PACKET){
            SubChannelPacketTransmitBegin packetTransmitBegin = new SubChannelPacketTransmitBegin(this);
            packetTransmitBegin.setPacket(packet);
            Event event = new Event();
            event.setEventInterface(packetTransmitBegin);
            event.setInterTime(interTime);
            simulator.addEvent(event);
            return;
        }
    }

    public int getIndex(){
        return index;
    }

    public void send(Packet packet){
        this.send(packet,0);
    }

    public int getNumTrans(){
        return this.numTrans;
    }

    private void turnToIDLE(){
        this.state = StateSubChannel.IDLE;

        SubChannelTurnToIDLE turnToIDLE;
        Event event;
        CSDevice[] csDevices = channel.getCsDevices();
        for (int i = 0 ; i < csDevices.length ; i++){
            if (csDevices[i].isAttachToSubChannel(this.index)){
                turnToIDLE = new SubChannelTurnToIDLE(this);
                turnToIDLE.setCsDevice(csDevices[i]);
                event = new Event();
                event.setEventInterface(turnToIDLE);
                event.setInterTime(0);
                this.simulator.addEvent(event);
            }
        }
    }

    private void turnToTRANSMIT(){
        this.state = StateSubChannel.TRANSMITTING;

        SubChannelTurnToTRANSMIT turnToTRANSMIT;
        Event event;
        CSDevice[] csDevices = channel.getCsDevices();
        for (int i = 0 ; i < csDevices.length ; i++){
            if (csDevices[i].isAttachToSubChannel(this.index)){
                turnToTRANSMIT = new SubChannelTurnToTRANSMIT(this);
                turnToTRANSMIT.setCsDevice(csDevices[i]);
                event = new Event();
                event.setEventInterface(turnToTRANSMIT);
                event.setInterTime(0);
                this.simulator.addEvent(event);
            }
        }
    }

    private void turnToPROPAGATION(){
        this.state = StateSubChannel.PROPAGATION;

        SubChannelTurnToPROPAGATION turnToPROPAGATION;
        Event event;
        CSDevice[] csDevices = channel.getCsDevices();
        for (int i = 0 ; i < csDevices.length ; i++){
            if (csDevices[i].isAttachToSubChannel(this.index)){
                turnToPROPAGATION = new SubChannelTurnToPROPAGATION(this);
                turnToPROPAGATION.setCsDevice(csDevices[i]);
                event = new Event();
                event.setEventInterface(turnToPROPAGATION);
                event.setInterTime(0);
                this.simulator.addEvent(event);
            }
        }
    }

    public void turnToState(StateSubChannel state){
        if (this.state == StateSubChannel.IDLE){
            if (state == StateSubChannel.TRANSMITTING){
                this.turnToTRANSMIT();
                this.numTrans = 1;
                return;
            }
        }
        if (this.state == StateSubChannel.TRANSMITTING){
            if (state == StateSubChannel.IDLE){
                this.numTrans -= 1;
                if (this.numTrans == 0){
                    this.turnToIDLE();
                }
                return;
            }
            if (state == StateSubChannel.TRANSMITTING){
                this.numTrans += 1;
                return;
            }
        }
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public StateSubChannel getState() {
        return state;
    }

    public Channel getChannel() {
        return channel;
    }

    public double getTimeTrans(Packet packet){
        return packet.getLengthBits()/this.bps;
    }

    public void setBps(double bps){
        this.bps = bps;
    }

    public PrintControl getPrintControl(){
        return this.printControl;
    }

    public double getBps(){
        return bps;
    }
}

class SubChannelTurnToPROPAGATION implements EventInterface{
    SubChannel subChannel;
    CSDevice csDevice;

    public SubChannelTurnToPROPAGATION(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    public void setCsDevice(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run(){
        this.csDevice.subChannelStateToPropagation(this.subChannel);
    }
}

class SubChannelTurnToTRANSMIT implements EventInterface{
    SubChannel subChannel;
    CSDevice csDevice;

    public SubChannelTurnToTRANSMIT(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    public void setCsDevice(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run(){
        this.csDevice.subChannelStateToTransmit(this.subChannel);
    }
}

class SubChannelTurnToIDLE implements EventInterface{
    SubChannel subChannel;
    CSDevice csDevice;

    public SubChannelTurnToIDLE(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    public void setCsDevice(CSDevice csDevice){
        this.csDevice = csDevice;
    }

    @Override
    public void run(){
        this.csDevice.subChannelStateToIdle(this.subChannel);
    }
}

class SubChannelPacketTransmitBegin implements EventInterface{
    SubChannel subChannel;
    Packet packet;

    public SubChannelPacketTransmitBegin(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    public void setPacket(Packet packet){
        this.packet = packet;
    }

    @Override
    public void run() {
        String str = "";
        subChannel.turnToState(StateSubChannel.TRANSMITTING);
        double t = subChannel.getTimeTrans(packet);
        SubChannelPacketTransmitEnd packetTransmitEnd = new SubChannelPacketTransmitEnd(subChannel);
        packetTransmitEnd.setPacket(packet);
        Event event = new Event();
        event.setInterTime(t);
        event.setEventInterface(packetTransmitEnd);
        subChannel.getSimulator().addEvent(event);

        str += subChannel.getSimulator().getCurTime() + "s, ";
        str += "一个包("+packet.getUid()+")开始传送。";
        str += subChannel.getNumTrans();
        subChannel.getPrintControl().printlnLogicInfo(subChannel, str);
    }
}

class SubChannelPacketTransmitEnd implements EventInterface{
    SubChannel subChannel;
    Packet packet;

    public SubChannelPacketTransmitEnd(SubChannel subChannel){
        this.subChannel = subChannel;
    }

    public void setPacket(Packet packet){
        this.packet = packet;
    }

    @Override
    public void run() {
        String str = "";
        subChannel.turnToState(StateSubChannel.IDLE);

        str += subChannel.getSimulator().getCurTime() + "s, ";
        str += "一个包("+packet.getUid()+")完成传送。";
        str += subChannel.getNumTrans();
        subChannel.getPrintControl().printlnLogicInfo(subChannel, str);
    }
}