package teumin.client.controller;

import teumin.network.Network;

public class Controller {
    protected Network network;
    // 현재 접속한 사람 id

    public void setNetwork(Network network) {
        this.network = network;
    }
}
