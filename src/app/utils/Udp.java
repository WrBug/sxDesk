package app.utils;

import app.model.ShanXunManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Udp {
    private DatagramSocket clientSocket = null;
    private InetSocketAddress serverAddress = null;
    private byte[] data;

    private Udp(String host, int port, byte[] data) throws SocketException {
        clientSocket = new DatagramSocket();
        serverAddress = new InetSocketAddress(host, port);
        this.data = data;
    }

    public static Udp instance(String host, int port, byte[] data) throws SocketException {
        return new Udp(host, port, data);
    }

    public void send() {
        ShanXunManager.successTotal++;
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(data, data.length,
                    serverAddress);
            clientSocket.send(packet);
            clientSocket.close();
        } catch (SocketException e) {
        } catch (IOException e) {
        }

    }
}