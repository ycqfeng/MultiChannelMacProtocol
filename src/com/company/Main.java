package com.company;

import han_multiChannelMac.CSDevice;
import han_multiChannelMac.Channel;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Channel channel = new Channel();
        channel.setNumberOfChannels(10);
        channel.setBandWidthALL(1);

        CSDevice csDevice = new CSDevice();
        channel.attachDevice(csDevice);
        System.out.println("Hello,world.");
    }
}
