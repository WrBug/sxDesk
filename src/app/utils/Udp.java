package app.utils;

import app.model.ShanXunManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDP {
    private DatagramSocket clientSocket = null;
    private InetSocketAddress serverAddress = null;
    private byte[] data;

    private UDP(String host, int port, byte[] data) throws SocketException {
        clientSocket = new DatagramSocket();
        serverAddress = new InetSocketAddress(host, port);
        this.data = data;
    }
    public static void send(String host, int port, byte[] data) {
        try {
            UDP udp = new UDP(host, port, data);
            DatagramPacket packet = null;
            packet = new DatagramPacket(data, data.length,
                    udp.serverAddress);
            udp.clientSocket.send(packet);
            udp.clientSocket.close();
        } catch (SocketException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}