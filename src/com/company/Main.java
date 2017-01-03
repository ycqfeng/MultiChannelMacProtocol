package com.company;


import han_multiChannelMacProtocol.CSDevice;
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
        CSDevice csDevice = new CSDevice(simulator);

        simulator.setPrintControl(printControl);
        channel.setPrintControl(printControl);
        channel.setNumOfSubChannels(1);
        channel.setALLBps(1000000);
        csDevice.setPrintControl(printControl);
        csDevice.setChannel(channel);
        csDevice.setOccupySubChannel(0,true);

        Packet packet = new Packet();
        packet.setLengthBits(1000);
        csDevice.send(packet, 0, 1);

        packet = new Packet();
        packet.setLengthBits(1000);
        csDevice.send(packet, 0, 1.0001);

        packet = new Packet();
        packet.setLengthBits(1000);
        csDevice.send(packet, 0, 2);

        simulator.setStopTime(100);

        printControl.setPrintLogicInfoState(channel, true);
        printControl.setPrintLogicInfoState(channel.getSubChannel(0), true);
        printControl.setPrintLogicInfoState(csDevice, true);

        simulator.start();
        System.out.println("Hello,world.");
    }
}
