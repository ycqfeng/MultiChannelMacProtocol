package com.company;


import han_multiChannelMacProtocol.Channel;
import han_multiChannelMacProtocol.Packet;
import han_simulatorComponents.Simulator;
import printControlComponents.PrintControl;

public class Main {

    public static void main(String[] args) {
	// write your code here
        PrintControl printControl = new PrintControl();
        Simulator simulator = new Simulator();

        Channel channel = new Channel(simulator);

        simulator.setPrintControl(printControl);
        simulator.register(channel);
        channel.setPrintControl(printControl);
        channel.setNumOfSubChannels(1);
        channel.setALLBps(1000000);

        Packet packet = new Packet();
        packet.setLengthBits(1000);
        channel.getSubChannel(0).send(packet,1);

        packet = new Packet();
        packet.setLengthBits(1000);
        channel.getSubChannel(0).send(packet,2);

        packet = new Packet();
        packet.setLengthBits(1000);
        channel.getSubChannel(0).send(packet,2.0001);

        simulator.setStopTime(100);

        printControl.setPrintLogicInfoState(channel, true);
        printControl.setPrintLogicInfoState(channel.getSubChannel(0), true);

        simulator.start();
        System.out.println("Hello,world.");
    }
}
