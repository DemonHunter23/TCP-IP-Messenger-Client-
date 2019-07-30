package com.company;

import network.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.start();
    }
}
