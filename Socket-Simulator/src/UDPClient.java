import java.io.*;
import java.net.*;


public class UDPClient {
   
    public static void main(String[] args) throws IOException {
        
        DatagramSocket socket = new DatagramSocket();
        
        byte[] bytesSent = new byte[1024];
        
        String message = "hello, this is the client";
        bytesSent = message.getBytes();
        
        //address of machine
        InetAddress address = InetAddress.getByName("localhost");
        int port = 4445;
        
        //packet to send
        DatagramPacket packet = new DatagramPacket(bytesSent, bytesSent.length, address, port);
        socket.send(packet);
        
        //received packet
        String receivedMessage = new String(packet.getData());
        System.out.println("Received message: " + receivedMessage);
        
        socket.close();
    }
    
}
