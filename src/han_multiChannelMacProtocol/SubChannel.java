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

    public SubChannel(Simulator simulator, Channel channel, PrintControl printControl){
        this.simulator = simulator;
        this.channel = channel;
        this.printControl = printControl;
        printControl.register(this);
        this.state = StateSubChannel.IDLE;
        this.numTrans = 0;
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

    public void send(Packet packet){
        this.send(packet,0);
    }

    public int getNumTrans(){
        return this.numTrans;
    }

    public void turnToState(StateSubChannel state){
        if (this.state == StateSubChannel.IDLE){
            if (state == StateSubChannel.TRANSMITTING){
                this.state = state;
                this.numTrans = 1;
                return;
            }
        }
        if (this.state == StateSubChannel.TRANSMITTING){
            if (state == StateSubChannel.IDLE){
                this.numTrans -= 1;
                if (this.numTrans == 0){
                    this.state = StateSubChannel.IDLE;
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